import Registries.FeedRouterActor

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer


object QuickstartServer extends App with SocialRoutes {

  implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  //#server-bootstrapping

  val masterRegistryActor: ActorRef = system.actorOf(FeedRouterActor.props)

  lazy val routes: Route = socialRoutes

  Http().bindAndHandle(routes, "localhost", 8081)
  println(s"Server online at http://localhost:8081/")

  Await.result(system.whenTerminated, Duration.Inf)
}

