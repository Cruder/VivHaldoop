package com.cruder.vivhaldoop.share

import org.apache.spark.sql.SparkSession

object SharedSpark {

  val spark: SparkSession = SparkSession
    .builder()
    .master("local")
    .appName("vivhaldoop")
    .getOrCreate()
}
