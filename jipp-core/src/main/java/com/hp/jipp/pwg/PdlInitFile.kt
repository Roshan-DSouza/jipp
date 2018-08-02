// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
//
// DO NOT MODIFY. Code is auto-generated by genTypes.py. Content taken from registry at
// https://www.iana.org/assignments/ipp-registrations/ipp-registrations.xml, updated on 2018-04-06
@file:Suppress("MaxLineLength", "WildcardImport")

package com.hp.jipp.pwg

import com.hp.jipp.encoding.* // ktlint-disable no-wildcard-imports

/**
 * Data object corresponding to a "pdl-init-file" collection as defined in:
 * [PWG5100.11](http://ftp.pwg.org/pub/pwg/candidates/cs-ippjobprinterext10-20101030-5100.11.pdf).
 */
@Suppress("RedundantCompanionReference", "unused")
data class PdlInitFile
@JvmOverloads constructor(
    val pdlInitFileEntry: String? = null,
    val pdlInitFileLocation: java.net.URI? = null,
    val pdlInitFileName: String? = null,
    /** Encoded form, if known. */
    val _encoded: List<Attribute<*>>? = null
) : AttributeCollection {

    /** Produce an attribute list from members, or return the original [_encoded] attribute list if present. */
    override val attributes: List<Attribute<*>> by lazy {
        _encoded ?: listOfNotNull(
            pdlInitFileEntry?.let { Members.pdlInitFileEntry.of(it) },
            pdlInitFileLocation?.let { Members.pdlInitFileLocation.of(it) },
            pdlInitFileName?.let { Members.pdlInitFileName.of(it) }
        )
    }

    /** Type for attributes of this collection */
    class Type(override val name: String) : AttributeCollection.Type<PdlInitFile>(Members)

    /** All member names as strings. */
    object Name {
        /** "pdl-init-file-entry" member name */
        const val pdlInitFileEntry = "pdl-init-file-entry"
        /** "pdl-init-file-location" member name */
        const val pdlInitFileLocation = "pdl-init-file-location"
        /** "pdl-init-file-name" member name */
        const val pdlInitFileName = "pdl-init-file-name"
    }

    /** Builder for immutable [PdlInitFile] objects. */
    class Builder() {
        /** Constructs a new [Builder] pre-initialized with values in [source]. */
        constructor(source: PdlInitFile) : this() {
            pdlInitFileEntry = source.pdlInitFileEntry
            pdlInitFileLocation = source.pdlInitFileLocation
            pdlInitFileName = source.pdlInitFileName
        }
        var pdlInitFileEntry: String? = null
        var pdlInitFileLocation: java.net.URI? = null
        var pdlInitFileName: String? = null

        /** Return a new [PdlInitFile] object containing all values initialized in this builder. */
        fun build() = PdlInitFile(
            pdlInitFileEntry,
            pdlInitFileLocation,
            pdlInitFileName
        )
    }

    companion object Members : AttributeCollection.Converter<PdlInitFile> {
        override fun convert(attributes: List<Attribute<*>>): PdlInitFile =
            PdlInitFile(
                extractOne(attributes, pdlInitFileEntry)?.value,
                extractOne(attributes, pdlInitFileLocation),
                extractOne(attributes, pdlInitFileName)?.value,
                _encoded = attributes)
        /**
         * "pdl-init-file-entry" member type.
         */
        @JvmField val pdlInitFileEntry = NameType(Name.pdlInitFileEntry)
        /**
         * "pdl-init-file-location" member type.
         */
        @JvmField val pdlInitFileLocation = UriType(Name.pdlInitFileLocation)
        /**
         * "pdl-init-file-name" member type.
         */
        @JvmField val pdlInitFileName = NameType(Name.pdlInitFileName)
    }
}