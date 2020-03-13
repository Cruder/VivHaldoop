package com.cruder.vivhaldoop

import java.sql.Timestamp

import com.cruder.vivhaldoop.share.SharedSpark
import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{desc, rank}
import org.apache.spark.sql.types.{StringType, StructField, StructType, TimestampType}

object TopTweets {
  def main(args: Array[String]): Unit = {
    val spark = SharedSpark.spark
    val schema = StructType(Seq(
      StructField("message", StringType),
      StructField("hashtag", StringType),
      StructField("posted_at", TimestampType),
      StructField("created_at", TimestampType)
    ))

    val rdd = spark.sparkContext.parallelize(Seq(
      Row(
        "at nibh in #hac habitasse",
        "#hac",
        Timestamp.valueOf("2014-01-01 23:00:01"),
        Timestamp.valueOf("2018-01-01 23:00:01")
      ), Row(
        "dapibus duis at velit eu est congue #elementum in #hac habitasse",
        "#elementum",
        Timestamp.valueOf("2014-01-01 23:00:01"),
        Timestamp.valueOf("2018-01-01 23:00:01")
      ), Row(
        "dapibus duis at velit eu est congue #elementum in #hac habitasse",
        "#hac",
        Timestamp.valueOf("2014-01-01 23:00:01"),
        Timestamp.valueOf("2018-01-01 23:00:01")
      ), Row(
        "dapibus duis at velit eu est congue #truc in #hac habitasse",
        "#truc",
        Timestamp.valueOf("2014-01-01 23:00:01"),
        Timestamp.valueOf("2018-01-01 23:00:01")
      )))

    val df = spark.createDataFrame(rdd, schema)
    df.printSchema()
    df.show(3)

    val count = df.groupBy("hashtag").count()
      .orderBy(desc("count")).limit(10)
      .withColumn("rank", rank().over(Window.orderBy(desc("count"))))
    count.printSchema()
    count.show

    df.write.mode("overwrite").saveAsTable("twitter.top_tweets")
  }
}
