import scala.io._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query._
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._


case class InterestPoint(line:String) {
  val data = line.split(",")
  val id = data(0).drop(1)
  val latitude = data(1)
  val longtitude = data(2)
  val name = data(3)
  val tourism = cleanTag(data(8).split(";")(0))
  val amenity = cleanTag(data(8).split(";")(1))
  val sport = cleanTag(data(8).split(";")(2))

  //Open data and convert it to classes
  def cleanTag(str:String):String = {
	if(str.length() < 2){
		return "None"
	}
	str
  }	
}


object InterestPoint {  
  
  def openPlacesFile():Seq[InterestPoint] = {
    val file = Source.fromFile("places_usa.csv").getLines.map(l => InterestPoint(l))
    val interest_points_seq = file.toSeq
    val interest_points_seq_with_names = interest_points_seq.filter(x => x.name.length > 2)
    return interest_points_seq_with_names
  }

  def toBson(interestPoint: InterestPoint): DBObject = {
    MongoDBObject(
    	"osm_id" -> interestPoint.id,
    	"name" -> interestPoint.name,
    	"tourism" -> interestPoint.tourism,
    	"amenity" -> interestPoint.amenity,
    	"sport" -> interestPoint.sport,
    	"geometry"  -> 
      		MongoDBObject(
      			"type" -> "Point",
      			"coordinates" -> GeoCoords(interestPoint.latitude.toDouble, interestPoint.longtitude.toDouble)
      		)
	    )
	}


  def main(args: Array[String]) {
	 	//Open places dataset file and get the sequence of points of interest
	  val interest_points_seq = openPlacesFile()
	    
	  val mongoClient = MongoClient("localhost", 27017)

  	//Connect to db
  	val db = mongoClient("bigdataproject")
	
  	//Create collection
  	val places_collection = db("interestPoint")

	  //Upload all places in collection interestPoint
	  interest_points_seq.foreach{ x =>
	    places_collection.insert(toBson(x))  
	  }

    println("Done: Uploaded all datapoints in collection interestPoint points")

	} 

}




