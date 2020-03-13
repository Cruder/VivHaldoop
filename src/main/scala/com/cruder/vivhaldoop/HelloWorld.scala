package com.cruder.vivhaldoop

import java.util.function.{Consumer, Predicate}

import com.cruder.vivhaldoop.share.AppProperties.appProperties
import com.cruder.vivhaldoop.share.SharedSpark
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

// scp artifacts/vivhaldoop_jar/vivhaldoop.jar vivhaldoop@edge5.sagean.fr:/home/vivhaldoop
object HelloWorld {
//  val logger = LoggerFactory.getLogger(this.getClass)
  val TIME = "app.tweetsLastSeconds"

  def main(args: Array[String]): Unit = {
       println("Hello World")

    val filterFunc = new Predicate[String] {
      override def test(key: String): Boolean = key.startsWith("twitter4j")
    }
    val consumerFunc = new Consumer[String] {
      override def accept(key: String): Unit = {
        val value = appProperties.getProperty(key)
        System.setProperty(key, value)
        println(s"'$value'")
      }
    }

    appProperties.stringPropertyNames().stream()
      .filter( filterFunc )
      .forEach( consumerFunc )

    val spark = SharedSpark.spark

    println("=============== CREATE STREAMING CONTEXT ===============")
    val ssc = new StreamingContext(spark.sparkContext, Seconds(appProperties.getProperty(TIME).toLong))
    println("=============== CREATE TWITTER STREAMING ===============")
    val tweets = TwitterUtils.createStream(ssc, None)
    val statuses = tweets.map(status => status.getText)
    statuses.print()
    val enTweets = tweets.filter(_.getLang == "en")

    //    val hashTags = tweets.map(tweet => tweet.getText.split(" ").filter(_.startsWith("#")))
    enTweets.saveAsObjectFiles("tweets/tweet","json")

    println("=============== START STREAM LISTENING ===============")
    ssc.start()
    ssc.awaitTermination()

  }
}
