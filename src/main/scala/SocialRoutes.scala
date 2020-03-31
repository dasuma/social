import Registries.FeedResponses.{FeedResponse, GenericResponse}
import Registries.FeedRouterActor.{CreateFeed, GetFeed, GetFeedByFriendUser, GetFeedStore}
import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging

import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives.{onSuccess, _}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.RouteDirectives.complete

import scala.concurrent.Future
import akka.pattern.ask
import akka.util.Timeout
import models.{Feed, GenericMessage}

trait SocialRoutes extends JsonSupport {
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[SocialRoutes])

  def masterRegistryActor: ActorRef

  implicit lazy val timeout = Timeout(5.seconds)

  //#all-routes

  lazy val socialRoutes: Route =

    pathPrefix("user") {
      path("") {
        get {
          parameters('user.as[Long]) { (user) =>
            val feed: Future[FeedResponse] =
              (masterRegistryActor ? GetFeed(user)).mapTo[FeedResponse]
            onSuccess(feed) { performed =>
              log.info("get_feed [{}]:", performed)
              complete((StatusCodes.Accepted, performed))
            }
          }
        }
      }
    } ~ pathPrefix("store") {
      path("") {
        get {
          parameters('store.as[String]) { (store) =>
            val feedUpdate: Future[FeedResponse] =
              (masterRegistryActor ? GetFeedStore(store)).mapTo[FeedResponse]
            onSuccess(feedUpdate) { performed =>
              log.info("get_feed [{}]:", performed)
              complete((StatusCodes.Accepted, performed))
            }
          }
        }
      }
    } ~ pathPrefix("id") {
      path("") {
        get {
          parameters('store.as[String]) { (store) =>
            val feedUpdate: Future[FeedResponse] =
              (masterRegistryActor ? GetFeedStore(store)).mapTo[FeedResponse]
            onSuccess(feedUpdate) { performed =>
              log.info("get_feed [{}]:", performed)
              complete((StatusCodes.Accepted, performed))
            }
          }
        }
      }
    } ~ pathPrefix("/store/friend") {
      path("") {
        get {
          parameters('user.as[Long], 'id.as[String]) { (user, id) =>
            val feedUpdate: Future[FeedResponse] =
              (masterRegistryActor ? GetFeedByFriendUser(user, id)).mapTo[FeedResponse]
            onSuccess(feedUpdate) { performed =>
              log.info("get_feed [{}]:", performed)
              complete((StatusCodes.Accepted, performed))
            }
          }
        }
      }
    } ~ pathPrefix("feed") {
      post {
        entity(as[Feed]) { feed =>
          val feedCreate1: Future[GenericResponse] =
            (masterRegistryActor ? CreateFeed(feed)).mapTo[GenericResponse]
          onSuccess(feedCreate1) { performed =>
            log.info("feed creado [{}]: {}", feed.user, performed)
            complete(StatusCodes.Created, performed.result)
          }
        }
      }
    }
}


