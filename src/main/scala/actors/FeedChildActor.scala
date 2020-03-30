package actors

import Registries.FeedRouterActor.{ GetEvent, GetFeed}
import akka.actor.OneForOneStrategy
import models.{Feed}
import akka.actor.SupervisorStrategy.Restart
import akka.persistence.PersistentActor

class FeedChildActor(idFeed: String) extends PersistentActor {

  override val supervisorStrategy = OneForOneStrategy(loggingEnabled = false) {
    case _ =>
      println("Unexpected failure: {}")
      Restart
  }

  var feedList: List[Feed] = List()

  private def createFeed(feed: Feed) = {
    println("Message received: " + feed.activity + " from - " + self.path.name);
    feedList = feedList :+ feed
    println("Sender: " + sender());
  }

  private def getFeed() = {
    println("Message get: " + feedList + " from - " + self.path.name)
    feedList
  }

  override def receiveRecover = {
    case evt: GetEvent => getFeed()
  }

  override def receiveCommand: Receive = {
    case feed: Feed => createFeed(feed)
    case GetFeed(user: Long) =>
      val _sender = sender()
      persist(GetEvent(user)) {
        evt => _sender ! (getFeed())
      }
  }

  override def persistenceId: String = idFeed

}

