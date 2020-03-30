package actors

import Registries.FeedResponses.{FeedResponse, GenericResponse}
import Registries.FeedRouterActor.{CreateFeed, GetFeed}
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


  }

  private def createFeed(feed: Feed) = {

    println("Message received: " + feed + " from - " + self.path.name);
    println("Sender: " + sender())
    val child = getChild(feed.user.toString)
    child ! feed
    GenericResponse(GenericMessage(201,"Feed creado exitosamente"))
   // val t = ask(test, getFeed())
   // println("Sender: " + t)
   // val result2 = Await.result(t, 1 second)
   // println("Sender: " + result2)
   // result2.asInstanceOf[List[Feed]]
  }

  private def getFeed(user: Long) = {
    implicit val timeout = Timeout(10 seconds);
    val child = getChild(user.toString)
    val t = ask(child, GetFeed(user))
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