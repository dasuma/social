
lazy val akkaHttpVersion = "10.0.11"
lazy val akkaVersion = "2.5.26"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.social",
      scalaVersion := "2.12.7"
    )),
    name := "AkkaPredictionTeam",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.akka" %% "akka-http-caching" % "10.1.3",
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
    )
  )
