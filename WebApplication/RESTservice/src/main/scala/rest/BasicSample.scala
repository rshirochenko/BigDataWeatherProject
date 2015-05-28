package rest

import scala.concurrent.duration._

import akka.actor.{ Actor, ActorLogging, ActorSystem, Props }
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization.{ read, write }
import spray.can.Http
import spray.httpx.Json4sSupport
import spray.routing._
import spray.can.server.Stats
import spray.http.StatusCodes._
import spray.http.HttpHeaders.RawHeader
import spray.http.MediaType
import recommendation._
import spray.http.MediaTypes._
import spray.http.HttpHeaders._
import spray.http.ContentTypes._

/* Used to mix in Spray's Marshalling Support with json4s */
object Json4sProtocol extends Json4sSupport {
  implicit def json4sFormats: Formats = DefaultFormats
}


/* Our route directives, the heart of the service.
 * Note you can mix-in dependencies should you so chose */
trait SpraySampleService extends HttpService {
  import Json4sProtocol._
  import WorkerActor._
  
  //These implicit values allow us to use futures
  //in this trait.
  implicit def executionContext = actorRefFactory.dispatcher
  implicit val timeout = Timeout(5 seconds)

  //Our worker Actor handles the work of the request.
  val worker = actorRefFactory.actorOf(Props[WorkerActor], "worker")

  val spraysampleRoute = {
    path("location"/Segment/Segment) { (lat,long) =>
      get{
        respondWithMediaType(`application/json`){
          respondWithHeader(RawHeader("Access-Control-Allow-Origin","*")){
            complete(Location.getLocationReturnPlaces(lat, long))
          }
        }
      }
    }
  }
}
