package location

import java.io._
import java.util.ArrayList
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._
import scala.io._
import scala.math._ 
import interestpoints.{InterestPoint_out, InterestPoint, InterestPoint_reformed}
import weather.{Weather, Weather_data}
import recomendation.{Recomendation}
import test_data.{TestData}

case class Loc(latitude: Double, longtitude: Double)

//Location class of the current location. This class should receive the location from GET request, 
//InterestPoint class - search for the nearest points of the interest
//Weather class - make interpolation of the weather and give back weather(case class weather_data) for the current location//
//Recomendation class - get nearest available places and give back the final recomended places for shown on the map of the web-service
//As result: Location class in the end should send the result to the web-server via POST request

object Location {  
	
	//Make the dump location 
	//TODO: Change this function to GET function
	def dumpJsonLocation():String= {
		val location = new Loc(40.863966, -74.098471)
	  	
	  	val json =
	    (
	     ("longtitude" -> location.longtitude) ~
	     ("latitude" -> location.latitude)
	  	)

	  	return compact(render(json))
  	}	

  	//Convert the json sting to Location scala object
	def readJsonLocation(jsonStringLocation: String):Loc = {
		implicit val formats = DefaultFormats
	   	val json = parse(jsonStringLocation)
	   	val location = json.extract[Loc]
	   	return location
	}
 		
	def main(args: Array[String]) {
	  	println("Hello all!")
	  	//Make dump json with coordinates
   		val jsonStringLocation = dumpJsonLocation()

	   	//Convert it to Location scala object
	   	val location = readJsonLocation(jsonStringLocation)
	   	println(location)

	   	//Get the nearest places
	   	val interest_places_array = InterestPoint.getNearestInterestPoints(location,1)
	    //interest_places_array.foreach(println)

	    //Get the weather class value
	    //val weather = Weather.returnWeatherData()
	    //println(weather)
	    //val stations = Weather.getNearestStationsGHCN(location,2)
	    //stations.foreach(println)
	    val weather_3days = Weather.predictWeather(location,1)
	    println(weather_3days(0))
	   
	   
	    //Make recomendation and receive the Array[InterestPoints_out] to POST it to web-service
	   	//val recomended_places = Recomendation.makeRecomendation(interest_places_array, weather)
	    //recomended_places.foreach(println)
		///val recomendation_3days = Recomendation.makeRecomendation(interest_places_array, weather_3days(0))
		//recomendation_3days.foreach(println)

	} 

}
