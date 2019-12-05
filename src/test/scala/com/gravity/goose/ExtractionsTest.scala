package com.gravity.goose

import org.junit.Test
import org.junit.Assert._
import utils.FileHelper
import java.time.{LocalDateTime, ZoneId, ZonedDateTime}
import com.netaporter.uri.Uri

/**
 * Created by Jim Plush
 * User: jim
 * Date: 8/19/11
 */

class ExtractionsTest {

  def getHtml(filename: String): String = {
    FileHelper.loadResourceFile(TestUtils.staticHtmlDir + filename, Goose.getClass)
  }

  @Test
  def fortune1(): Unit ={
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("fortune1.txt")
    val url = "http://fortune.com/2016/04/14/gamestop-ceo-ransformation-games/"
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    val title = "GameStop CEO Paul Raines Talks Gaming Retail Transformation"
    val content = "GameStop CEO Paul Raines Talks Retail Transformation"
    TestUtils.runArticleAssertions(article = article, expectedTitle = title, expectedStart = content)
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def bloomberg1(): Unit ={
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("bloomberg1.txt")
    val url = "http://www.bloomberg.com/news/articles/2016-05-10/bearish-grantham-admits-major-error-being-bullish-on-metals"
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    val title = "Bearish Grantham Admits `Major Error' Being Bullish on Metals"
    val content = "Bearish Grantham Admits `Major Error' Being Bullish on Metals"
    TestUtils.runArticleAssertions(article = article, expectedTitle = title, expectedStart = content)
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def cnn1() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("cnn1.txt")
    val url = "http://www.cnn.com/2010/POLITICS/08/13/democrats.social.security/index.html"
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    val title = "Democrats to use Social Security against GOP this fall"
    val content = "Washington (CNN) -- Democrats pledged "
    TestUtils.runArticleAssertions(article = article, expectedTitle = title, expectedStart = content)
  }

  @Test
  def businessWeek2() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("businessweek2.txt")
    val url: String = "http://www.businessweek.com/technology/here-comes-apples-real-tv-09132011.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article,
      expectedStart = "At Home Depot, we first realized we needed to have a real conversation with")
    TestUtils.printReport()
  }

  @Test
  def businessWeek3() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("businessweek3.txt")
    val url: String = "http://www.businessweek.com/management/five-social-media-lessons-for-business-09202011.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Get ready, America, because by Christmas 2012 you will have an Apple TV in your living room")
    TestUtils.printReport()
  }

  @Test
  def techcrunch1() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("techcrunch1.txt")
    val url = "http://techcrunch.com/2011/08/13/2005-zuckerberg-didnt-want-to-take-over-the-world/"
    val content = "The Huffington Post has come across this fascinating five-minute interview"
    val title = "2005 Zuckerberg Didn’t Want To Take Over The World"
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    TestUtils.runArticleAssertions(article = article, expectedTitle = title, expectedStart = content)
  }

  @Test
  def businessweek1() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("businessweek1.txt")
    val url: String = "http://www.businessweek.com/magazine/content/10_34/b4192066630779.htm"
    val title = "Olivia Munn: Queen of the Uncool"
    val content = "Six years ago, Olivia Munn arrived in Hollywood with fading ambitions of making it as a sports reporter and set about deploying"
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    TestUtils.runArticleAssertions(article = article, expectedTitle = title, expectedStart = content)
  }

  @Test
  def foxNews() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("foxnews1.txt")
    val url: String = "http://www.foxnews.com/politics/2010/08/14/russias-nuclear-help-iran-stirs-questions-improved-relations/"
    val content = "Russia's announcement that it will help Iran get nuclear fuel is raising questions"
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    TestUtils.runArticleAssertions(article = article, expectedStart = content)
  }

  @Test
  def aolNews() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("aol1.txt")
    val url: String = "http://www.aolnews.com/nation/article/the-few-the-proud-the-marines-getting-a-makeover/19592478"
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    val content = "WASHINGTON (Aug. 13) -- Declaring \"the maritime soul of the Marine Corps\" is"
    TestUtils.runArticleAssertions(article = article, expectedStart = content)
  }

  @Test
  def huffingtonPost2() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("huffpo2.txt")
    val url: String = "http://www.huffingtonpost.com/2011/10/06/alabama-workers-immigration-law_n_997793.html"
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    val content = "MONTGOMERY, Ala. -- Alabama's strict new immigration law may be backfiring."
    TestUtils.runArticleAssertions(article = article, expectedStart = content)
  }


  @Test
  def testHuffingtonPost() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val url: String = "http://www.huffingtonpost.com/2010/08/13/federal-reserve-pursuing_n_681540.html"
    val html = getHtml("huffpo1.txt")

    val title: String = "Federal Reserve's Low Rate Policy Is A 'Dangerous Gamble,' Says Top Central Bank Official"
    val content = "A top regional Federal Reserve official sharply criticized Friday"
    val keywords = "federal, reserve's, low, rate, policy, is, a, 'dangerous, gamble,', says, top, central, bank, official, business"
    val description = "A top regional Federal Reserve official sharply criticized Friday the Fed's ongoing policy of keeping interest rates near zero -- and at record lows -- as a \"dangerous gamble.\""
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    TestUtils.runArticleAssertions(article = article, expectedTitle = title, expectedStart = content, expectedDescription = description)

    /*val expectedTags = "Federal Open Market Committee" ::
        "Federal Reserve" ::
        "Federal Reserve Bank Of Kansas City" ::
        "Financial Crisis" ::
        "Financial Reform" ::
        "Financial Regulation" ::
        "Financial Regulatory Reform" ::
        "Fomc" ::
        "Great Recession" ::
        "Interest Rates" ::
        "Kansas City Fed" ::
        "Monetary Policy" ::
        "The Financial Fix" ::
        "Thomas Hoenig" ::
        "Too Big To Fail" ::
        "Wall Street Reform" ::
        "Business News" ::
        Nil
    assertNotNull("Tags should not be NULL!", article.tags)
    assertTrue("Tags should not be empty!", article.tags.size > 0)

    for (actualTag <- article.tags) {
      assertTrue("Each Tag should be contained in the expected set!", expectedTags.contains(actualTag))
    }*/
  }


  @Test
  def wallStreetJournal() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("wsj1.txt")
    val url: String = "http://online.wsj.com/article/SB10001424052748704532204575397061414483040.html"
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    val content = "The Obama administration has paid out less than a third of the nearly $230 billion"
    TestUtils.runArticleAssertions(article = article, expectedStart = content)
  }

  @Test
  def wsj2(): Unit ={
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("wsj2.txt")
    val url: String = "https://www.wsj.com/articles/uniteds-strategy-to-reduce-overbookings-1502676300"
    val article = TestUtils.getArticle(url = Uri.parse(url), rawHTML = html)
    val content = "Business Journal Reports: Leadership United’s Strategy to Reduce Over"
    TestUtils.runArticleAssertions(article = article, expectedStart = content, expectedAuthor = "Steven Norton")
    assert(article.publishTimestamp.isDefined)
    assert(article.tickers.equals(Seq("UAL")))
  }

  @Test
  def usaToday() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("usatoday1.txt")
    val url: String = "http://content.usatoday.com/communities/thehuddle/post/2010/08/brett-favre-practices-set-to-speak-about-return-to-minnesota-vikings/1"
    val article = TestUtils.getArticle(Uri.parse(url), rawHTML = html)
    val content = "Brett Favre says he couldn't give up on one more"
    TestUtils.runArticleAssertions(article = article, expectedStart = content)
  }

  @Test
  def wiredPubDate() {
    val url = "http://www.wired.com/playbook/2010/08/stress-hormones-boxing/";
    val html = getHtml("wired1.txt")

    // example of a custom PublishDateExtractor
    implicit val config = new Configuration();
    config.enableImageFetching = false
    /*config.setPublishDateExtractor(new PublishDateExtractor() {
      @Override
      def extract(rootElement: Element): Option[ZonedDateTime] = {
        // look for this guy: <meta name="DisplayDate" content="2010-08-18" />
        val elements = Selector.select("meta[name=DisplayDate]", rootElement);
        if (elements.size() == 0) return null;
        val metaDisplayDate = elements.get(0);
        if (metaDisplayDate.hasAttr("content")) {
          val dateStr = metaDisplayDate.attr("content");

          return Some(new ZonedDateTime(fmt.parse(dateStr)))
        }
        null;
      }
    });*/

    val article = TestUtils.getArticle(Uri.parse(url), rawHTML = html)

    TestUtils.runArticleAssertions(
      article,
      "Stress Hormones Could Predict Boxing Dominance",
      "On November 25, 1980, professional boxing");

    val expectedDateString = "2010-08-18";
    //assertEquals("Publish date should equal: \"2010-08-18\"", expectedDateString, fmt.format(article.publishDate));
    //System.out.println("Publish Date Extracted: " + fmt.format(article.publishDate));

  }

  @Test
  def espn() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("espn1.txt")
    val url: String = "http://sports.espn.go.com/espn/commentary/news/story?id=5461430"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "If you believe what college football coaches have said about sports")
  }


  @Test
  def engadget() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("engadget1.txt")
    val url: String = "http://www.engadget.com/2010/08/18/verizon-fios-set-top-boxes-getting-a-new-hd-guide-external-stor/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Streaming and downloading TV content to mobiles is nice")
  }

  @Test
  def msn1() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("msn1.txt")
    val expected = getHtml("msn1_result.txt")
    val url: String = "http://lifestyle.msn.com/your-life/your-money-today/article.aspx?cp-documentid=31244150"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = expected)

  }

  @Test
  def guardian1() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("guardian1.txt")
    val expected = getHtml("guardian1_result.txt")
    val url: String = "http://www.guardian.co.uk/film/2011/nov/18/kristen-wiig-bridesmaids"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = expected)

    assert(article.publishTimestamp.isDefined)
  }


  @Test
  def time() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("time1.txt")
    val url: String = "http://www.time.com/time/health/article/0,8599,2011497,00.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "This month, the federal government released",
      expectedTitle = "Invisible Oil from BP Spill May Threaten Gulf Aquatic Life")
  }

  @Test
  def time2() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("time2.txt")
    val url: String = "http://newsfeed.time.com/2011/08/24/washington-monument-closes-to-repair-earthquake-induced-crack/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Despite what the jeers of jaded Californians might suggest")
  }

  @Test
  def cnet() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("cnet1.txt")
    val url: String = "http://news.cnet.com/8301-30686_3-20014053-266.html?tag=topStories1"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Filed Under: Signal Strength Wireless Digital Media Verizon shows")
  }

  @Test
  def yahoo() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("yahoo1.txt")
    val url: String = "http://news.yahoo.com/apple-says-steve-jobs-resigning-ceo-224628633.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "SAN FRANCISCO (AP) — Steve Jobs, the mind behind the iPhone")
  }

  @Test
  def politico() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("politico1.txt")
    val url: String = "http://www.politico.com/news/stories/1010/43352.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "If the newest Census Bureau estimates stay close to form")
    assert(article.publishTimestamp.isDefined)
  }


  @Test
  def businessinsider1() {
    val url = "http://www.businessinsider.com/goldman-on-the-fed-announcement-2011-9"
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("businessinsider1.txt")
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "As everyone in the world was transfixed on the Fed")
  }

  @Test
  def businessinsider2() {
    val url = "http://www.businessinsider.com/goldman-on-the-fed-announcement-2011-9"
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("businessinsider2.txt")
    val article = TestUtils.getArticle(Uri.parse(url), html)

    TestUtils.runArticleAssertions(article = article,
      expectedStart = "From Goldman on the FOMC operation twist announcement")
  }

  @Test
  def cnbc1() {
    val url = "http://www.cnbc.com/id/44613978"
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("cnbc1.txt")
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Some traders found Wednesday's Fed statement to be a bit gloomier than expected.")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def stocksdaily() {
    val url = "http://www.stocksdaily.net/lam-research-corporation-nasdaqlrcx-held-6827-934-in-short-term-investmentscash/136092/"
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("stocksdaily.txt")
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Home Analyst Views Lam Research Corporation (NASDAQ:LRCX)")
    assert(article.publishTimestamp.isDefined)
  }

 /* @Test
  def theatlantic1() {
    val url = "http://www.theatlantic.com/entertainment/archive/2014/12/how-a-rap-god-lost-his-powers/383571/"
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("theatlantic1.txt")
    val article = TestUtils.getArticle(url, html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Did you hear that")
    assert(article.publishDate != null)
  }*/

  /*
  * --------------------------------------------------------
  * Test Fixes for GitHub Issues Submitted
  * --------------------------------------------------------
  */
  @Test
  def issue24() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("issue_24.txt")
    val expected = getHtml("issue_24_result.txt")
    val url: String = "http://danielspicar.github.com/goose-bug.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    assertEquals("The beginning of the article text was not as expected!", expected, article.articleText)
  }

  @Test
  def issue25() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("issue_25.txt")
    val url: String = "http://www.accountancyage.com/aa/analysis/2111729/institutes-ifrs-bang"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "UK INSTITUTES have thrown their weight behind rapid adoption of international financial reporting standards in the US.")
  }

  @Test
  def issue28() {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("issue_28.txt")
    val url: String = "http://www.telegraph.co.uk/foodanddrink/foodanddrinknews/8808120/Worlds-hottest-chilli-contest-leaves-two-in-hospital.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Emergency services were called to Kismot Restaurant's curry-eating challenge,")
  }

  @Test
  def issue32() {
    // this link is an example of web devs putting content not in paragraphs but embedding them in span tags with br's
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("issue_32.txt")
    val url: String = "http://www.tulsaworld.com/site/articlepath.aspx?articleid=20111118_61_A16_Opposi344152&rss_lnk=7"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Opposition to a proposal to remove certain personal data")
  }

  @Test
  def nyt1(): Unit ={
    // No Body
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("nyt1.txt")
    val url: String = "http://www.nytimes.com/packages/khtml/2004/03/04/washington/20040304_BLACKMUN_FEATURE.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = null)
  }

  @Test
  def nyt2(): Unit ={
    // No Body
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("nyt2.txt")
    val url: String = "https://www.nytimes.com/2018/01/09/us/california-mudslides.html?hp&action=click&pgtype=Homepage&clickSource=story-heading&module=photo-spot-region&region=top-news&WT.nav=top-news"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "SAN FRANCISCO — Drenching rain sent mud roaring down the hillsides of Santa Barbara County on Tuesday, killing at least eight people, carrying ")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def cleveland1(): Unit ={
    // ld script
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("cleveland1.txt")
    val url: String = "http://www.cleveland.com/parma/index.ssf/2016/11/residents_will_vote_on_a_numbe.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = null)
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def titleArrayOutOfBounds(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("titleArrayOutOfBounds.txt")
    val url: String = "http://mightycleanhome.com/173/"
    val article = TestUtils.getArticle(Uri.parse(url), html)

    val html2 = getHtml("titleArrayOutOfBounds2.txt")
    val url2: String = "http://www.aseguratuventa.com/index.php?a=2&b=17624&utm_source=twitterfeed&utm_medium=twitter"
    val article2 = TestUtils.getArticle(Uri.parse(url), html2)
  }

  @Test
  def reuters1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("reuters1.txt")
    val url = "http://www.reuters.com/article/us-mideast-crisis-iraq-mosul-idUSKBN19Q1HG?il=0"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "GENEVA/ERBIL, Iraq (Reuters) - The population of Mosul")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def wapo1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("wapo1.txt")
    val url = "https://www.washingtonpost.com/local/winning-numbers-drawn-in-dc-3-evening-game/2017/08/22/b19ad646-8799-11e7-96a7-d178cf3524eb_story.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "WASHINGTON _ The winning numbers in Tuesday evening’s")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def nasdaq1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("nasdaq1.txt")
    val url = "http://www.nasdaq.com/article/canadian-stocks-are-sinking-on-commodity-weakness--canadian-commentary-20151207-00647?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed:+nasdaq/symbols+(Articles+by+Symbol)"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "(RTTNews.com) - The Canadian stock market is solidly")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def yahoofinance1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("yahoofinance1.txt")
    val url = "http://www.nasdaq.com/article/canadian-stocks-are-sinking-on-commodity-weakness--canadian-commentary-20151207-00647?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed:+nasdaq/symbols+(Articles+by+Symbol)"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("2015-11-18T19:18:39"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Just three exchange-traded funds hit 52-week highs",
      expectedPublishDate = publishDate)
  }

  @Test
  def businessinsider3(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("businessinsider3.txt")
    val url = "http://www.nasdaq.com/article/canadian-stocks-are-sinking-on-commodity-weakness--canadian-commentary-20151207-00647?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed:+nasdaq/symbols+(Articles+by+Symbol)"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "It turns out that Melania Trump and Chelsea Clinton")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def dallasnews1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("dallasnews1.txt")
    val url = "https://www.dallasnews.com/opinion/commentary/2017/08/22/neighborhood-segregation-america-happen-accident"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Neighborhood segregation in America didn't happen by accident Filed under Commentary")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def newsmax1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("newsmax1.txt")
    val url = "https://www.newsmax.com/politics/tom-cole-government-shutdown-mistake-border-wall/2017/08/23/id/809312/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "It would be a \"mistake\" if President Donald Trump's threat")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def bbc1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("bbc1.txt")
    val url = "http://www.bbc.com/news/business-41032628?ocid=socialflow_twitter"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Uber is still losing money, but the ride-hailing firm appears to have stemmed the flow of cash to some degree.")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def marketwatch1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("marketwatch1.txt")
    val url = "https://www.marketwatch.com/story/clapper-questions-trumps-fitness-for-office-trumps-approval-rating-hits-new-post-charlottesville-low-in-poll-2017-08-23?siteid=rss"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "By RobertSchroeder Fiscal policy reporter Getty Images Former Director of National")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def bostonglobe1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("bostonglobe1.txt")
    val url = "https://www.bostonglobe.com/magazine/2017/08/22/let-stop-teaching-kids-that-reading-boring/JqBLwfpPJA6kByavjcnCNO/story.html?s_campaign=bostonglobe%3Asocialflow%3Atwitter"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Perspective | Magazine Let’s stop teaching kids that reading is boring Novels are")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def skynews1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("skynews1.txt")
    val url = "https://news.sky.com/story/pound-hits-lowest-level-since-2009-against-the-euro-11001861"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "The pound has hit a fresh eight-year low against the euro after the single currency was boosted")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def miamiherald1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("miamiherald1.txt")
    val url = "http://www.miamiherald.com/news/nation-world/world/americas/venezuela/article156672104.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Maracanã Stadium pulsed with samba, bossa nova")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def miamiherald2(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("miamiherald2.txt")
    val url = "http://www.miamiherald.com/news/nation-world/world/americas/article168120297.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "The number of Venezuelans seeking asylum tripled from 2015 to 2016, as the once-wealthy nation continues to be trapped in a punishing economic, social and political crisis.")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def apnews1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("apnews1.txt")
    val url = "https://apnews.com/f4f6e2d57e514e2da055d3d335285f26?utm_campaign=SocialFlow&utm_source=Twitter&utm_medium=AP"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Denmark Copenhagen Journalists Europe Swedish journalist's torso found")
    assert(article.publishTimestamp.isDefined)
    assert(article.metaDescription.isDefined)
  }

  @Test
  def yonhapnews1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("yonhapnews1.txt")
    val url = "http://english.yonhapnews.co.kr/news/2017/06/01/38/0200000000AEN20170601002651315F.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "SEOUL, June 1 (Yonhap) -- Sistar's new and final song \"Lonely\" on Thursday rose to No. 1 on major South Korean music streaming charts.")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def bloomberg2(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("bloomberg2.txt")
    val url = "https://www.bloomberg.com/news/articles/2018-01-17/how-to-reverse-america-s-foreign-tourist-problem"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Photographer: Waldo Swiegers/Bloomberg Airbnb Appoints Head of China Operations After Long Search")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def seekingalpha1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("seekingalpha1.txt")
    val url = "https://seekingalpha.com/article/4036547-blackstone-buying-brookdale-senior-living-inc-103_10-percent-held-institutions"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Blackstone Buying Brookdale Senior Living Inc.? 103.10% Is Held By Institution")
    assert(article.publishTimestamp.isDefined)
    assert(article.tickers.equals(Seq("BKD", "BX")))
  }

  @Test
  def abc1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("abc1.txt")
    val url = "http://abcnews.go.com/Politics/putin-suggests-us-hackers-interfered-election-blamed-russia/story?id=47800206&cid=social_twitter_abcn"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Russian President Vladimir Putin says it is possible that American")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
    assert(article.canonicalLink.get == "http://abcnews.go.com/Politics/putin-suggests-us-hackers-interfered-election-blamed-russia/story?id=47800206")
  }

  @Test
  def nikkei1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("nikkei1.txt")
    val url = "https://asia.nikkei.com/Business/Trends/Companies-set-sights-on-Japan-s-homegrown-GPS"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "TOKYO -- Japan's augmented Global Positioning System is expected to create a host of new business opportunities")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def fortune2(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("fortune2.txt")
    val url = "http://fortune.com/2017/06/01/jetblue-delta-boarding-checkin/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "We’re inching ever closer to")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def fortune3(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("fortune3.txt")
    val url = "http://fortune.com/2017/06/02/jpmorgan-chase-jamie-dimon-white-house-paris-climate-president-donald-trump/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Though JPMorgan CEO Jamie Dimon says he totally disagrees with")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def suntimes1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("suntimes1.txt")
    val url = "https://chicago.suntimes.com/entertainment/longtime-cso-violinist-fred-spector-has-died-at-92/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Longtime CSO violinist Fred Spector has died at 92 Obituaries")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def cnn2(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("cnn2.txt")
    val url = "http://money.cnn.com/2017/06/02/news/companies/lloyd-blankfein-tweets-again/index.html?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+rss%2Fmoney_latest+%28CNNMoney%3A+Latest+News%29"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "For Lloyd Blankfein, the answer was a bold call for")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def thehill1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("thehill1.txt")
    val url = "http://thehill.com/blogs/ballot-box/house-races/170525-rep-steve-king-iowa-caucus-still-matters"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Rep. Steve King (R-Iowa) has two words for anyone who")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def thehill3(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("thehill3.txt")
    val url = "https://thehill.com/blogs/blog-briefing-room/419398-ocasio-cortez-shreds-mike-huckabee-leave-the-false-statements-to"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Rep.-elect Alexandria Ocasio-CortezAlexandria Ocasio-CortezPelosi and Ocasio-Cortez:")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def thestreet(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("thestreet.txt")
    val url = "https://www.thestreet.com/story/14185345/1/rwt-august-18th-options-begin-trading.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Investors in Redwood Trust Inc (RWT) saw new options")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def thestreet2(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("thestreet2.txt")
    val url = "https://www.thestreet.com/story/13553443/1/will-focus-on-trade-and-united-technologies-lead-trump-to-victory-in-indiana.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "For months, Donald Trump has hammered Carrier")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
    assert(article.tickers.equals(Seq("UTX")))
  }

  @Test
  def dallasnews2(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("dallasnews2.txt")
    val url = "https://www.dallasnews.com/business/business/2014/03/05/exxon-mobil-suspends-ukraine-exploration"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Exxon Mobil suspends Ukraine discussions Filed under Business")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def ndtv1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("ndtv1.txt")
    val url = "https://www.ndtv.com/chennai-news/chennai-fire-blazes-for-second-day-4-floors-of-building-cave-in-1706535"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "A major market in the heart of Chennai")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def thehill2(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("thehill2.txt")
    val url = "http://thehill.com/blogs/ballot-box/house-races/170525-rep-steve-king-iowa-caucus-still-matters"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Rep. Steve King (R-Iowa) has two words for anyone")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def stltoday1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("stltoday1.txt")
    val url = "http://thehill.com/blogs/ballot-box/house-races/170525-rep-steve-king-iowa-caucus-still-matters"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    assert(article.articleText.isEmpty)
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def fortune4(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("fortune4.html")
    val url = "https://www.reuters.com/article/mongolia-riotinto-idUSL3N1JH3LP"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "British Prime Minister")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def time3(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("time3.html")
    val url = "https://www.reuters.com/article/mongolia-riotinto-idUSL3N1JH3LP"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "This video")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def miamiherald3(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("miamiherald3.txt")
    val url = "http://www.miamiherald.com/sports/spt-columns-blogs/fish-bytes/article155268444.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "PITTSBURGH -- Derek Dietrich")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def euronews1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("euronews1.txt")
    val url = "http://www.euronews.com/2017/06/08/how-to-follow-the-uk-election?utm_term=Autofeed&utm_campaign=Echobox&utm_medium=Social&utm_source=Twitter#link_time=1496959566"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Britain’s snap general")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def nasdaq2(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("nasdaq2.txt")
    val url = "http://www.nasdaq.com/article/enersys-ens-q4-earnings-revenues-beat-guidance-slashed-cm796969"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Industrial battery manufacturer, EnerSys ENS kept its winning streak alive")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
    assert(article.tickers.length == 4)
    assert(article.author.isDefined)
  }

  @Test
  def nasdaq3(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("nasdaq3.txt")
    val url = "http://www.nasdaq.com/press-release/amtrust-financial-services-inc-appoints-adam-karkowsky-as-new-chief-financial-officer-20170605-00560"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "NEW YORK, June 05, 2017 (GLOBE NEWSWIRE) -- AmTrust Financial Services")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def chicagosuntimes1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("chicagosuntimes1.txt")
    val url = "http://chicago.suntimes.com/news/comey-to-say-trump-asked-for-loyalty-told-him-to-let-flynn-probe-go/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Comey to say Trump asked for loyalty, told him to let Flynn probe go Chicago News")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def rt1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("rt1.txt")
    val url = "https://www.rt.com/business/392454-outage-british-airways-eighty-million/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "The IT meltdown that left thousands of passengers")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def etftrends1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("etftrends1.txt")
    val url = "https://www.etftrends.com/etf-investing/nushares-international-etf-options-to-diversify-esg-exposure/?utm_source=dlvr.it&utm_medium=twitter"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "NuShares International ETF Options to Diversify ESG Exposure")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def usatoday2(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("usatoday2.txt")
    val url = "https://www.usatoday.com/story/money/markets/2016/06/06/sell-may-and-investing-adages-debunked/85296684/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "TOP TRENDING x Share 8 Share This Story! Let friends in your social network know what you are reading about FacebookEmailTwitterGoogle+LinkedInPinterest 'Sell in May'")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def nyt3(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("nyt3.txt")
    val url = "http://www.nytimes.com/2011/05/17/sports/a-companys-small-town-arenas-leave-cities-with-big-problems.html?_r=0"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "RIO RANCHO, N.M. — The plan sounded great during ")
    //assert(article.publishDate.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def businessinsider4(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("businessinsider4.txt")
    val url = "http://www.businessinsider.com/investing-basics-young-people-5-2014-9"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Libby Kane Sep. 19, 2014, 2:09 PM 311,826 facebook linkedin twitter email copy link Anyone with a little spare cash can invest.Flickr / Dima Viunnyk You're never too young to invest.")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def foxnews2(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("foxnews2.txt")
    val url = "http://www.foxnews.com/entertainment/2018/05/10/monica-lewinsky-gets-apology-after-town-country-uninvited-her-from-event-after-bill-clinton-agreed-to-attend.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Town & Country")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def foxnews3(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("foxnews3.html")
    val url = "https://www.foxnews.com/media/joey-jones-krugman-nyt-republicans-pretend-patriots"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "After a New York Times")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def houstonchronicle1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("houstonchronicle1.txt")
    val url = "https://www.houstonchronicle.com/news/article/Connecticut-state-troopers-to-be-equipped-with-12953353.php"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "MIDDLETOWN, Conn. (AP) — Connecticut State")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def nbcnews1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("nbcnews1.html")
    val url = "https://www.nbcnews.com/news/us-news/administration-delays-steel-aluminum-tariffs-canada-eu-mexico-n870311?cid=public-rss_20180508"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Breaking News Emails Get breaking news alerts and special reports. The news and stories that matter, delivered weekday mornings. SUBSCRIBE WASHINGTON — The White House")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def telesurtv1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("telesurtv1.txt")
    val url = "https://www.nbcnews.com/news/us-news/administration-delays-steel-aluminum-tariffs-canada-eu-mexico-n870311?cid=public-rss_20180508"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    //TestUtils.runArticleAssertions(article = article,
    //  expectedStart = "WASHINGTON — The White House is postponing a decision")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def atlantic1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("atlantic1.txt")
    val url = "https://www.theatlantic.com/entertainment/archive/2018/05/im-not-black-im-kanye/559763/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "I’m Not Black, I’m Kanye Kanye West wants freedom—white freedom.")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def axios1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("axios1.html")
    val url = "https://www.axios.com/newsletters/axios-am-f9d0575f-832e-4291-a597-8103d61e0ab5.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Today is the 17th Memorial Day since 9/11. Since then")
    //assert(article.publishTimestamp.isDefined)
    //Axios does not  have a timestamp
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def fortune5(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("fortune5.html")
    val url = "http://fortune.com/2017/09/27/apple-iphone-8-crackling-noise/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "iPhone 8 users are complaining that the earpiece for")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def fortune6(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("fortune6.html")
    val url = "https://fortune.com/2011/07/26/is-apple-undervalued-at-404/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Now that the stock has finally broken through the $400 barrier, how high can it go?")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def forbes1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("forbes1.txt")
    val url = "http://www.forbes.com/sites/ericjackson/2015/06/25/the-most-important-executive-in-silicon-valley-that-no-one-is-talking-about/?utm_campaign=yahootix&partner=yahootix"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "[Author was long YHOO at the time of writing]")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def reuters3(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("reuters3.html")
    val url = "https://www.reuters.com/article/idUSASB0AWN3"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "* Central Pacific Financial Corp reports") //Should be "Jan 25 (Reuters) - Central Pacific Financial Corp :"
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def fastcompany1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("fastcompany1.html")
    val url = "https://news.fastcompany.com/a-second-federal-appeals-court-has-ruled-against-trumps-travel-ban-4040556"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    //This one selects the wrong piece of text because the body is super short
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "I spent a good chunk of the past few days reviewi")
    // There is no timestamp
    //assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def ktvu1(): Unit = {
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("ktvu1.html")
    val url = "http://www.ktvu.com/news/258694056-story"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    //val publishDate = Some(ZonedDateTime.of(LocalDateTime.parse("Jun 19, 2017 11:38 AM EDT"), ZoneId.of("UTC").normalized()))
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Man injured with bullet")
    assert(article.publishTimestamp.isDefined)
    assert(article.canonicalLink.isDefined)
  }

  @Test
  def blogger1(): Unit ={
    // ld script
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("blogger1.txt")
    val url: String = "http://fat-pitch.blogspot.com/2013/09/what-does-small-cap-outperformance-tell.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "For good reason, investors are looking at the performance of the Russell 2000 (RUT).")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def kentucky1(): Unit ={
    // NO TIME ZONE
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("kentucky1.txt")
    val url: String = "https://www.kentucky.com/latest-news/article44132223.html"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Charlotte-area credit unions have seen an increase in phone calls and new members in")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def washingtonexaminer1(): Unit ={
    // NO TIME ZONE
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("washingtonexaminer1.html")
    val url: String = "https://www.washingtonexaminer.com/business/trump-teases-wall-street-with-hints-of-china-tariff-relief"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "Trump teases Wall Street with hints of China-tariff relief by James Langford")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def japantimes1(): Unit ={
    // NO TIME ZONE
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("japantimes1.html")
    val url: String = "https://www.japantimes.co.jp/news/2016/12/17/national/japan-south-korea-swap-intelligence-north-korean-threats-first-time/"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "National Japan and South Korea swap intelligence on North Korean")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def wallstreetde1(): Unit ={
    // NO TIME ZONE
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("wallstreetde1.html")
    val url: String = "https://www.wallstreet-online.de/nachricht/11805119-immobilienmarkt-einzug-deutsche-grossbanken-new-yorker-hauptquartier"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "National Japan and South Korea swap intelligence on North Korean")
    assert(article.publishTimestamp.isDefined)
  }

  @Test
  def fool1(): Unit ={
    // NO TIME ZONE
    implicit val config = TestUtils.NO_IMAGE_CONFIG
    val html = getHtml("fool1.html")
    val url: String = "https://www.fool.com/careers/2019/09/27/the-top-4-reasons-workers-seek-flexible-jobs.aspx\t"
    val article = TestUtils.getArticle(Uri.parse(url), html)
    TestUtils.runArticleAssertions(article = article,
      expectedStart = "National Japan and South Korea swap intelligence on North Korean")
    assert(article.publishTimestamp.isDefined)
  }
}