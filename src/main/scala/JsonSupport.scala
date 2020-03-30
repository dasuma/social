import Registries.FeedResponses.{FeedResponse, GenericResponse}
import models.{Feed, GenericMessage}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  import DefaultJsonProtocol._

  implicit val feedJsonFormat = jsonFormat2(Feed)

  implicit val FeedResponseJsonFormat = jsonFormat1(FeedResponse)

  implicit val GenericResponseJsonFormat = jsonFormat2(GenericMessage)

}