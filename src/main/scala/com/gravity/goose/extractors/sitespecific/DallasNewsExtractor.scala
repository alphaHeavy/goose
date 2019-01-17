package com.gravity.goose.extractors.sitespecific

import com.gravity.goose.ArticleInternal
import com.gravity.goose.extractors.StandardContentExtractor
import com.gravity.goose.outputformatters.StandardOutputFormatter
import org.jsoup.nodes.Element

class DallasNewsExtractor extends StandardContentExtractor {
  override def extractArticle(article: ArticleInternal): Option[String] = {
    import scala.collection.JavaConverters._
    val foo = article.doc.select("div[class=art-story__text]")

    val newElement = article.doc.createElement("div")

    foo.iterator().asScala.foreach(newElement.appendChild(_))
    article.topNode = postExtractionCleanup(newElement)
    Some(StandardOutputFormatter.getFormattedText(article.topNode))
  }
}
