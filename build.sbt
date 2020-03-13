name := "vivhaldoop"

version := "0.1"

scalaVersion := "2.12.10"

val sparkVersion = "2.4.0"
val twitterApiVersion = "4.0.7"
val path = "com.cruder.vivhaldoop"
//scala
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql"  % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.bahir" %% "spark-streaming-twitter" % "2.4.0",
)

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % twitterApiVersion,
  "org.twitter4j" % "twitter4j-stream" % twitterApiVersion
)

// test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"

assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}


mainClass in assembly := Some(s"$path.HelloWorld")