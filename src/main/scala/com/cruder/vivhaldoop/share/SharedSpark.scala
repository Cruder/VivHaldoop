package com.cruder.vivhaldoop.share

import com.cruder.vivhaldoop.share.AppProperties.appProperties
import org.apache.spark.sql.SparkSession;

object SharedSpark {

  private def init = () => {
    SparkSession
      .builder()
      .master(appProperties.getProperty("spark.master"))
      .appName(appProperties.getProperty("app.name"))
      .getOrCreate()
  }

  val spark: SparkSession = init()
}
