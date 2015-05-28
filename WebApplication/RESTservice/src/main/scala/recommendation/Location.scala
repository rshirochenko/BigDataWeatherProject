package recommendation

import java.io._
import java.util.ArrayList
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._
import scala.io._
import scala.math._ 
import recommendation._

case class Loc(latitude: Double, longtitude: Double)

//Location class of the current location. This class should receive the location from GET request, 
//InterestPoint class - search for the nearest points of the interest
//Weather class - make interpolation of the weather and give back weather(case class weather_data) for the current location//
//Recomendation class - get nearest available places and give back the final recomended places for shown on the map of the web-service
//As result: Location class in the end should send the result to the web-server via POST request

object Location {  
	
	def getLocationReturnPlaces(latitude: String, longtitude: String):Array[JObject] = {
		val lat = latitude.toDouble
		val long = longtitude.toDouble
		val location = Loc(lat, long)

		//Get the nearest places
	   	val interest_places_array = InterestPoint.getNearestInterestPoints(location,1)
	   
	    //Get the weather class value
	   // val weather = Weather.returnWeatherData()
	    val weather_predictions_3days = Weather.predictWeather(location,1)
		//
	   	println(weather_predictions_3days(0))
	    //Make recomendation and receive the Array[InterestPoints_out] to return it to web-service
	    val recomended_places = Recommendation.makeRecommendation(interest_places_array, weather_predictions_3days(0))

	   	//Convert places to GeoJSON format		
		return InterestPoint.convertAllPointsToGeoJson(recomended_places,weather_predictions_3days(0))
		//return recomended_places
	}

}
