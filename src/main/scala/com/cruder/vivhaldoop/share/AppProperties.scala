package com.cruder.vivhaldoop.share

import java.io.{File, FileInputStream, IOException}
import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object AppProperties {
  val conf: SparkConf = new SparkConf()
    .setAppName("vivhaldoop")

  val spark: SparkSession = SparkSession
    .builder
    .config(conf)
    .getOrCreate()

  val path: String = "/user/vivhaldoop/"

  private def init = () => {

    val prop = new Properties()
    try {
      val properties = new File("app.properties")
      prop.load(new FileInputStream(properties))
    } catch {
      case io: IOException =>
        io.printStackTrace()
    }
    prop
  }

  val appProperties: Properties = init()
}
