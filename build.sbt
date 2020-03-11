name := "project"

version := "0.1"

scalaVersion := "2.13.1"

val sparkVersion = "2.4.5"
val twitterApiVersion = "4.0.7"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-twitter" % sparkVersion
)

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % twitterApiVersion,
  "org.twitter4j" % "twitter4j-stream" % twitterApiVersion
)