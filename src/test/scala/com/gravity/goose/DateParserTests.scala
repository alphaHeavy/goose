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
  def maimiherald(): Unit = {
    val string = "June 12, 2017 07:16 PM"
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

}
