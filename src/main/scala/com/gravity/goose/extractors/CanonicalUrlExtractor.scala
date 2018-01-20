package com.gravity.goose.extractors

import org.jsoup.nodes.Element

object CanonicalUrlExtractor {

  private final val selectors = Seq(
    "link[rel=canonical]"
  )

  def extractCanonicalUrl(rootElement: Element): Option[String] = {
    selectors.map(x => Option(rootElement.select(x).attr("href"))).head
  }
}
