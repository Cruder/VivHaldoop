package com.cruder.vivhaldoop

import com.cruder.vivhaldoop.share.AppProperties.appProperties
import com.cruder.vivhaldoop.share.SharedSpark
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.api.java.JavaStreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.slf4j.LoggerFactory
import twitter4j.Status

// scp artifacts/vivhaldoop_jar/vivhaldoop.jar vivhaldoop@edge5.sagean.fr:/home/vivhaldoop
object HelloWorld {
//  val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
       println("Hello World")
    val spark = SharedSpark.spark

    appProperties.stringPropertyNames().stream()
      .filter(key => key.startsWith("twitter4j"))
      .forEach(key => System.setProperty(key, appProperties.getProperty(key)))

    val ssc = new StreamingContext(spark.sparkContext, Seconds(10))
    val tweets = TwitterUtils.createStream(ssc, None)

//    val hashTags = tweets.map(tweet => tweet.getText.split(" ").filter(_.startsWith("#")))
    tweets.saveAsObjectFiles("tweets","hashtags.txt")

    ssc.start()
    ssc.awaitTermination()

  }
}
