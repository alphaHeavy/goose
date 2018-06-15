package com.gravity.goose

import java.time.{LocalDateTime, ZonedDateTime}
import java.time.format.DateTimeFormatter

import org.junit.Test

class DateParserTests {
  @Test
  def abc(): Unit = {
    val string = "Jun 26, 2017, 10:39 PM ET"
    val dateFormat = DateTimeFormatter.ofPattern("MMM d',' yyyy',' h:mm a z")
    val result = ZonedDateTime.from(dateFormat.parse(string))
    assert(result != null)
  }

  @Test
  def nikkei(): Unit = {
    val string = "August 2, 2017 6:44 am JST"
    val dateFormat = DateTimeFormatter.ofPattern("MMMM d',' yyyy h:mm a z")
    dateFormat.parse(string)
    val result = ZonedDateTime.from(dateFormat.parse(string))
    assert(result != null)
  }

  @Test
  def suntimes(): Unit = {
    val string = "06/04/2017, 01:41am".replace("am","AM")
    val dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy',' HH:mma")
    dateFormat.parse(string)
    val result = ZonedDateTime.from(dateFormat.parse(string))
    assert(result != null)
  }

  @Test
  def maimiherald2(): Unit = {
    val string = "June 12, 2017 07:16 PM"
    val dateFormat = DateTimeFormatter.ofPattern("MMMM dd',' yyyy hh:mm a")
    val result = LocalDateTime.parse(string, dateFormat)
    assert(result != null)
  }

  def miamiherald2(): Unit = {
    val string = "June 09, 2017 10:54 AM"
    val dateFormat = DateTimeFormatter.ofPattern("MMMM dd',' yyyy hh:mm a")
    val result = LocalDateTime.parse(string, dateFormat)
    assert(result != null)
  }

  @Test
  def ndtv(): Unit = {
    val string = "Thu, 01 Jun 2017 11:12:55 +0530"
    val dateFormat = DateTimeFormatter.ofPattern("E',' dd MMM yyyy HH:mm:ss X")
    val result = LocalDateTime.parse(string, dateFormat)
    assert(result != null)
  }

  @Test
  def nasdaq(): Unit = {
    val string = "June 05, 2017, 08:30:00 AM EDT"
    val dateFormat = DateTimeFormatter.ofPattern("MMMM dd',' yyyy',' hh:mm:ss a z")
    val result = LocalDateTime.parse(string, dateFormat)
    assert(result != null)
  }

  @Test
  def rt(): Unit = {
    val string = "21:05 GMT, Jun 15, 2017"
    val dateFormat = DateTimeFormatter.ofPattern("HH:mm z',' MMM dd',' yyyy")
    val result = LocalDateTime.parse(string, dateFormat)
    assert(result != null)
  }

  @Test
  def etftrends1(): Unit = {
    val string = "June 7, 2017 at 11:16 AM"
    val dateFormat = DateTimeFormatter.ofPattern("MMMM d',' yyyy 'at' hh:mm a")
    val result = LocalDateTime.parse(string, dateFormat)
    assert(result != null)
  }


  @Test
  def usatoday1(): Unit = {
    val string = "2016-06-06T06:38:55.8470000"
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnn")
    val result = LocalDateTime.parse(string, dateFormat)
    assert(result != null)
  }

  @Test
  def nbcnews1(): Unit = {
    val string = "Tue May 01 2018 05:55:14 GMT"
    val dateFormat = DateTimeFormatter.ofPattern("E MMM dd yyyy HH:mm:ss z")
    val result = LocalDateTime.parse(string, dateFormat)
    assert(result != null)
  }
}
