

package com.social

import Registries.FeedRegistryActor.{ActionPerformed, UpdateFeed}
import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging

import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives.{onSuccess, _}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.put
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path

import scala.concurrent.{Await, Future}
import akka.pattern.ask
import akka.util.Timeout
import models.Feed
import com.social.Registries.FeedRegistryActor._

//#user-routes-class
trait SocialRoutes extends JsonSupport {
  //#user-routes-class

  // we leave these abstract, since they will be provided by the App
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[SocialRoutes])

  // other dependencies that UserRoutes use
  def feedRegistryActor: ActorRef

  // Required by the `ask` (?) method below
  implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration

  //#all-routes

  lazy val socialRoutes: Route =
    pathPrefix("feed") {
      path("") {
        post {
          entity(as[Feed]) { feed =>
            val feedCreate: Future[ActionPerformed] =
              (feedRegistryActor ? CreateFeed(feed)).mapTo[ActionPerformed]
            onSuccess(feedCreate) { performed =>
              log.info("feed actualizado [{}]: {}", feed.user, performed.description)
              complete((StatusCodes.Created, performed))
            }
          }
        }
        get {
          parameters('user.as[Long]) { (user) =>
            val feedUpdate: Future[ActionPerformed] =
              (feedRegistryActor ? Get(user)).mapTo[ActionPerformed]
            onSuccess(feedUpdate) { performed =>
              log.info("feed actualizado [{}]:", performed.description)
              complete((StatusCodes.Created, performed))
            }
          }
        }
      }
    }
}