package com.cruder.vivhaldoop.share

import java.io.{FileInputStream, IOException, File}
import java.util.Properties

object AppProperties {
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
