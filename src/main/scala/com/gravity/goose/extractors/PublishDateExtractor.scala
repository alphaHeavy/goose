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

import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import java.time._

import com.gravity.goose.extractors.PublishDateExtractor.info
import org.json4s._
import org.json4s.native.JsonMethods._
import com.gravity.goose.utils.Logging
import com.netaporter.uri.Uri
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

class PublishDateExtractor(hostnameTZ: Map[String, ZoneId]) {

  def extractCandidate(rootElement: Element, selector: String, url: Uri): Seq[ZonedDateTime] = {
    import scala.collection.JavaConversions._

    rootElement.select(selector).flatMap(item => {
      try {
      if (item.nodeName() == "time") {
        PublishDateExtractor.safeParseISO8601Date(item.attr("datetime"), url)
          .orElse(PublishDateExtractor.safeParseISO8601Date(item.html.trim, url))
          .orElse(PublishDateExtractor.safeParseISO8601Date(item.attr("content"), url))
      }
      else if(item.nodeName() == "abbr")
      {
        PublishDateExtractor.safeParseISO8601Date(item.attr("title"), url)
      }
      else if(item.nodeName() == "script" && item.attr("type") == "application/ld+json")
      {
        val json = parse(item.html,false)

        val dateCreated = json \\ "dateCreated" match {
          case JString(x) => Some(PublishDateExtractor.safeParseISO8601Date(x, url))
          case _ => None
        }
        val datePublished = json \\ "datePublished" match {
          case JString(x) => Some(PublishDateExtractor.safeParseISO8601Date(x, url))
          case _ => None
        }

        val wrapped = dateCreated.orElse(datePublished)
        if (wrapped.isDefined)
        {
          wrapped.get
        }
        else
        {
          None
        }
      }
      else if(item.nodeName() == "script" && item.html.trim.startsWith("window.__data"))
        {
          val date = s"""date_gmt":"([^"]*)""".r
          val matches = date.findAllIn(item.html)

          matches.next()
          val dt = Some(matches.group(1))

          dt match {
            case Some(x) => safeParseNoTimeZone(x, url, ZoneId.of("GMT"))
            case _ => None
          }
        }
      else if(item.nodeName() == "span")
        {
          PublishDateExtractor.safeParseISO8601Date(item.html(), url)
            .orElse(PublishDateExtractor.safeParseISO8601Date(item.attr("content"), url))
            .orElse(PublishDateExtractor.parseBusinessInsiderDate(item.attr("rel")))
        }
      else if (item.nodeName() == "p") {
        item.select("strong").remove()
        val text = item.text().replace("\u00a0", "").trim
        PublishDateExtractor.safeParseISO8601Date(text, url)
      }
      else {
        if(item.hasAttr("content")) {
          PublishDateExtractor.safeParseISO8601Date(item.attr("content"), url)
        }
        else
        {
          PublishDateExtractor.safeParseISO8601Date(item.text().trim, url)
        }
      }}
    catch {
      case e: Exception =>
        println(e)
        Nil
    }})
  }

  def extractCandidateNoTimeZone(rootElement: Element, selector: String, zoneId: ZoneId, url: Uri): Seq[ZonedDateTime] = {
    try {
      import scala.collection.JavaConverters._
      rootElement.select(selector).asScala.flatMap(item => {
        if (item.nodeName() == "time") {
          safeParseNoTimeZone(item.attr("datetime"), url, zoneId)
        }
        else if(item.nodeName() == "abbr")
        {
          safeParseNoTimeZone(item.attr("title"), url, zoneId)
        }
        else if(item.nodeName() == "script")
        {
          val json = parse(item.html,false)

          val dateCreated = json \\ "dateCreated" match {
            case JString(x) => Some(safeParseNoTimeZone(x, url, zoneId))
            case _ => None
          }
          val datePublished = json \\ "datePublished" match {
            case JString(x) => Some(safeParseNoTimeZone(x, url, zoneId))
            case _ => None
          }

          val wrapped = dateCreated.orElse(datePublished)
          if (wrapped.isDefined)
            {
              wrapped.get
            }
          else
            {
              None
            }
        }
        else if(item.nodeName() == "span")
          {
            safeParseNoTimeZone(item.html, url, zoneId)
          }
        else if (item.nodeName() == "p") {
          safeParseNoTimeZone(item.text(), url, zoneId)
        }
        else {
          if(item.hasAttr("content")) {
            safeParseNoTimeZone(item.attr("content"), url, zoneId)
          }
          else
            {
              safeParseNoTimeZone(item.text().trim, url, zoneId)
            }
        }})
    }
    catch {
      case e: Exception =>
        println(e + " " + url.toString)
        Nil
    }
  }

