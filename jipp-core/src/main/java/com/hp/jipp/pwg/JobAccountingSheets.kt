// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
//
// DO NOT MODIFY. Code is auto-generated by genTypes.py. Content taken from registry at
// https://www.iana.org/assignments/ipp-registrations/ipp-registrations.xml, updated on 2018-04-06
@file:Suppress("MaxLineLength", "WildcardImport")

package com.hp.jipp.pwg

import com.hp.jipp.encoding.* // ktlint-disable no-wildcard-imports

/**
 * Data object corresponding to a "job-accounting-sheets" collection as defined in:
 * [PWG5100.3](http://ftp.pwg.org/pub/pwg/candidates/cs-ippprodprint10-20010212-5100.3.pdf),
 * [RFC8011](http://www.iana.org/go/rfc8011).
 */
@Suppress("RedundantCompanionReference", "unused")
data class JobAccountingSheets
@JvmOverloads constructor(
    /** May contain any keyword from [OutputBin] or a name. */
    val jobAccountingOutputBin: String? = null,
    /** May contain any keyword from [JobAccountingSheetsType] or a name. */
    val jobAccountingSheetsType: String? = null,
    /** May contain any keyword from [Media] or a name. */
    val media: String? = null,
    val mediaCol: MediaCol? = null,
    /** Encoded form, if known. */
    val _encoded: List<Attribute<*>>? = null
) : AttributeCollection {

    /** Produce an attribute list from members, or return the original [_encoded] attribute list if present. */
    override val attributes: List<Attribute<*>> by lazy {
        _encoded ?: listOfNotNull(
            jobAccountingOutputBin?.let { Members.jobAccountingOutputBin.of(it) },
            jobAccountingSheetsType?.let { Members.jobAccountingSheetsType.of(it) },
            media?.let { Members.media.of(it) },
            mediaCol?.let { Members.mediaCol.of(it) }
        )
    }

    /** Type for attributes of this collection */
    class Type(override val name: String) : AttributeCollection.Type<JobAccountingSheets>(Members)

    /** All member names as strings. */
    object Name {
        /** "job-accounting-output-bin" member name */
        const val jobAccountingOutputBin = "job-accounting-output-bin"
        /** "job-accounting-sheets-type" member name */
        const val jobAccountingSheetsType = "job-accounting-sheets-type"
        /** "media" member name */
        const val media = "media"
        /** "media-col" member name */
        const val mediaCol = "media-col"
    }

    /** Builder for immutable [JobAccountingSheets] objects. */
    class Builder() {
        /** Constructs a new [Builder] pre-initialized with values in [source]. */
        constructor(source: JobAccountingSheets) : this() {
            jobAccountingOutputBin = source.jobAccountingOutputBin
            jobAccountingSheetsType = source.jobAccountingSheetsType
            media = source.media
            mediaCol = source.mediaCol
        }
        /** May contain any keyword from [OutputBin] or a name. */
        var jobAccountingOutputBin: String? = null
        /** May contain any keyword from [JobAccountingSheetsType] or a name. */
        var jobAccountingSheetsType: String? = null
        /** May contain any keyword from [Media] or a name. */
        var media: String? = null
        var mediaCol: MediaCol? = null

        /** Return a new [JobAccountingSheets] object containing all values initialized in this builder. */
        fun build() = JobAccountingSheets(
            jobAccountingOutputBin,
            jobAccountingSheetsType,
            media,
            mediaCol
        )
    }

    companion object Members : AttributeCollection.Converter<JobAccountingSheets> {
        override fun convert(attributes: List<Attribute<*>>): JobAccountingSheets =
            JobAccountingSheets(
                extractOne(attributes, jobAccountingOutputBin),
                extractOne(attributes, jobAccountingSheetsType),
                extractOne(attributes, media),
                extractOne(attributes, mediaCol),
                _encoded = attributes)
        /**
         * "job-accounting-output-bin" member type.
         * May contain any keyword from [OutputBin] or a name.
         */
        @JvmField val jobAccountingOutputBin = KeywordType(Name.jobAccountingOutputBin)
        /**
         * "job-accounting-sheets-type" member type.
         * May contain any keyword from [JobAccountingSheetsType] or a name.
         */
        @JvmField val jobAccountingSheetsType = KeywordType(Name.jobAccountingSheetsType)
        /**
         * "media" member type.
         * May contain any keyword from [Media] or a name.
         */
        @JvmField val media = KeywordType(Name.media)
        /**
         * "media-col" member type.
         */
        @JvmField val mediaCol = MediaCol.Type(Name.mediaCol)
    }
}