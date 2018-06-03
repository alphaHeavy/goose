package com.gravity.goose.extractors
import com.gravity.goose.Article
import org.json4s.JsonAST.JString
import org.json4s.native.JsonMethods.parse

class JSONArticleExtractor extends StandardContentExtractor {
  override def extractArticle(article: Article): String = {
    val jsonBlobs = article.rawDoc.select("script[type=application/ld+json]")

    if(jsonBlobs.size() == 1) {
      try {
        val json = parse(jsonBlobs.get(0).html, false)
        json \\ "articleBody" match {
          case JString(x) => x
          case _ => ""
        }
      }
      catch {
        case ex: Exception =>
          println("JSONArticleExtractor Failed To Parse " + article.finalUrl + " " + ex.toString)
          ""
      }
    }
    else
    {
      ""
    }
  }
}
