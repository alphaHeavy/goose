package com.gravity.goose.extractors

import com.gravity.goose.ArticleInternal
import com.gravity.goose.extractors.sitespecific.DallasNewsExtractor

class MasterContentExtractor extends ContentExtractor {
  private val standardContentExtractor = new StandardContentExtractor()
  private val multiPartStoryExtractor = new MultiPartStoryExtractor()
  private val siteSpecificContentExtractors: Map[String, ContentExtractor] = Map()
  private val jsonContentExtractor = new JSONArticleExtractor()
  private val articleTagExtractor = new ArticleTagExtractor()

  override def getTitle(article: ArticleInternal): String = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.getTitle(article)
      case Some(extractor) => extractor.getTitle(article)
    }
  }

  override def getMetaDescription(article: ArticleInternal): String = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.getMetaDescription(article)
      case Some(extractor) => extractor.getMetaDescription(article)
    }
  }

  override def getMetaKeywords(article: ArticleInternal): String = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.getMetaKeywords(article)
      case Some(extractor) => extractor.getMetaKeywords(article)
    }
  }

  override def getCanonicalLink(article: ArticleInternal): String = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.getCanonicalLink(article)
      case Some(extractor) => extractor.getCanonicalLink(article)
    }
  }

  override def extractTags(article: ArticleInternal): Set[String] = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.extractTags(article)
      case Some(extractor) => extractor.extractTags(article)
    }
  }

  override def extractArticle(article: ArticleInternal): Option[String] = {
    val candidate = multiPartStoryExtractor
      .extractArticle(article)
      .orElse(standardContentExtractor.extractArticle(article))
      .orElse(jsonContentExtractor.extractArticle(article))

    val at = articleTagExtractor.extractArticle(article)

    if (at.isDefined && candidate.isDefined) {
      if (at.get.length > candidate.get.length) {
        at
      }
      else {
        candidate
      }
    }
    else
    {
      candidate.orElse(at)
    }

    /*
    siteSpecificContentExtractors.get(article.domain) match {
      case None =>
        if(multiPartStoryExtractor.shouldUse(article))
          {
            multiPartStoryExtractor.extractArticle(article)
          }
        else {
          val result = standardContentExtractor.extractArticle(article)
          if(result.isEmpty)
            {
              jsonContentExtractor.extractArticle(article)
            }
          else
            {
              result
            }
        }
      case Some(extractor) => extractor.extractArticle(article)
    }*/
  }
}
