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
            case Some(x) => PublishDateExtractor.safeParseNoTimeZone(x, url, ZoneId.of("GMT"))
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
      else if(item.hasAttr("data-timestamp"))
      {
        PublishDateExtractor.safeParseISO8601Date(item.attr("data-timestamp"), url)
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
        Nil
    }})
  }

  def extractCandidateNoTimeZone(rootElement: Element, selector: String, zoneId: ZoneId, url: Uri): Seq[ZonedDateTime] = {
    try {
      import scala.collection.JavaConverters._
      rootElement.select(selector).asScala.flatMap(item => {
        if (item.nodeName() == "time") {
          PublishDateExtractor.safeParseNoTimeZone(item.attr("datetime"), url, zoneId)
        }
        else if(item.nodeName() == "abbr")
        {
          PublishDateExtractor.safeParseNoTimeZone(item.attr("title"), url, zoneId)
        }
        else if(item.nodeName() == "script")
        {
          val json = parse(item.html,false)

          val dateCreated = json \\ "dateCreated" match {
            case JString(x) => Some(PublishDateExtractor.safeParseNoTimeZone(x, url, zoneId))
            case _ => None
          }
          val datePublished = json \\ "datePublished" match {
            case JString(x) => Some(PublishDateExtractor.safeParseNoTimeZone(x, url, zoneId))
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
            PublishDateExtractor.safeParseNoTimeZone(item.html, url, zoneId)
          }
        else if (item.nodeName() == "p") {
          PublishDateExtractor.safeParseNoTimeZone(item.text(), url, zoneId)
        }
        else if(item.hasAttr("content")) {
          PublishDateExtractor.safeParseNoTimeZone(item.attr("content"), url, zoneId)
        }
        else {
            PublishDateExtractor.safeParseNoTimeZone(item.text().trim, url, zoneId)
        }})
    }
    catch {
      case e: Exception =>
        Nil
    }
  }

  final val pubSelectors = Seq(
    "div[class=byline-timestamp]",
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
    "p[class=published-date]",
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

            val pub = bestPubDate.orElse (bestPubDateNoTimeZone)
            val upd = bestModDate.orElse (bestModDateNoTimeZone)

            (pub.orElse(upd), upd)
          case None => (bestPubDate.orElse(bestModDate),bestModDate)
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
    "www.washingtonexaminer.com" -> ZoneId.of("UTC"),
    "www.mcclatchydc.com" -> ZoneId.of("America/New_York"))


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

  def tryParseZoned(fmts: List[DateTimeFormatter], str: String): ZonedDateTime = {
    def _go(fmt: DateTimeFormatter, x: String): Option[ZonedDateTime] = {
      try {
        val r = ZonedDateTime.parse(x, fmt)
        Some(r)
      }
      catch {
        case ex:Exception => None
      }
    }
    fmts.flatMap(x => _go(x, str)).head
  }

  def tryParseLocal(fmts: List[DateTimeFormatter], str: String): LocalDateTime = {
    def _go(fmt: DateTimeFormatter, x: String): Option[LocalDateTime] = {
      try {
        val r = LocalDateTime.parse(x, fmt)
        Some(r)
      }
      catch {
        case ex:Exception => {
          println(ex)
          None
        }
      }
    }
    fmts.flatMap(x => _go(x, str)).head
  }

  val localPatterns = List(
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"),
    DateTimeFormatter.ofPattern("E',' dd M YYYY HH:mm:ss"),
    DateTimeFormatter.ofPattern("MMM dd',' yyyy HH:mm a"),
    DateTimeFormatter.ofPattern("MMMM dd',' yyyy hh:mm a"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
    DateTimeFormatter.ofPattern("MM/dd/yyyy',' hh:mma"),
    DateTimeFormatter.ofPattern("MMM dd',' yyyy 'at' hh:mm a"),
    DateTimeFormatter.ofPattern("MMMM d',' yyyy 'at' hh:mm a"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.n")
  )

  def safeParseNoTimeZone(txt: String, url: Uri, zoneId: ZoneId): Option[ZonedDateTime] = {
    try {

      val txt2 = fixampm(txt).trim

      val result = tryParseLocal(localPatterns, txt2)

      Some(ZonedDateTime.of(result, zoneId).withZoneSameInstant(ZoneId.of("UTC").normalized()))
    }
    catch {
      case ex: Throwable =>
        println(ex)
        info(s"safeParseNoTimeZone `$txt` could not be parsed to date as it did not meet the ISO 8601 spec" + " " + url.toString)
        None
    }
  }

  def fixampm(str: String): String = {
    str
      .replace(" a. m. ", " AM ")
      .replace(" p. m. ", " PM ")
      .replace(" a.m. ", " AM ")
      .replace(" p.m. ", " PM ")
      .replace("am", "AM")
      .replace("pm", "PM")
  }

  def fixtz(str: String): String = {
    if(str.endsWith("-500"))
      str.replace("-500", "-0500")
    else if(str.endsWith("-14400"))
      str.replace("-14400","")
    else if(str.endsWith("+0000 (UTC)"))
      str.replace("+0000 (UTC)", "")
    else {
      str
    }
  }

  val zonedPatterns = List(
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmZ"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:Z"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmz"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"),
    DateTimeFormatter.ofPattern("yyyy-MM-ddzHH:mm:ssZ"),
    DateTimeFormatter.ofPattern("E',' dd M YYYY HH:mm:ss Z"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssVV"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSVV"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"),
    DateTimeFormatter.ofPattern("E',' dd MMM yyyy HH:mm:ss z"),
    DateTimeFormatter.ofPattern("MMM d',' yyyy',' h:mm a z"),
    DateTimeFormatter.ofPattern("MMMM d',' yyyy h:mm a z"),
    DateTimeFormatter.ofPattern("E',' dd MMM yyyy HH:mm:ss X"),
    DateTimeFormatter.ofPattern("MMMM dd',' yyyy',' hh:mm:ss a z"),
    DateTimeFormatter.ofPattern("HH:mm z',' MMM dd',' yyyy"),
    DateTimeFormatter.ofPattern("yyyy-MM-ddzHH:mm:ss"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss Z"),
    DateTimeFormatter.ofPattern("E MMM dd yyyy HH:mm:ss z"),
    DateTimeFormatter.ofPattern("MMM dd yyyy h:mma z"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnX"),
    DateTimeFormatter.ofPattern("MMM d',' yyyy h:mm a z")
  )
  /**
    * Helper function to parse ISO 8601 date/time strings safely.
    */
  def safeParseISO8601Date(txt: String, url: Uri): Option[ZonedDateTime] = {
    if (txt == null || txt.isEmpty)
      return None

    var txt2 = fixampm(txt)
    txt2 = fixtz(txt2)

    try {
      val result = tryParseZoned(zonedPatterns, txt2)
      val x = result.withZoneSameInstant(ZoneId.of("UTC").normalized())
      Some(x)
    } catch {
      case ex: Exception =>
        println(ex)
        info(s"safeParseISO8601Date `$txt` could not be parsed to date as it did not meet the ISO 8601 spec" + " " + url.toString)
        None
    }
  }
}

