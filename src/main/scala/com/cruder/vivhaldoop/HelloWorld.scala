package com.cruder.vivhaldoop

import java.io.FileInputStream
import java.util.Properties

import org.apache.spark.SparkConf
import com.cruder.vivhaldoop.share.AppProperties.appProperties
import org.apache.spark.streaming.api.java.JavaStreamingContext
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds}

object HelloWorld {
  def main(args: Array[String]): Unit = {
    println("Hello World")

    val sparkConfig = new SparkConf()
      .setAppName(appProperties.getProperty("app.name"))

    val jssc = new JavaStreamingContext(sparkConfig, Seconds(10))
    val stream = TwitterUtils.createStream(jssc)

    val hashTags = stream.map(tweet => tweet.getText.split(" ").filter(_.startsWith("#"))).dstream
    hashTags.saveAsTextFiles("hashtags.txt")

    jssc.start()
    jssc.awaitTermination()

  }
}
