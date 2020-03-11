name := "project"

version := "0.1"

scalaVersion := "2.11.0"

val sparkVersion = "2.4.5"
val twitterApiVersion = "4.0.7"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
)

libraryDependencies += "org.apache.spark" %% "spark-streaming-twitter" % "1.6.3"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % twitterApiVersion,
  "org.twitter4j" % "twitter4j-stream" % twitterApiVersion
)