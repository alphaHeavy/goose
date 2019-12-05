package com.gravity.goose.extractors
import com.gravity.goose.ArticleInternal
import com.gravity.goose.outputformatters.StandardOutputFormatter
import org.json4s.JsonAST.{JField, JObject, JString, JValue}
import org.json4s.native.JsonMethods.parse
import org.jsoup.Jsoup

class JSONArticleExtractor extends StandardContentExtractor {
  val jsonBlobSelectors = List("script[type=application/ld+json]", "script[id=preload]")

  override def extractArticle(article: ArticleInternal): Option[String] = {
    jsonBlobSelectors.map(x => extractFromBlob(article, x)).find(x => x.isDefined).flatten
  }

  private def findContent(json: JValue): Option[String] = {
    try {
      val textChoices = json \\ "content"
      val textChoice = textChoices.asInstanceOf[JObject].obj.last._2.asInstanceOf[JString].s
      Some(StandardOutputFormatter.getFormattedText(Jsoup.parse(textChoice)))
      //Some(textChoice)
    }
    catch {
      case ex: Exception =>
        println(ex)
        None
    }
  }

  private def extractFromBlob(article: ArticleInternal, selector: String): Option[String] = {
    val jsonBlobs = article.rawDoc.select(selector)

    if(jsonBlobs.size() == 1) {
      try {
        var text = jsonBlobs.get(0).html
        if(text.contains("window.__PRELOADED_STATE__ = "))
        {
            text = text.replace("window.__PRELOADED_STATE__ = ", "").dropRight(1)
        }

        val json = parse(text, false)
        val foo = json \\ "articleBody" match {
          case JString(x) => Some(x)
          case _ => None
        }

        val foo2 = findContent(json)

        val r = foo.orElse(foo2)
        r
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
