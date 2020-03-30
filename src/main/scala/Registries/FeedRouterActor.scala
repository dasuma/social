package Registries

import actors.FeedMasterActor
import akka.actor.Props
import models.{Feed, GenericMessage}

object FeedRouterActor {

  final case class CreateFeed(feed: Feed)

  final case class GetFeed(user:Long)

  case class GetEvent(user: Long)

  def props: Props = Props[FeedMasterActor]

}

object FeedResponses {

  final case class FeedResponse(result: List[Feed])

  final case class GenericResponse(result: GenericMessage)

}