package com.gravity.goose.extractors
import com.gravity.goose.ArticleInternal
import org.json4s.JsonAST.JString
import org.json4s.native.JsonMethods.parse

class JSONArticleExtractor extends StandardContentExtractor {
  override def extractArticle(article: ArticleInternal): Option[String] = {
    val jsonBlobs = article.rawDoc.select("script[type=application/ld+json]")

    if(jsonBlobs.size() == 1) {
      try {
        val json = parse(jsonBlobs.get(0).html, false)
        json \\ "articleBody" match {
          case JString(x) => Some(x)
          case _ => None
        }
      }
      catch {
        case ex: Exception =>
          println("JSONArticleExtractor Failed To Parse " + article.finalUrl + " " + ex.toString)
          None
      }
    }
    else
    {
      None
    }
  }
}
