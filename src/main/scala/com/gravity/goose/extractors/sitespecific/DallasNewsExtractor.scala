package com.gravity.goose.extractors.sitespecific

import com.gravity.goose.Article
import com.gravity.goose.extractors.StandardContentExtractor
import com.gravity.goose.outputformatters.StandardOutputFormatter
import org.jsoup.nodes.Element

class DallasNewsExtractor extends StandardContentExtractor {
  override def extractArticle(article: Article): String = {
    import scala.collection.JavaConverters._
    val foo = article.doc.select("div[class=art-story__text]")

    val newElement = new Element("div")

    foo.iterator().asScala.foreach(newElement.appendChild(_))
    article.topNode = postExtractionCleanup(newElement)
    StandardOutputFormatter.getFormattedText(article.topNode)
  }
}
