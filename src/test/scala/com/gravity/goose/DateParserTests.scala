package com.gravity.goose

import java.time.{LocalDateTime, ZoneId, ZonedDateTime}
import java.time.format.DateTimeFormatter

import com.gravity.goose.extractors.PublishDateExtractor
import com.netaporter.uri.Uri
import org.junit.Test

class DateParserTests {
  @Test
  def abc(): Unit = {
    val string = "Jun 26, 2017, 10:39 PM ET"
    val result = PublishDateExtractor.safeParseISO8601Date(string, Uri.parse("https://www.google.com"))
    assert(result.isDefined)
  }

  @Test
  def nikkei(): Unit = {
    val string = "August 2, 2017 6:44 am JST"
    val result = PublishDateExtractor.safeParseISO8601Date(string, Uri.parse("https://www.google.com"))
    assert(result.isDefined)
  }

  @Test
  def suntimes(): Unit = {
    val string = "06/04/2017, 01:41am".replace("am","AM")
    val result = PublishDateExtractor.safeParseNoTimeZone(string, Uri.parse("https://www.google.com"), ZoneId.of("America/New_York"))
    assert(result.isDefined)
  }

  @Test
  def maimiherald2(): Unit = {
    val string = "June 12, 2017 07:16 PM"
    val result = PublishDateExtractor.safeParseNoTimeZone(string, Uri.parse("https://www.google.com"), ZoneId.of("America/New_York"))
    assert(result.isDefined)
  }

  @Test
  def ndtv(): Unit = {
    val string = "Thu, 01 Jun 2017 11:12:55 +0530"
    val result = PublishDateExtractor.safeParseISO8601Date(string, Uri.parse("https://www.google.com"))
    assert(result.isDefined)
  }

  @Test
  def nasdaq(): Unit = {
    val string = "June 05, 2017, 08:30:00 AM EDT"
    val result = PublishDateExtractor.safeParseISO8601Date(string, Uri.parse("https://www.google.com"))
    assert(result.isDefined)
  }

  @Test
  def rt(): Unit = {
    val string = "21:05 GMT, Jun 15, 2017"
    val result = PublishDateExtractor.safeParseISO8601Date(string, Uri.parse("https://www.google.com"))
    assert(result.isDefined)
  }

  @Test
  def etftrends1(): Unit = {
    val string = "June 7, 2017 at 11:16 AM"
    val result = PublishDateExtractor.safeParseNoTimeZone(string, Uri.parse("https://www.google.com"), ZoneId.of("America/New_York"))
    assert(result.isDefined)
  }


  @Test
  def usatoday1(): Unit = {
    val string = "2016-06-06T06:38:55.8470000"
    val result = PublishDateExtractor.safeParseNoTimeZone(string, Uri.parse("https://www.google.com"), ZoneId.of("America/New_York"))
    assert(result.isDefined)
  }

  @Test
  def nbcnews1(): Unit = {
    val string = "Tue May 01 2018 05:55:14 GMT"
    val result = PublishDateExtractor.safeParseISO8601Date(string, Uri.parse("https://www.google.com"))
    assert(result.isDefined)
  }

  @Test
  def ktvu1(): Unit = {
    val string = "Jun 03 2017 12:56PM PDT"
    val result = PublishDateExtractor.safeParseISO8601Date(string, Uri.parse("https://www.google.com"))
    assert(result.isDefined)
  }

  @Test
  def apnews1(): Unit = {
    val string = "2017-08-23 19:42:06"
    val result = PublishDateExtractor.safeParseNoTimeZone(string, Uri.parse("https://www.google.com"), ZoneId.of("America/New_York"))
    assert(result.isDefined)
  }

  @Test
  def logs1(): Unit = {
    val string = "2019-09-24T22:36:43.981548Z"
    val result = PublishDateExtractor.safeParseISO8601Date(string, Uri.parse("https://www.google.com"))
    assert(result.isDefined)
  }

  @Test
  def logs2(): Unit = {
    val string = "Oct 1, 2019 8:49 a.m. ET"
    val result = PublishDateExtractor.safeParseISO8601Date(string, Uri.parse("https://www.google.com"))
    assert(result.isDefined)
  }

  @Test
  def suntimes1(): Unit = {
    val string = "06/04/2017, 01:41am"
    val result = PublishDateExtractor.safeParseNoTimeZone(string, Uri.parse("https://www.google.com"), ZoneId.of("America/New_York"))
    assert(result.isDefined)
  }

  @Test
  def businessinsider(): Unit = {
    val string = "2019-01-19EST16:09:00-1800"
    val result = PublishDateExtractor.safeParseISO8601Date(string, Uri.parse("https://www.google.com"))
    assert(result.isDefined)
  }
}
