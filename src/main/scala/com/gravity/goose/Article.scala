package com.gravity.goose

import java.time.ZonedDateTime

case class Article(title: String, articleText: String, metaDescription: Option[String], metaKeywords: Option[String],
                   canonicalLink: Option[String], publishTimestamp: Option[ZonedDateTime],
                   updatedTimestamp: Option[ZonedDateTime], publishedDate: Option[ZonedDateTime],
                   author: Option[String], tickers: Seq[String])