  //Parse
  def safeParseNoTimeZone(txt: String, url: Uri, zoneId: ZoneId): Option[ZonedDateTime] = {
    try {

      val txt2 = txt.replace("am", "AM").replace("pm", "PM").trim

      val date1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
      val date2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      val date3 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
      val date4 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
      val date5 = DateTimeFormatter.ofPattern("E',' dd M YYYY HH:mm:ss")
      val date6 = DateTimeFormatter.ofPattern("MMMM dd',' yyyy HH:mm a")
      val date7 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
      val date8 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
      val date9 = DateTimeFormatter.ofPattern("MM/dd/yyyy',' hh:mma")
      val date10 = DateTimeFormatter.ofPattern("MMMM dd',' yyyy hh:mm a")
      val date11 = DateTimeFormatter.ofPattern("MMMM dd',' yyyy 'at' hh:mm a")
      val date12 = DateTimeFormatter.ofPattern("MMMM d',' yyyy 'at' hh:mm a")
      val date13 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnn")

      val parser = new DateTimeFormatterBuilder()
        .appendOptional(date13)
        .appendOptional(date1)
        .appendOptional(date2)
        .appendOptional(date3)
        .appendOptional(date4)
        .appendOptional(date5)
        .appendOptional(date6)
        .appendOptional(date7)
        .appendOptional(date8)
        .appendOptional(date9)
        .appendOptional(date10)
        .appendOptional(date11)
        .appendOptional(date12)

        .toFormatter

      val result = LocalDateTime.parse(txt2, parser)

      Some(ZonedDateTime.of(result, zoneId).withZoneSameInstant(ZoneId.of("UTC").normalized()))
    }
    catch {
      case ex: Throwable =>
        println(ex)
        info(s"safeParseNoTimeZone `$txt` could not be parsed to date as it did not meet the ISO 8601 spec" + " " + url.toString)
        None
    }
  }

  final val pubSelectors = Seq(
    "meta[property~=article:published_time]",
    "meta[name=date]",
    "meta[name=parsely-pub-date]",
    "time",
    "meta[itemprop=datePublished]",
    "meta[name=dcterms.date]",
    "meta[property=tout:article:pubdate]",
    "meta[name=sailthru.date]",
    "script[type=application/ld+json]",
    "abbr[itemprop=datePublished]",
    "span[itemprop=datePublished]",
    "span[data-bi-format=date]",
    "span[property=article:published_time]",
    "meta[name=Search.Updated]",
    "meta[property=article:published_time]",
    "span[id=pubilsh_date]", // Miami Herald
    "span[id=publish_date]",
    "meta[name=publish_date]",
    "p[class=publish-time]",
    "p[class=updated]",
    "p[id=publish_date]",
    "span[class=timestamp]",
    "span[class=date-area-date]",
    "meta[name=sailthru.date]",
    "span[class=post-relative-date top-date]",
    "script",
    "span[class=published-date pull-right]",
    "span[itemprop=dateCreated]",
    "span[class=entry-date]",
    "meta[name=dcterms.created]",
    "meta[name=cXenseParse:recs:publishtime]"
  )

  final val modSelectors = Seq(
    "meta[property~=article:modified_time]",
    "meta[itemprop=dateModified]",
    "meta[property~=og:updated_time]",
    "meta[name=Last-Modified]",
    "script")

  def extract(rootElement: Element, url: Uri): (Option[ZonedDateTime], Option[ZonedDateTime]) = {
    url.host match {
      case Some(hostname) =>
        // A few different ways to get a date.
        def bestPubDate = pubSelectors.flatMap (extractCandidate (rootElement, _, url) ).reduceOption (PublishDateExtractor.minDate)
        def bestModDate = modSelectors.flatMap (extractCandidate (rootElement, _, url) ).reduceOption (PublishDateExtractor.minDate)

        hostnameTZ.get (hostname) match {
          case Some (x) =>
            def bestPubDateNoTimeZone = pubSelectors.flatMap (extractCandidateNoTimeZone (rootElement, _, x, url) )
              .reduceOption (PublishDateExtractor.minDate)
            def bestModDateNoTimeZone = modSelectors.flatMap (extractCandidateNoTimeZone (rootElement, _, x, url) )
              .reduceOption (PublishDateExtractor.minDate)

            (bestPubDate.orElse (bestPubDateNoTimeZone), bestModDate.orElse (bestModDateNoTimeZone))
          case None => (bestPubDate,bestModDate)
        }
      case None => (None, None)
    }
  }
}

/*abstract class PublishDateExtractor extends Extractor[Option[ZonedDateTime]] {
  /**
  * Intended to search the DOM and identify the {@link Date} of when this article was published.
  * <p>This will be called by the {@link com.jimplush.goose.ContentExtractor#extractContent(String)} method and will be passed to {@link com.jimplush.goose.Article#setPublishDate(java.util.Date)}</p>
  *
  * @param rootElement passed in from the {@link com.jimplush.goose.ContentExtractor} after the article has been parsed
  * @return {@link Date} of when this particular article was published or <code>null</code> if no date could be found.
  */
  def extract(rootElement: Element): Option[ZonedDateTime]
}*/

