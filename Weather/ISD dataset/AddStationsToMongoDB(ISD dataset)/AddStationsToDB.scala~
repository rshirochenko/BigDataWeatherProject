import scala.io._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query._
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._


case class Station(line:String) {
  val data = line.split(";")
  val id = data(0)
  val latitude = data(1)
  val longtitude = data(2)
  val elevation = data(3)
}


object StationAdd {  
  
  def openPlacesFile():Seq[Station] = {
    val file = Source.fromFile("stations_for_db.csv").getLines.map(l => Station(l))
    val stations_seq = file.toSeq
    return stations_seq
  }

  def toBson(station: Station): DBObject = {
    MongoDBObject(
      "station_id" -> station.id,
      "coordinates" -> GeoCoords(station.latitude.toDouble, station.longtitude.toDouble),
      "elevation" -> station.elevation
    )
  }


  def main(args: Array[String]) {
    //Open places dataset file and get the sequence of points of interest
    val stations_seq = openPlacesFile()
      
    val mongoClient = MongoClient("localhost", 27017)

    //Connect to db
    val db = mongoClient("bigdataproject")
  
    //Create collection
    val stations_collection = db("stations_ghcn")

    //Upload all places in collection interestPoint
    stations_seq.foreach{ x =>
      stations_collection.insert(toBson(x))  
    }

    println("Done: Uploaded all staions in collection stations points")

  } 

}




