/**
 * Licensed to Gravity.com under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  Gravity.com licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gravity.goose.extractors

import java.util.Date
import javax.xml.datatype.DatatypeFactory

import com.gravity.goose.utils.Logging
import org.joda.time.format.{DateTimeFormat, DateTimeFormatterBuilder, DateTimeParser, ISODateTimeFormat}
import org.jsoup.nodes.Element

/**
* Implement this class to extract the {@link Date} of when this article was published.
*/
/**
 * Created by IntelliJ IDEA.
 * User: robbie
 * Date: 5/19/11
 * Time: 2:50 PM
 */
abstract class PublishDateExtractor extends Extractor[Date] {
  /**
  * Intended to search the DOM and identify the {@link Date} of when this article was published.
  * <p>This will be called by the {@link com.jimplush.goose.ContentExtractor#extractContent(String)} method and will be passed to {@link com.jimplush.goose.Article#setPublishDate(java.util.Date)}</p>
  *
  * @param rootElement passed in from the {@link com.jimplush.goose.ContentExtractor} after the article has been parsed
  * @return {@link Date} of when this particular article was published or <code>null</code> if no date could be found.
  */
  def extract(rootElement: Element): Date
}

object PublishDateExtractor extends Logging {
  val logPrefix = "PublishDateExtractor: "

  lazy val datatypeFactory: DatatypeFactory = DatatypeFactory.newInstance()

  /**
    * Helper function to return the minimum of two non-null Java Dates.
    */
  def minDate(lhs: java.util.Date, rhs: java.util.Date): java.util.Date = {
    if (lhs.getTime < rhs.getTime)
      lhs
    else
      rhs
  }

  /**
    * Helper function to parse ISO 8601 date/time strings safely.
    */
  def safeParseISO8601Date(txt: String): Option[java.util.Date] = {
    if (txt == null || txt.isEmpty)
      return None

    try {
      val iso8601format1 = ISODateTimeFormat.basicDateTime()
      val iso8601format2 = ISODateTimeFormat.basicDateTimeNoMillis()
      val iso8601format3 = ISODateTimeFormat.dateTimeNoMillis()
      val date1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
      val date2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss Z")
      val date3 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mmZ")
      val date4 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
      val parser = new DateTimeFormatterBuilder().append(null, Array(iso8601format1.getParser,
        iso8601format2.getParser,iso8601format3.getParser, date1.getParser, date2.getParser, date3.getParser,
        date4.getParser)).toFormatter
      Option(parser.parseDateTime(txt)).map(x=>x.toDate)
    } catch {
      case ex: Exception =>
        info(s"`$txt` could not be parsed to date as it did not meet the ISO 8601 spec")
        None
    }
  }
}

