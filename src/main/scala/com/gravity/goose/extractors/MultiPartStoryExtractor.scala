package com.gravity.goose.extractors

import com.gravity.goose.Article
import com.gravity.goose.outputformatters.StandardOutputFormatter
import org.jsoup.nodes.Element

import scala.collection.JavaConverters._

class MultiPartStoryExtractor extends StandardContentExtractor {
  private val selectors = Seq("p[class~=story-body-text]", "div[class=art-story__text]")
  def shouldUse(article: Article): Boolean = {
    val candidates = selectors.map(selector => article.doc.select(selector)).filter(_.size > 0)
    candidates.size == 1
  }

  override def extractArticle(article: Article): String = {
    val candidates = selectors.map(selector => article.doc.select(selector)).filter(_.size > 0)
    val newElement = article.doc.createElement("div")
    candidates(0).iterator().asScala.foreach(newElement.appendChild(_))
    article.topNode = postExtractionCleanup(newElement)
    StandardOutputFormatter.getFormattedText(article.topNode)
  }
}
