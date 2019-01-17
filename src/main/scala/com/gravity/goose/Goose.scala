/**
 * Licensed to Gravity.com under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  Gravity.com licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gravity.goose

import com.gravity.goose.cleaners.StandardDocumentCleaner
import com.gravity.goose.extractors.{AuthorExtractor, CanonicalUrlExtractor}
import com.netaporter.uri.Uri
import org.jsoup.Jsoup

/**
 * Created by Jim Plush - Gravity.com
 * Date: 8/14/11
 */
class Goose(config: Configuration = new Configuration) {
  def extractArticle(html: String, url: Uri): Article = {
    val article = new ArticleInternal()

    val extractor = config.contentExtractor
    val docCleaner = new StandardDocumentCleaner
    val doc = Jsoup.parse(html)

    article.finalUrl = url.toString
    article.domain = url.host.getOrElse("")
    article.rawHtml = html
    article.doc = doc
    article.rawDoc = doc.clone()

    article.title = extractor.getTitle(article)
    article.author = AuthorExtractor.extractAuthor(doc)
    val (publishTimestamp, updateTimestamp) = config.publishDateExtractor.extract(doc, url)
    article.publishTimestamp = publishTimestamp
    article.updatedTimestamp = updateTimestamp
    article.additionalData = config.getAdditionalDataExtractor.extract(doc)
    article.metaDescription = extractor.getMetaDescription(article)
    article.metaKeywords = extractor.getMetaKeywords(article)
    article.canonicalLink = CanonicalUrlExtractor.extractCanonicalUrl(doc)
    article.tags = extractor.extractTags(article)
    article.doc = docCleaner.clean(article)
    article.cleanedArticleText = extractor.extractArticle(article).getOrElse("")
    article.tickers = config.tickerExtractor.extractTickers(html, url.host.getOrElse(""), doc)

    val metaDescription = article.metaDescription.isEmpty match {
      case true => None
      case false => Some(article.metaDescription)
    }

    val metaKeywords = article.metaKeywords.isEmpty match {
      case true => None
      case false => Some(article.metaKeywords)
    }

    Article(article.title, article.cleanedArticleText, metaDescription, metaKeywords, article.canonicalLink,
      publishTimestamp, updateTimestamp, None, article.author, article.tickers)
  }
}