name := "vivhaldoop"

version := "0.1"

scalaVersion := "2.12.10"

val sparkVersion = "2.4.5"
val twitterApiVersion = "4.0.7"

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql"  % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion
)

libraryDependencies += "org.apache.bahir" %% "spark-streaming-twitter" % "2.4.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % twitterApiVersion,
  "org.twitter4j" % "twitter4j-stream" % twitterApiVersion
)