object PublishDateExtractor extends Logging {
  val timezones = Map("www.newsmax.com" -> ZoneId.of("America/New_York"),
    "apnews.com" -> ZoneId.of("UTC"),
    "www.miamiherald.com" -> ZoneId.of("America/New_York"),
    "news.sky.com" -> ZoneId.of("Europe/London"),
    "www.bostonglobe.com" -> ZoneId.of("America/New_York"),
    "english.yonhapnews.co.kr" -> ZoneId.of("Asia/Seoul"),
    "abcnews.go.com" -> ZoneId.of("America/New_York"),
    "chicago.suntimes.com" -> ZoneId.of("America/Chicago"),
    "money.cnn.com" -> ZoneId.of("America/New_York"),
    "www.euronews.com" -> ZoneId.of("Etc/GMT-2"),
    "www.etftrends.com" -> ZoneId.of("UTC"),
    "www.usatoday.com" -> ZoneId.of("America/New_York"),
    "www.houstonchronicle.com" -> ZoneId.of("UTC"),
    "www.theatlantic.com" -> ZoneId.of("America/New_York"),
    "www.kentucky.com" -> ZoneId.of("America/New_York"),
    "www.washingtonexaminer.com" -> ZoneId.of("UTC"))


  val logPrefix = "PublishDateExtractor: "

  //lazy val datatypeFactory: DatatypeFactory = DatatypeFactory.newInstance()

  /**
    * Helper function to return the minimum of two non-null Java Dates.
    */
  def minDate(lhs: ZonedDateTime, rhs: ZonedDateTime): ZonedDateTime = {
    if (lhs.isBefore(rhs))
      lhs
    else
      rhs
  }

  def parseBusinessInsiderDate(txt: String): Option[ZonedDateTime] = {
    if (txt == null || txt.isEmpty)
      return None

    Some(ZonedDateTime.ofInstant(Instant.ofEpochSecond(txt.toLong), ZoneId.of("America/New_York"))
      .withZoneSameInstant(ZoneOffset.UTC))
  }

  /**
    * Helper function to parse ISO 8601 date/time strings safely.
    */
  def safeParseISO8601Date(txt: String, url: Uri): Option[ZonedDateTime] = {
    if (txt == null || txt.isEmpty)
      return None

    var txt2 = txt.replace(" am ", " AM ").replace(" pm ", " PM ")
    if(txt.endsWith("-500"))
      txt2 = txt.replace("-500", "-0500")
    if(txt2.endsWith("-14400"))
      txt2 = txt2.replace("-14400","")
    if(txt2.endsWith("+0000 (UTC)"))
      txt2 = txt2.replace("+0000 (UTC)", "")

    try {
      val date1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
      val date2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")
      val date3 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmZ")
      val date4 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
      val date5 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:Z")
      val date6 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmz")
      val date7 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
      val date8 = DateTimeFormatter.ofPattern("yyyy-MM-ddzHH:mm:ssZ")
      val date9 = DateTimeFormatter.ofPattern("E',' dd M YYYY HH:mm:ss Z")
      val date10 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssVV")
      val date11 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSVV")
      val date12 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
      val date13 = DateTimeFormatter.ofPattern("E',' dd MMM yyyy HH:mm:ss z")
      val date14 = DateTimeFormatter.ofPattern("MMM d',' yyyy',' h:mm a z")
      val date15 = DateTimeFormatter.ofPattern("MMMM d',' yyyy h:mm a z")
      val date16 = DateTimeFormatter.ofPattern("E',' dd MMM yyyy HH:mm:ss X")
      val date17 = DateTimeFormatter.ofPattern("MMMM dd',' yyyy',' hh:mm:ss a z")
      val date18 = DateTimeFormatter.ofPattern("HH:mm z',' MMM dd',' yyyy")
      val date19 = DateTimeFormatter.ofPattern("yyyy-MM-ddzHH:mm:ss")
      val date20 = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss Z")
      val date21 = DateTimeFormatter.ofPattern("E MMM dd yyyy HH:mm:ss z")
      val date22 = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma z")
      val parser = new DateTimeFormatterBuilder()
        .appendOptional(date1)
        .appendOptional(date2)
        .appendOptional(date3)
        .appendOptional(date4)
        .appendOptional(date5)
        .appendOptional(date6)
        .appendOptional(date7)
        .appendOptional(date8)
        .appendOptional(date9)
        .appendOptional(date10)
        .appendOptional(date11)
        .appendOptional(date12)
        .appendOptional(date13)
        .appendOptional(date14)
        .appendOptional(date15)
        .appendOptional(date16)
        .appendOptional(date17)
        .appendOptional(date18)
        .appendOptional(date19)
        .appendOptional(date20)
        .appendOptional(date21)
        .appendOptional(date22)
        .toFormatter

      val x = ZonedDateTime.from(parser.parse(txt2)).withZoneSameInstant(ZoneId.of("UTC").normalized())
      Some(x)
    } catch {
      case ex: Exception =>
        println(ex)
        info(s"safeParseISO8601Date `$txt` could not be parsed to date as it did not meet the ISO 8601 spec" + " " + url.toString)
        None
    }
  }
}

