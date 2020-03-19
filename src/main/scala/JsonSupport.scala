package com.social

import Registries.FeedRegistryActor.ActionPerformed
import Registries.FeedRegistryActor.FeedResponse
import models.Feed

//#json-support
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val feedJsonFormat = jsonFormat2(Feed)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
  implicit val FeedResponseJsonFormat = jsonFormat1(FeedResponse)

}

//#json-support