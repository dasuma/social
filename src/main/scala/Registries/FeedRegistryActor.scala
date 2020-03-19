package com.social.Registries

import akka.actor.{Actor, ActorLogging, Props}
import models.Feed
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.persistence.PersistentActor
import spray.json.DefaultJsonProtocol

object FeedRegistryActor {

  final case class ActionPerformed(description: String)

  final case class FeedResponse(result: List[Feed])

  final case class CreateFeed(feed: Feed)

  final case class Get(user: Long)

  def props: Props = Props[FeedRegistryActor]
}

class FeedRegistryActor extends PersistentActor with ActorLogging {

  import FeedRegistryActor._

  var feedList: List[Feed] = List()

  def receive: Receive = {
    case CreateFeed(feed) =>
      val _sender = sender()
      persist(FeedEvent(score)) {
        evt =>
          updateFeed(feed)
          _sender ! FeedResponse(feedList)
      }
    case Get(user) => sender() ! ActionPerformed(getScoreByTeam(user).toString)
  }

  private def updateFeed(feed: Feed) = {
    feedList += feed
  }

  private def getScoreByTeam(user: Long): Int = {
    12
  }

  override def receiveRecover = {
    case evt: FeedEvent => updateFeed(evt.feed)
  }

  object FeedRegistryActor {

    case class FeedCommand(feed: Feed)

    case class FeedEvent(feed: Feed)

  }

}
