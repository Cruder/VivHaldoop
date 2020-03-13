package com.cruder.vivhaldoop

import java.sql.Timestamp

import com.cruder.vivhaldoop.share.SharedSpark
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{desc, rank}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType, TimestampType}

object TopTweets {

  def main(args: Array[String]): Unit = {
    val spark = SharedSpark.spark
    populateMainDB(spark)

    val hashtag_dataframme = spark.sql("select * from twitter.hashtags")
    val count_dataframe = hashtag_dataframme.groupBy("hashtag").count()
      .withColumn("rank", rank().over(Window.orderBy(desc("count"))))
    count_dataframe.printSchema()
    count_dataframe.show

//    //    EVOLUTION
    val last_trends_dataframe = spark.sql("select * from twitter.last_trends")
    val trends_dataframe = count_dataframe.join(last_trends_dataframe, "hashtag")
      .withColumn("diff_rank", last_trends_dataframe("rank") - count_dataframe("rank"))
      .withColumn("diff_count", last_trends_dataframe("count") - count_dataframe("count"))
      .drop(last_trends_dataframe("rank"))
      .drop(last_trends_dataframe("count"))
      .drop(count_dataframe("count"))
      .drop(count_dataframe("count"))

    trends_dataframe.printSchema()

    count_dataframe.write.mode("overwrite").saveAsTable("twitter.last_trends")
    trends_dataframe.write.mode("overwrite").saveAsTable("twitter.trends")

    //    TOP 10
    val top_hashtags_dataframe = count_dataframe
      .orderBy(desc("count"))
      .limit(10)
    top_hashtags_dataframe.write.mode("overwrite").saveAsTable("twitter.top_hashtags")


  }

  def populateMainDB(spark: SparkSession): Unit = {
    val schema = StructType(Seq(
      StructField("message", StringType),
      StructField("hashtag", StringType),
      StructField("posted_at", TimestampType),
      StructField("created_at", TimestampType),
      StructField("lat", DoubleType),
      StructField("long", DoubleType),

    ))

    val rdd = spark.sparkContext.parallelize(Seq(
      Row(
        "at nibh in #hac habitasse",
        "#elementum",
        Timestamp.valueOf("2019-06-19 14:53:351"),
        Timestamp.valueOf("2019-04-18 23:03:07"),
        -12.03856,
        -76.928749
      ), Row(
        "dapibus duis at velit eu est congue #elementum in #hac habitasse",
        "#elementum",
        Timestamp.valueOf("2014-01-01 23:00:01"),
        Timestamp.valueOf("2018-01-01 23:00:01"),
        47.9392632,
        32.5560249
      ), Row(
        "dapibus duis at velit eu est congue #elementum in #hac habitasse",
        "#hac",
        Timestamp.valueOf("2014-01-01 23:00:01"),
        Timestamp.valueOf("2018-01-01 23:00:01"),
        47.9392632,
        32.5560249
      ), Row(
        "blandit #nam nulla integer pede justo lacinia #hac eget tincidunt eget #tempus vel",
        "#nam",
        Timestamp.valueOf("2014-01-01 23:00:01"),
        Timestamp.valueOf("2018-01-01 23:00:01"),
        47.5951141,
        37.4831419
      ), Row(
        "blandit #nam nulla integer pede justo lacinia #hac eget tincidunt eget #tempus vel",
        "#hac",
        Timestamp.valueOf("2014-01-01 23:00:01"),
        Timestamp.valueOf("2018-01-01 23:00:01"),
        47.5951141,
        37.4831419
      ), Row(
        "blandit #nam nulla integer pede justo lacinia #hac eget tincidunt eget #tempus vel",
        "#tempus",
        Timestamp.valueOf("2014-01-01 23:00:01"),
        Timestamp.valueOf("2018-01-01 23:00:01"),
        47.5951141,
        37.4831419
      ), Row(
        "blandit #aaaaa nulla integer pede justo lacinia #hac eget tincidunt eget #tempus vel",
        "#aaaaa",
        Timestamp.valueOf("2014-01-01 23:00:01"),
        Timestamp.valueOf("2018-01-01 23:00:01"),
        47.5951141,
        37.4831419
      )))

    val df1 = spark.createDataFrame(rdd, schema)
    df1.write.mode("overwrite").saveAsTable("twitter.hashtags")
  }
}
