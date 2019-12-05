package com.gravity.goose.extractors

import org.jsoup.nodes.Element
import com.gravity.goose.utils.Logging
import org.jsoup.select.Elements

/**
  * Created by steve on 7/5/17.
  */
object AuthorExtractor extends Logging {
  final val authorSelectors = Seq(
    "meta[name=author]",
    "a[rel=author]",
    "meta[property=author]"
  )

  private def getFromMatch(m: Element): Option [String] = {
    m.nodeName() match {
      case x if x == "meta" => Some(m.attr("content"))
      case x if x == "a" => Some(m.text())
      case _ => None
    }

  }

  def extractAuthor(rootElement: Element): Option[String] = {
    import scala.collection.JavaConversions._
    authorSelectors.flatMap(x => rootElement.select(x).map(getFromMatch).filter(_.isDefined).map(_.get)).headOption
  }
}
