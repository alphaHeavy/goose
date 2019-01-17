package com.gravity.goose.extractors
import com.gravity.goose.ArticleInternal

class SiteSpecificContentExtractor extends StandardContentExtractor {
  override def extractArticle(article: ArticleInternal): Option[String] = {
    None
  }
}
