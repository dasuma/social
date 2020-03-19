package com.social

//#quick-start-server
import Registries.FeedRegistryActor

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import Registries.FeedRegistryActor._


//#main-class
object QuickstartServer extends App with SocialRoutes {

  // set up ActorSystem and other dependencies here
  //#main-class
  //#server-bootstrapping
  implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  //#server-bootstrapping

  val feedRegistryActor: ActorRef = system.actorOf(FeedRegistryActor.props, "feedRegistryActor")

  //#main-class
  // from the UserRoutes trait
  lazy val routes: Route = socialRoutes
  //#main-class

  //#http-server
  Http().bindAndHandle(routes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/")

  Await.result(system.whenTerminated, Duration.Inf)
  //#http-server
  //#main-class
}
//#main-class
//#quick-start-server