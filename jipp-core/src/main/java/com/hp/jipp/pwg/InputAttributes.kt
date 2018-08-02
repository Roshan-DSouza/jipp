// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
//
// DO NOT MODIFY. Code is auto-generated by genTypes.py. Content taken from registry at
// https://www.iana.org/assignments/ipp-registrations/ipp-registrations.xml, updated on 2018-04-06
@file:Suppress("MaxLineLength", "WildcardImport")

package com.hp.jipp.pwg

import com.hp.jipp.encoding.* // ktlint-disable no-wildcard-imports

/**
 * Data object corresponding to a "input-attributes" collection as defined in:
 * [PWG5100.15](http://ftp.pwg.org/pub/pwg/candidates/cs-ippfaxout10-20131115-5100.15.pdf).
 */
@Suppress("RedundantCompanionReference", "unused")
data class InputAttributes
@JvmOverloads constructor(
    val inputAutoScaling: Boolean? = null,
    val inputAutoSkewCorrection: Boolean? = null,
    val inputBrightness: Int? = null,
    /** May contain any keyword from [InputColorMode]. */
    val inputColorMode: String? = null,
    /** May contain any keyword from [InputContentType]. */
    val inputContentType: String? = null,
    val inputContrast: Int? = null,
    /** May contain any keyword from [InputFilmScanMode]. */
    val inputFilmScanMode: String? = null,
    val inputImagesToTransfer: Int? = null,
    /** May contain any keyword from [Media] or a name. */
    val inputMedia: String? = null,
    val inputOrientationRequested: Orientation? = null,
    val inputQuality: PrintQuality? = null,
    val inputResolution: Resolution? = null,
    val inputScalingHeight: Int? = null,
    val inputScalingWidth: Int? = null,
    val inputScanRegions: List<InputScanRegions>? = null,
    val inputSharpness: Int? = null,
    /** May contain any keyword from [Sides]. */
    val inputSides: String? = null,
    /** May contain any keyword from [InputSource]. */
    val inputSource: String? = null,
    /** Encoded form, if known. */
    val _encoded: List<Attribute<*>>? = null
) : AttributeCollection {

    /** Produce an attribute list from members, or return the original [_encoded] attribute list if present. */
    override val attributes: List<Attribute<*>> by lazy {
        _encoded ?: listOfNotNull(
            inputAutoScaling?.let { Members.inputAutoScaling.of(it) },
            inputAutoSkewCorrection?.let { Members.inputAutoSkewCorrection.of(it) },
            inputBrightness?.let { Members.inputBrightness.of(it) },
            inputColorMode?.let { Members.inputColorMode.of(it) },
            inputContentType?.let { Members.inputContentType.of(it) },
            inputContrast?.let { Members.inputContrast.of(it) },
            inputFilmScanMode?.let { Members.inputFilmScanMode.of(it) },
            inputImagesToTransfer?.let { Members.inputImagesToTransfer.of(it) },
            inputMedia?.let { Members.inputMedia.of(it) },
            inputOrientationRequested?.let { Members.inputOrientationRequested.of(it) },
            inputQuality?.let { Members.inputQuality.of(it) },
            inputResolution?.let { Members.inputResolution.of(it) },
            inputScalingHeight?.let { Members.inputScalingHeight.of(it) },
            inputScalingWidth?.let { Members.inputScalingWidth.of(it) },
            inputScanRegions?.let { Members.inputScanRegions.of(it) },
            inputSharpness?.let { Members.inputSharpness.of(it) },
            inputSides?.let { Members.inputSides.of(it) },
            inputSource?.let { Members.inputSource.of(it) }
        )
    }

    /** Type for attributes of this collection */
    class Type(override val name: String) : AttributeCollection.Type<InputAttributes>(Members)

    /** All member names as strings. */
    object Name {
        /** "input-auto-scaling" member name */
        const val inputAutoScaling = "input-auto-scaling"
        /** "input-auto-skew-correction" member name */
        const val inputAutoSkewCorrection = "input-auto-skew-correction"
        /** "input-brightness" member name */
        const val inputBrightness = "input-brightness"
        /** "input-color-mode" member name */
        const val inputColorMode = "input-color-mode"
        /** "input-content-type" member name */
        const val inputContentType = "input-content-type"
        /** "input-contrast" member name */
        const val inputContrast = "input-contrast"
        /** "input-film-scan-mode" member name */
        const val inputFilmScanMode = "input-film-scan-mode"
        /** "input-images-to-transfer" member name */
        const val inputImagesToTransfer = "input-images-to-transfer"
        /** "input-media" member name */
        const val inputMedia = "input-media"
        /** "input-orientation-requested" member name */
        const val inputOrientationRequested = "input-orientation-requested"
        /** "input-quality" member name */
        const val inputQuality = "input-quality"
        /** "input-resolution" member name */
        const val inputResolution = "input-resolution"
        /** "input-scaling-height" member name */
        const val inputScalingHeight = "input-scaling-height"
        /** "input-scaling-width" member name */
        const val inputScalingWidth = "input-scaling-width"
        /** "input-scan-regions" member name */
        const val inputScanRegions = "input-scan-regions"
        /** "input-sharpness" member name */
        const val inputSharpness = "input-sharpness"
        /** "input-sides" member name */
        const val inputSides = "input-sides"
        /** "input-source" member name */
        const val inputSource = "input-source"
    }

    /** Builder for immutable [InputAttributes] objects. */
    class Builder() {
        /** Constructs a new [Builder] pre-initialized with values in [source]. */
        constructor(source: InputAttributes) : this() {
            inputAutoScaling = source.inputAutoScaling
            inputAutoSkewCorrection = source.inputAutoSkewCorrection
            inputBrightness = source.inputBrightness
            inputColorMode = source.inputColorMode
            inputContentType = source.inputContentType
            inputContrast = source.inputContrast
            inputFilmScanMode = source.inputFilmScanMode
            inputImagesToTransfer = source.inputImagesToTransfer
            inputMedia = source.inputMedia
            inputOrientationRequested = source.inputOrientationRequested
            inputQuality = source.inputQuality
            inputResolution = source.inputResolution
            inputScalingHeight = source.inputScalingHeight
            inputScalingWidth = source.inputScalingWidth
            inputScanRegions = source.inputScanRegions
            inputSharpness = source.inputSharpness
            inputSides = source.inputSides
            inputSource = source.inputSource
        }
        var inputAutoScaling: Boolean? = null
        var inputAutoSkewCorrection: Boolean? = null
        var inputBrightness: Int? = null
        /** May contain any keyword from [InputColorMode]. */
        var inputColorMode: String? = null
        /** May contain any keyword from [InputContentType]. */
        var inputContentType: String? = null
        var inputContrast: Int? = null
        /** May contain any keyword from [InputFilmScanMode]. */
        var inputFilmScanMode: String? = null
        var inputImagesToTransfer: Int? = null
        /** May contain any keyword from [Media] or a name. */
        var inputMedia: String? = null
        var inputOrientationRequested: Orientation? = null
        var inputQuality: PrintQuality? = null
        var inputResolution: Resolution? = null
        var inputScalingHeight: Int? = null
        var inputScalingWidth: Int? = null
        var inputScanRegions: List<InputScanRegions>? = null
        var inputSharpness: Int? = null
        /** May contain any keyword from [Sides]. */
        var inputSides: String? = null
        /** May contain any keyword from [InputSource]. */
        var inputSource: String? = null

        /** Return a new [InputAttributes] object containing all values initialized in this builder. */
        fun build() = InputAttributes(
            inputAutoScaling,
            inputAutoSkewCorrection,
            inputBrightness,
            inputColorMode,
            inputContentType,
            inputContrast,
            inputFilmScanMode,
            inputImagesToTransfer,
            inputMedia,
            inputOrientationRequested,
            inputQuality,
            inputResolution,
            inputScalingHeight,
            inputScalingWidth,
            inputScanRegions,
            inputSharpness,
            inputSides,
            inputSource
        )
    }

    companion object Members : AttributeCollection.Converter<InputAttributes> {
        override fun convert(attributes: List<Attribute<*>>): InputAttributes =
            InputAttributes(
                extractOne(attributes, inputAutoScaling),
                extractOne(attributes, inputAutoSkewCorrection),
                extractOne(attributes, inputBrightness),
                extractOne(attributes, inputColorMode),
                extractOne(attributes, inputContentType),
                extractOne(attributes, inputContrast),
                extractOne(attributes, inputFilmScanMode),
                extractOne(attributes, inputImagesToTransfer),
                extractOne(attributes, inputMedia),
                extractOne(attributes, inputOrientationRequested),
                extractOne(attributes, inputQuality),
                extractOne(attributes, inputResolution),
                extractOne(attributes, inputScalingHeight),
                extractOne(attributes, inputScalingWidth),
                extractAll(attributes, inputScanRegions),
                extractOne(attributes, inputSharpness),
                extractOne(attributes, inputSides),
                extractOne(attributes, inputSource),
                _encoded = attributes)
        /**
         * "input-auto-scaling" member type.
         */
        @JvmField val inputAutoScaling = BooleanType(Name.inputAutoScaling)
        /**
         * "input-auto-skew-correction" member type.
         */
        @JvmField val inputAutoSkewCorrection = BooleanType(Name.inputAutoSkewCorrection)
        /**
         * "input-brightness" member type.
         */
        @JvmField val inputBrightness = IntType(Name.inputBrightness)
        /**
         * "input-color-mode" member type.
         * May contain any keyword from [InputColorMode].
         */
        @JvmField val inputColorMode = KeywordType(Name.inputColorMode)
        /**
         * "input-content-type" member type.
         * May contain any keyword from [InputContentType].
         */
        @JvmField val inputContentType = KeywordType(Name.inputContentType)
        /**
         * "input-contrast" member type.
         */
        @JvmField val inputContrast = IntType(Name.inputContrast)
        /**
         * "input-film-scan-mode" member type.
         * May contain any keyword from [InputFilmScanMode].
         */
        @JvmField val inputFilmScanMode = KeywordType(Name.inputFilmScanMode)
        /**
         * "input-images-to-transfer" member type.
         */
        @JvmField val inputImagesToTransfer = IntType(Name.inputImagesToTransfer)
        /**
         * "input-media" member type.
         * May contain any keyword from [Media] or a name.
         */
        @JvmField val inputMedia = KeywordType(Name.inputMedia)
        /**
         * "input-orientation-requested" member type.
         */
        @JvmField val inputOrientationRequested = Orientation.Type(Name.inputOrientationRequested)
        /**
         * "input-quality" member type.
         */
        @JvmField val inputQuality = PrintQuality.Type(Name.inputQuality)
        /**
         * "input-resolution" member type.
         */
        @JvmField val inputResolution = ResolutionType(Name.inputResolution)
        /**
         * "input-scaling-height" member type.
         */
        @JvmField val inputScalingHeight = IntType(Name.inputScalingHeight)
        /**
         * "input-scaling-width" member type.
         */
        @JvmField val inputScalingWidth = IntType(Name.inputScalingWidth)
        /**
         * "input-scan-regions" member type.
         */
        @JvmField val inputScanRegions = InputScanRegions.Type(Name.inputScanRegions)
        /**
         * "input-sharpness" member type.
         */
        @JvmField val inputSharpness = IntType(Name.inputSharpness)
        /**
         * "input-sides" member type.
         * May contain any keyword from [Sides].
         */
        @JvmField val inputSides = KeywordType(Name.inputSides)
        /**
         * "input-source" member type.
         * May contain any keyword from [InputSource].
         */
        @JvmField val inputSource = KeywordType(Name.inputSource)
    }

    /**
     * Data object corresponding to a "input-scan-regions" collection.
     */
    @Suppress("RedundantCompanionReference", "unused")
    data class InputScanRegions
    @JvmOverloads constructor(
        val xDimension: Int? = null,
        val xOrigin: Int? = null,
        val yDimension: Int? = null,
        val yOrigin: Int? = null,
        /** Encoded form, if known. */
        val _encoded: List<Attribute<*>>? = null
    ) : AttributeCollection {

        /** Produce an attribute list from members, or return the original [_encoded] attribute list if present. */
        override val attributes: List<Attribute<*>> by lazy {
            _encoded ?: listOfNotNull(
                xDimension?.let { Members.xDimension.of(it) },
                xOrigin?.let { Members.xOrigin.of(it) },
                yDimension?.let { Members.yDimension.of(it) },
                yOrigin?.let { Members.yOrigin.of(it) }
            )
        }

        /** Type for attributes of this collection */
        class Type(override val name: String) : AttributeCollection.Type<InputScanRegions>(Members)

        /** All member names as strings. */
        object Name {
            /** "x-dimension" member name */
            const val xDimension = "x-dimension"
            /** "x-origin" member name */
            const val xOrigin = "x-origin"
            /** "y-dimension" member name */
            const val yDimension = "y-dimension"
            /** "y-origin" member name */
            const val yOrigin = "y-origin"
        }

        /** Builder for immutable [InputScanRegions] objects. */
        class Builder() {
            /** Constructs a new [Builder] pre-initialized with values in [source]. */
            constructor(source: InputScanRegions) : this() {
                xDimension = source.xDimension
                xOrigin = source.xOrigin
                yDimension = source.yDimension
                yOrigin = source.yOrigin
            }
            var xDimension: Int? = null
            var xOrigin: Int? = null
            var yDimension: Int? = null
            var yOrigin: Int? = null

            /** Return a new [InputScanRegions] object containing all values initialized in this builder. */
            fun build() = InputScanRegions(
                xDimension,
                xOrigin,
                yDimension,
                yOrigin
            )
        }

        companion object Members : AttributeCollection.Converter<InputScanRegions> {
            override fun convert(attributes: List<Attribute<*>>): InputScanRegions =
                InputScanRegions(
                    extractOne(attributes, xDimension),
                    extractOne(attributes, xOrigin),
                    extractOne(attributes, yDimension),
                    extractOne(attributes, yOrigin),
                    _encoded = attributes)
            /**
             * "x-dimension" member type.
             */
            @JvmField val xDimension = IntType(Name.xDimension)
            /**
             * "x-origin" member type.
             */
            @JvmField val xOrigin = IntType(Name.xOrigin)
            /**
             * "y-dimension" member type.
             */
            @JvmField val yDimension = IntType(Name.yDimension)
            /**
             * "y-origin" member type.
             */
            @JvmField val yOrigin = IntType(Name.yOrigin)
        }
    }
}