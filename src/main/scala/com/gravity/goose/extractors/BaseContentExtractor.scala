package com.gravity.goose.extractors

import com.gravity.goose.text.{ReplaceSequence, StringReplacement, StringSplitter, string}
import com.gravity.goose.utils.Logging
import org.jsoup.select.TagsEvaluator

abstract class BaseContentExtractor extends ContentExtractor with Logging {
  protected val MOTLEY_REPLACEMENT: StringReplacement = StringReplacement.compile("&#65533;", string.empty)
  protected val ESCAPED_FRAGMENT_REPLACEMENT: StringReplacement = StringReplacement.compile("#!", "?_escaped_fragment_=")
  protected val TITLE_REPLACEMENTS: ReplaceSequence = ReplaceSequence.create("&raquo;").append("»")
  protected val PIPE_SPLITTER: StringSplitter = new StringSplitter("\\|")
  protected val DASH_SPLITTER: StringSplitter = new StringSplitter(" - ")
  protected val ARROWS_SPLITTER: StringSplitter = new StringSplitter("»")
  protected val COLON_SPLITTER: StringSplitter = new StringSplitter(":")
  protected val SPACE_SPLITTER: StringSplitter = new StringSplitter(" ")
  protected val NO_STRINGS = Set.empty[String]
  protected val A_REL_TAG_SELECTOR: String = "a[rel=tag], a[href*=/tag/]"
  protected val TOP_NODE_TAGS = new TagsEvaluator(Set("p", "td", "pre"))
  protected val TOP_NODE_SELECTORS = Seq("div[class=content-body]")

  def getLogger() = logger
}
