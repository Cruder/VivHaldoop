package com.cruder.vivhaldoop.share

import java.io.{FileInputStream, IOException}
import java.util.Properties

object AppProperties {
  private def init = () => {
    val input = new FileInputStream("app.properties")
    val prop = new Properties()
    try {
      prop.load(input)
      prop.stringPropertyNames().stream()
        .filter(key => key.startsWith("twitter4j"))
        .forEach(key => System.setProperty(key,prop.getProperty(key)))
    } catch {
      case io: IOException =>
        io.printStackTrace()
    } finally if (input != null) input.close()
    prop
  }

  val appProperties: Properties = init()

}
