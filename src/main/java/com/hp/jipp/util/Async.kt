package com.hp.jipp.util

import java.util.*
import java.util.concurrent.*

/** A function that handles a Try result */
typealias TryCallback<T> = (Try<T>) -> Unit

/**
 * A holder for a value that may complete successfully or end in a thrown error.
 * Similar to Scala's [Future](https://www.scala-lang.org/api/2.12.2/scala/concurrent/Future.html).
 */
open class Async<Result> internal constructor(
        val executor: ExecutorService, work: (() -> Result)?) {

    /** The result at this moment, if any */
    @Volatile internal var result: Try<Result>? = null
        internal set(value) {
            synchronized(this) {
                value ?: throw IllegalArgumentException("Cannot set null")
                if (field != null) throw IllegalArgumentException("Already completed")
                field = value
                // No need to bother with any of this now
                cancelBlock = null
                future = null
            }
            tell()
        }

    /** Listeners waiting for results */
    private var listeners: List<TryCallback<Result>> = listOf()

    /** Thing to do if it's possible to cancel before a future gets started */
    internal var cancelBlock: (() -> Unit)? = null

    /** The success value, or null if incomplete or unsuccessful */
    val value: Result?
        get() {
            val result = this.result
            return when (result) {
                is Success<Result> -> result.value
                else -> null
            }
        }

    /** The error, or null if incomplete or successful */
    val error: Throwable?
        get() {
            val result = this.result
            return when (result) {
                is Error<Result> -> result.thrown
                else -> null
            }
        }

    /** Future doing work on a service, if necessary */
    private var future: Future<Unit>? = null

    init {
        if (work != null) {
            future = executor.submit(Callable {
                result = Try { work() }
            })
        }
    }

    /** Return true if completed successfully with a value */
    fun isValue(): Boolean = synchronized(this) {
        return isDone() && result is Success<Result>
    }

    /** Return true if completed with error */
    fun isError(): Boolean = synchronized(this) {
        return isDone() && result is Error<Result>
    }

    /** Return true if complete with either success or error. */
    fun isDone() = result != null

    /** Stop the attempt to get a value on this async (if it is not already done). */
    fun cancel() = synchronized(this) {
        // If it's complete then can't do anything
        if (isDone()) return false

        safeSetResult(Error(CancellationException()))

        // Apply the cancel block if possible
        val oldCancelBlock = this.cancelBlock
        this.cancelBlock = null
        if (oldCancelBlock != null) oldCancelBlock()

        // Forcibly cancel the future if possible
        future?.cancel(true)
    }

    /** Cancels this async if it has not completed in the specified period */
    fun timeout(millis: Long) {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                this@Async.cancel()
            }
        }, millis)
        listen { _ -> timer.cancel() }
    }

    /** Wait up to timeout for a value to arrive and return it, or null */
    fun await(timeout: Long): Result? = try {
        get(timeout)
    } catch (ignored: Throwable) {
        null
    }

    /** Like [await] but throws if the attempt to get the value threw */
    fun get(timeout: Long): Result? {
        if (!isDone()) {
            val latch = CountDownLatch(1)
            listen { _ -> latch.countDown() }
            latch.await(timeout, TimeUnit.MILLISECONDS)
        }

        return this.result?.get()
    }

    /** Tell all callbacks about results */
    private fun tell() {
        val result = this.result ?: return
        val toTell = synchronized(this) {
            val toTell = listeners
            // Only one notification is given
            listeners = listOf()
            toTell
        }
        // Inform outside of synchronized block
        toTell.forEach { executor.execute { it(result) } }
    }

    /** Listen for completion, returning this */
    private fun listen(listener: TryCallback<Result>): Async<Result> {
        synchronized(this) {
            listeners += listener
        }
        tell()
        return this
    }

    /** Stop listening for completion */
    private fun unlisten(listener: TryCallback<Result>): Async<Result> {
        synchronized(this) {
            listeners -= listener
        }
        return this
    }

    /** Adds a callback to be invoked if success is reached and returns the original async. */
    @JvmOverloads fun onSuccess(executor: ExecutorService = this.executor, callback: SuccessListener<Result>) =
            onSuccess(executor, callback::onSuccess)

    /** Adds a callback to be invoked if success is reached and returns the original async. */
    fun onSuccess(executor: ExecutorService = this.executor, callback: (Result) -> Unit) =
            listen { if (it is Success<Result>) executor.submit { callback(it.value) } }

    @JvmOverloads fun onError(executor: ExecutorService = this.executor, callback: ErrorListener) =
            onError(executor, callback::onError)

    /** Adds a callback to be invoked if an error is reached and returns the original async. */
    @JvmOverloads fun onError(executor: ExecutorService = this.executor, callback: (Throwable) -> Unit) =
            listen { if (it is Error<Result>) executor.submit { callback(it.thrown) } }

    /** Adds a callback to be invoked upon completion, and returns the original async */
    @JvmOverloads fun onDone(executor: ExecutorService = this.executor, callback: Listener<Result>) =
            this(executor) {
                when (it) {
                    is Error<Result> -> callback.onError(it.thrown)
                    is Success<Result> -> callback.onSuccess(it.value)
                }
            }

    /** Adds a callback to be invoked upon completion, and returns the original async */
    operator fun invoke(executor: ExecutorService = this.executor, callback: TryCallback<Result>): Async<Result> =
            listen { executor.submit { callback(it) } }

    /**
     * Supply code that will run on the first and only time this Async is cancelled. If the async is never
     * cancelled, the supplied code never executes
     */
    @JvmOverloads fun onCancel(executor: ExecutorService = this.executor, runnable: Runnable) =
        onCancel(executor, runnable::run)

    /**
     * Supply code that will run on the first and only time this Async is cancelled. If it is never
     * cancelled the supplied code never executes.
     */
    fun onCancel(executor: ExecutorService = this.executor, block: () -> Unit): Async<Result> {
        synchronized(this) {
            val oldCancelBlock = cancelBlock
            cancelBlock = {
                executor.execute(block)
                if (oldCancelBlock != null) oldCancelBlock()
            }
        }
        return this
    }

    @JvmOverloads fun <U> map(executor: ExecutorService = this.executor, mapper: Mapper<Result, U>) =
            flatMap(executor, { thrown -> Async(executor) { (mapper::map)(thrown) } })

    /** Return a new async to hold the converted result */
    fun <U> map(executor: ExecutorService = this.executor, mapper: (Result) -> U): Async<U> =
            flatMap(executor, { thrown -> Async(executor) { mapper(thrown) } })

    @JvmOverloads fun <U> flatMap(executor: ExecutorService = this.executor, mapper: Mapper<Result, Async<U>>) =
            flatMap(executor, mapper::map)

    /** Return a new async to hold the converted asynchronous result */
    fun <U> flatMap(executor: ExecutorService = this.executor, mapper: (Result) -> Async<U>): Async<U> {
        val settable = SettableAsync<U>(executor)

        val listener: TryCallback<Result> = {
            when (it) {
                is Error<Result> -> settable.setError(it.thrown)
                is Success<Result> -> {
                    try {
                        val next = mapper(it.value)
                        next.listen { settable.safeSetResult(it) }
                        settable.onCancel { -> next.cancel() }
                    } catch (thrown: Exception) {
                        settable.setError(thrown)
                    }
                }
            }
        }

        // If this is cancelled then really just stop listening
        settable.onCancel { -> unlisten(listener) }
        listen(listener)
        return settable
    }

    /** Safely set a result when it's not clear whether cancel might have occurred */
    internal fun safeSetResult(result: Try<Result>) = synchronized(this) {
        if (!isDone()) this.result = result
    }

    /** Return a new async that applies the mapper if this async results in an error */
    @JvmOverloads fun recover(executor: ExecutorService = this.executor, mapper: Mapper<Throwable, Result>) =
            recover(executor, mapper::map)

    /** Return a new async that applies the mapper if this async results in an error */
    fun recover(executor: ExecutorService = this.executor, mapper: (Throwable) -> Result) =
            flatRecover(executor, { thrown -> Async(executor) { mapper(thrown) } })

    /** Return a new async that applies the mapper if this async results in an error */
    @JvmOverloads fun flatRecover(executor: ExecutorService = this.executor, mapper: Mapper<Throwable, Async<Result>>) =
            flatRecover(executor, mapper::map)

    /** Return a new async that applies the mapper if this async results in an error */
    fun flatRecover(executor: ExecutorService = this.executor, mapper: (Throwable) -> Async<Result>): Async<Result> {
        val settable = SettableAsync<Result>(executor)
        val listener: TryCallback<Result> = {
            when (it) {
                is Error<Result> -> {
                    val next = mapper(it.thrown)
                    next.listen { settable.safeSetResult(it) }
                    settable.onCancel { -> next.cancel() }
                }
                is Success<Result> -> settable.result = it
            }
        }
        settable.onCancel { -> unlisten(listener) }
        listen(listener)
        return settable
    }

    /** Return a new Async which provides this async's success value after a delay */
    @JvmOverloads fun delay(executor: ExecutorService = this.executor, millis: Long): Async<Result> {
        val settable = SettableAsync<Result>(executor)
        val timer = Timer()

        val listener: TryCallback<Result> = {
            when (it) {
                is Error<Result> -> settable.safeSetResult(it)
                is Success<Result> -> {
                    settable.onCancel { timer.cancel() }
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            settable.safeSetResult(it)
                        }
                    }, millis)
                }
            }
        }
        settable.onCancel { unlisten(listener) }
        listen(listener)
        return settable
    }

    // Well-named callbacks for Java use
    interface Listener<in T> : ErrorListener, SuccessListener<T>

    interface SuccessListener<in T> {
        fun onSuccess(value: T)
    }

    interface ErrorListener {
        fun onError(thrown: Throwable)
    }

    interface Mapper<in T, out U> {
        @Throws(Throwable::class) fun map(from: T): U
    }

    companion object {
        private var PER_PROCESSOR = 4
        internal var DEFAULT_EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() *
                PER_PROCESSOR)

        /** Configure the default executor for this JVM */
        @JvmStatic fun setDefaultExecutor(service: ExecutorService) {
            DEFAULT_EXECUTOR = service
        }

        /** Return an async which completes with the result of the code block (including its exception if it throws) */
        operator fun <Result> invoke(executor: ExecutorService = DEFAULT_EXECUTOR, block: () -> Result) : Async<Result> =
                Async(executor, block)

        @JvmStatic @JvmOverloads
        fun <Result> work(executor: ExecutorService = DEFAULT_EXECUTOR, toRun: Callable<Result>) : Async<Result> =
                Async(executor, toRun::call)

        /** Return an async which completes when something has finished executing */
        @JvmStatic @JvmOverloads
        fun work(executor: ExecutorService = DEFAULT_EXECUTOR, toRun: Runnable): Async<Unit> =
                Async(executor, toRun::run)

        /** Return a completed async containing an error */
        @JvmStatic fun <T> error(thrown: Throwable): Async<T> {
            val async = SettableAsync<T>()
            async.result = Error(thrown)
            return async
        }

        /** Return a completed async containing success */
        @JvmStatic fun <T> success(value: T): Async<T> {
            val async = SettableAsync<T>()
            async.result = Success(value)
            return async
        }

        /** Return an async which waits before running something */
        @JvmStatic @JvmOverloads fun delay(executor: ExecutorService = DEFAULT_EXECUTOR, delay: Long,
                                           toRun: Runnable): Async<Unit> =
                delay(delay, executor, { -> toRun.run() })

        /** Return an async which waits before running something */
        @JvmStatic @JvmOverloads fun <T> delay(executor: ExecutorService = DEFAULT_EXECUTOR, delay: Long,
                                               toRun: Callable<T>) =
                delay(delay, executor, toRun::call)

        /** Return an async which waits before running something */
        fun <T> delay(delay: Long, executor: ExecutorService = DEFAULT_EXECUTOR,
                      toRun: () -> T): Async<T> =
                Async.success(Unit).delay(millis = delay).map(executor) { _ -> toRun() }

        // Consider .join, .zip, ?
    }
}

/**
 * An async carrying no result and doing no work until it is externally set
 */
class SettableAsync<Result> @JvmOverloads constructor(executor: ExecutorService = Async.DEFAULT_EXECUTOR):
        Async<Result>(executor, null) {

    /** Sets the success value. Ignored if this async is already complete. */
    fun setSuccess(value: Result) {
        synchronized(this) {
            if (isDone()) return
            result = Success(value)
        }
    }

    /** Sets an error state. Ignored if this async is already complete. */
    fun setError(thrown: Throwable) {
        synchronized(this) {
            if (isDone()) return
            result = Error(thrown)
        }
    }
}
