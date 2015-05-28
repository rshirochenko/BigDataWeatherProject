name := "hhtp_server"

scalaVersion := "2.11.4"

val sprayVersion = "1.3.1"

/* dependencies */
libraryDependencies ++= Seq (
  // -- testing --
  "org.scalatest" %% "scalatest" % "2.2.2" % "test"
  , "org.scalamock" %% "scalamock-scalatest-support" % "3.1.4" % "test"
  // -- Logging --
  ,"ch.qos.logback" % "logback-classic" % "1.1.2"
  //--Akka--
  ,"com.typesafe.akka" %% "akka-testkit" % "2.3.6" % "test"
  ,"com.typesafe.akka" %% "akka-actor" % "2.3.6"
  ,"com.typesafe.akka" %% "akka-slf4j" % "2.3.6"
  // -- Spray --
  ,"io.spray" %% "spray-routing" % sprayVersion
  ,"io.spray" %% "spray-can" % sprayVersion
  ,"io.spray" %% "spray-httpx" % sprayVersion
  ,"io.spray" %% "spray-testkit" % sprayVersion % "test"
  // -- Json --
  ,"org.json4s" %% "json4s-native" % "3.2.11"
  ,"com.typesafe.play" %% "play-json" % "2.4.0-M1"
  //--MongoDb--
  ,"org.mongodb" %% "casbah" % "2.8.1"
)

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"
