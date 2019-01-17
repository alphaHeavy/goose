package com.gravity.goose.extractors

import org.jsoup.nodes.Element

object TickerExtractor {
  private final val wsj = raw"quotes.wsj.com/([A-Z]+)".r
  private final val seekingAlpha = raw"/symbol/([A-Z]+)".r
  private final val nasdaq = raw"www.nasdaq.com/symbol/([A-Z]+)".r
  private final var thestreet = raw"/quote/([A-Z]+)".r

  private final val selectors = Seq(
    "span[class=ticker]"
  )

  private def getTickerFromElement(e: Element): Option[String] ={
    e.nodeName() match {
      case x if x == "span" =>
        val y = e.attr("data-symbol")
        if (!y.isEmpty) {
          Some(y)
        }
        else
          {
            None
          }
    }
  }

  private def extractSelectors(rootElement: Element): Seq[String] ={
    import scala.collection.JavaConversions._
    selectors.flatMap(x => rootElement.select(x).map(getTickerFromElement)).filter(_.isDefined).map(_.get)
  }

  def extractTickers(html: String, domain: String, rootElement: Element): Seq[String] = {
    domain match {
      case x if x == "www.wsj.com" => wsj.findAllMatchIn(html).map(_.group(1)).toList.distinct
      case x if x == "seekingalpha.com" => seekingAlpha.findAllMatchIn(html).map(_.group(1)).toList.distinct
      case x if x == "www.nasdaq.com" => nasdaq.findAllMatchIn(html).map(_.group(1)).toList.distinct
      case x if x == "www.thestreet.com" => thestreet.findAllMatchIn(html).map(_.group(1)).toList.distinct
      case _ => extractSelectors(rootElement)
    }
  }
}
