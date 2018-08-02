// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
//
// DO NOT MODIFY. Code is auto-generated by genTypes.py. Content taken from registry at
// https://www.iana.org/assignments/ipp-registrations/ipp-registrations.xml, updated on 2018-04-06

package com.hp.jipp.pwg;

/**
 * Values applicable for "job-delay-output-until" keywords, as defined in
 * <a href="http://ftp.pwg.org/pub/pwg/candidates/cs-ippjobprinterext10-20101030-5100.11.pdf">PWG5100.11</a>.
 *
 * Also used by:
 *   * `job-delay-output-until-default`
 *   * `job-delay-output-until-supported`
 */
public class JobDelayOutputUntil {
    public static final String dayTime = "day-time";
    public static final String evening = "evening";
    public static final String indefinite = "indefinite";
    public static final String night = "night";
    public static final String noDelayOutput = "no-delay-output";
    public static final String secondShift = "second-shift";
    public static final String thirdShift = "third-shift";
    public static final String weekend = "weekend";
}