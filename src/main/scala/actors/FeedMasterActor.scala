package actors

import Registries.FeedGetRouterActor.GetData
import Registries.FeedResponses.{FeedResponse, GenericResponse}
import Registries.FeedRouterActor.{CreateFeed, GetFeed, GetFeedByFriendUser, GetFeedStore}
import akka.actor.{Actor, ActorRef, Props}
import akka.util.Timeout
import models.{Feed, GenericMessage}
import akka.pattern.ask

import scala.concurrent.Await
import scala.concurrent.duration._

class FeedMasterActor extends Actor {

  def receive = {
    case CreateFeed(feed) => sender() ! ((createFeed(feed)))
    case GetFeed(user) => sender() ! (FeedResponse(getFeed(user)))
    case GetFeedStore(store) => sender() ! (FeedResponse(getFeedStore(store)))
    case GetFeedByFriendUser(user, id) => sender() ! (FeedResponse(getFeedByFriendUser(user, id)))
  }

  implicit val timeout = Timeout(10 seconds)


  private def createFeed(feed: Feed) = {
    //create feed user
    setFeed("user_"+feed.user.toString, feed)
    //create feed store
    setFeed("store_"+feed.store, feed)
    if ("id_"+feed.is_public == true) {
      //create feed id
      setFeed(feed.id, feed)
    }
    GenericResponse(GenericMessage(201, "Feed creado exitosamente"))
  }

  private def setFeed(actorId: String, feed: Feed) = {
    val child = getChild(actorId)
    child ! feed
  }

  private def getFeed(user: Long) = {
    getFunFeed(1, user, "", "")
  }

  private def getFeedStore(store: String) = {
    getFunFeed(2, 0, store, "")
  }

  private def getFeedByFriendUser(user: Long, id: String) = {
    getFunFeed(3, user, "", id)
  }

  private def getFunFeed(kind: Int, user: Long, store: String, id: String) = {
    val (idActor) = kind match {
      case 1 => "user_"+user.toString
      case 2 => "store_"+store
      case 3 => "id_"+id
    }
    val child = getChild(idActor)
    val t = ask(child, GetData())
    // println("Sender: " + t)
    val result = Await.result(t, 1 second)
    // println("Sender: " + result2)
    result.asInstanceOf[List[Feed]]
  }


  private def getChild(id: String): ActorRef =
    context.child(id).getOrElse {
      val child = context.actorOf(Props(new FeedChildActor(id)), name = id)
      child
    }
}