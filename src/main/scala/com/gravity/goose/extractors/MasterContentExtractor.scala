package com.gravity.goose.extractors

import com.gravity.goose.Article
import com.gravity.goose.extractors.sitespecific.DallasNewsExtractor

class MasterContentExtractor extends ContentExtractor {
  private val standardContentExtractor = new StandardContentExtractor()
  private val siteSpecificContentExtractors: Map[String, ContentExtractor] =
    Map("www.dallasnews.com" -> new DallasNewsExtractor())

  override def getTitle(article: Article): String = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.getTitle(article)
      case Some(extractor) => extractor.getTitle(article)
    }
  }

  override def getMetaDescription(article: Article): String = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.getMetaDescription(article)
      case Some(extractor) => extractor.getMetaDescription(article)
    }
  }

  override def getMetaKeywords(article: Article): String = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.getMetaKeywords(article)
      case Some(extractor) => extractor.getMetaKeywords(article)
    }
  }

  override def getCanonicalLink(article: Article): String = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.getCanonicalLink(article)
      case Some(extractor) => extractor.getCanonicalLink(article)
    }
  }

  override def extractTags(article: Article): Set[String] = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.extractTags(article)
      case Some(extractor) => extractor.extractTags(article)
    }
  }

  override def extractArticle(article: Article): String = {
    siteSpecificContentExtractors.get(article.domain) match {
      case None => standardContentExtractor.extractArticle(article)
      case Some(extractor) => extractor.extractArticle(article)
    }
  }
}
