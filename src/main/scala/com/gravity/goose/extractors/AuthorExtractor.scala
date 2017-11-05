package com.gravity.goose.extractors

import org.jsoup.nodes.Element
import com.gravity.goose.utils.Logging

/**
  * Created by steve on 7/5/17.
  */
object AuthorExtractor extends Logging {
  final val authorSelectors = Seq(
    "meta[name=author]"
  )

  def extractAuthor(rootElement: Element): Option[String] = {
    authorSelectors.map(x => Option(rootElement.select(x).attr("content"))).head
  }
}
