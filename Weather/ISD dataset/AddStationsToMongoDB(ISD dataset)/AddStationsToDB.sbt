name := "mongo_play"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
"org.apache.spark" %% "spark-core" % "1.2.1",
"org.json4s" %% "json4s-native" % "3.2.11",
"org.mongodb" %% "casbah" % "2.8.1"
)

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"
