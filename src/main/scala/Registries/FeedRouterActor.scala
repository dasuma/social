package Registries

import actors.FeedMasterActor
import akka.actor.Props
import models.{Feed, GenericMessage}

object FeedRouterActor {

  final case class CreateFeed(feed: Feed)

  final case class GetFeed(user: Long)

  final case class GetFeedStore(store: String)

  final case class GetFeedByFriendUser(user: Long, id: String)

  case class GetEvent()

  def props: Props = Props[FeedMasterActor]

}

object FeedResponses {

  final case class FeedResponse(result: List[Feed])

  final case class GenericResponse(result: GenericMessage)

}

object FeedGetRouterActor {

  final case class GetData()

}