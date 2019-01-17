package com.gravity.goose.extractors
import com.gravity.goose.ArticleInternal

class ArticleTagExtractor extends StandardContentExtractor {
  override def extractArticle(article: ArticleInternal): Option[String] = {
    val articleTags = article.rawDoc.select("article")
    if(articleTags.size() == 1)
      {
        Some(articleTags.get(0).text())
      }
    else
      {
        None
      }
  }
}
