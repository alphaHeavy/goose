package com.gravity.goose

import java.time.ZonedDateTime
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
}
