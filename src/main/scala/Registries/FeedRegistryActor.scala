package com.social.Registries

import akka.actor.{Actor, ActorLogging, Props}
import models.Feed

object FeedRegistryActor {
  final case class ActionPerformed(description: String)
  final case class UpdateFeed(feed: Feed)
  final case class Get(user:Long)
  def props: Props = Props[FeedRegistryActor]
}

class FeedRegistryActor extends Actor with ActorLogging {

  import FeedRegistryActor._

  def receive: Receive = {
    case UpdateFeed(feed) =>
      val y = ActionPerformed(updateFeed(feed).toString)
      sender() ! ActionPerformed(y.toString)
    case Get(user) =>
      sender() ! ActionPerformed(getScoreByTeam(user).toString)
  }

  private def updateFeed(feed: Feed): String = {
     "ok"
  }

  private def getScoreByTeam(user: Long): Int = {
    12
  }
}
