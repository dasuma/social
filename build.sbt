
lazy val akkaHttpVersion = "10.0.11"
lazy val akkaVersion    = "2.5.26"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.social",
      scalaVersion    := "2.12.7"
    )),
    name := "AkkaPredictionTeam",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
      "com.typesafe.akka" %% "akka-http-caching" % "10.1.3"

    )
  )