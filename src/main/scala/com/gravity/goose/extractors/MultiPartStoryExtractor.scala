package com.gravity.goose.extractors

import com.gravity.goose.Article
import com.gravity.goose.outputformatters.StandardOutputFormatter
import org.jsoup.nodes.Element

import scala.collection.JavaConverters._

class MultiPartStoryExtractor extends StandardContentExtractor {
  override def extractArticle(article: Article): String = {
    val selectors = Seq("p[class~=story-body-text]", "div[class=art-story__text]")
    val candidates = selectors.map(selector => article.doc.select(selector)).filter(_.size > 0)
    if(candidates.size == 1)
      {
        val newElement = new Element("div")
        candidates(0).iterator().asScala.foreach(newElement.appendChild(_))
        article.topNode = postExtractionCleanup(newElement)
        StandardOutputFormatter.getFormattedText(article.topNode)
      }
    else
      {
        super.extractArticle(article)
      }
  }
}
