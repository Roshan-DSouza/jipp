package com.hp.jipp.encoding

import com.google.common.base.Optional
import com.hp.jipp.util.BuildError
import com.hp.jipp.util.Hook

import java.util.Arrays

/**
 * Associates a specific tag and name such that an attribute can be safely created or retrieved from a group
 */
open class AttributeType<T>(val encoder: Attribute.BaseEncoder<T>, val tag: Tag, val name: String) {

    init {
        if (!(encoder.valid(tag) || Hook.`is`(Attribute.HOOK_ALLOW_BUILD_INVALID_TAGS))) {
            throw BuildError("Invalid tag $tag for encoder $encoder")
        }
    }

    /** Create an attribute of this attribute type with supplied values */
    fun of(vararg values: T): Attribute<T> = of(Arrays.asList(*values))

    /** Create an attribute of this attribute type with supplied values */
    fun of(values: List<T>): Attribute<T> = Attribute(tag, name, values, encoder)

    /** Return true if the attribute has a matching encoder */
    fun isValid(attribute: Attribute<*>): Boolean {
        return attribute.encoder == encoder
    }

    /** If possible, convert the supplied attribute into an attribute of this type. */
    // TODO: Replace Optional with Attribute<T>?
    open fun of(attribute: Attribute<*>): Optional<Attribute<T>> {
        if (attribute.encoder === encoder) {
            return Optional.of(of(attribute.values.map {
                @Suppress("UNCHECKED_CAST")
                it as T
            }))
        } else {
            return Optional.absent<Attribute<T>>()
        }
    }
}
