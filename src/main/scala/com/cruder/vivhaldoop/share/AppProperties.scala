package com.cruder.vivhaldoop.share

import java.io.{File, FileInputStream, IOException}
import java.util.Properties

object AppProperties {
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
