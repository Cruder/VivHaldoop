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

    var input: FileInputStream = null
    val prop = new Properties()
    try {
      val properties = new File("app.properties")
      input = new FileInputStream(properties)
      prop.load(input)
    } catch {
      case io: IOException =>
        io.printStackTrace()
    } finally {
      if (input != null) input.close()
    }
    prop
  }

  val appProperties: Properties = init()
}
