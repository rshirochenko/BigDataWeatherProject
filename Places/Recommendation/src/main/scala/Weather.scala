package weather

import scala.io._
import java.io._
import java.lang.String
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query._
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._
import scala.math._ 
import location.{Loc}
import sys.process._
import scala.collection.mutable.ArrayBuffer
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone


case class Weather_data (
	temp: Double,
	storm: Boolean,
	rain: Boolean,
	change: Boolean,
	fair: Boolean,
	dry: Boolean
)

case class Station (
	id: String,
	coordinates: (Double, Double),
	elevation: String
)

case class Measurement(
	station_id: String,
	meas: String,
	predictions: Array[Double]
)

object Weather {
	
	def returnWeatherData(): Weather_data = {
		val current_weather = Weather_data(15, false, false, false, true, false)
		return current_weather
	}

	/** Make weather prediction. */
	def predictWeather(location: Loc, radius: Double):Array[Weather_data] = {
		//Find stations for GHCN dataset(dataset 1)
		val stations_GHCN = getNearestStationsGHCN(location, radius)
		val stations_GHCN_ids = stations_GHCN.map(x => x.id)
		
		//Find stations for ISD dataset(dataset 2)
		val stations_ISD = getNearestStationsISD(location, radius)
		val stations_ISD_ids = stations_ISD.map(x => x.id)
				
		//Find the predictions for TMAX, TMIX, BAR(barometer classifications)
		val tmin_all = getPredictionTmin(stations_GHCN_ids)
		val tmax_all = getPredictionTmax(stations_GHCN_ids)
		val bar_1day = getPredictionBar(stations_ISD_ids)
		
		//Some stations from database does not have measurement for current day, filter them out
		val found_stations_id_GHCN = tmax_all.map(x => x.station_id)
		val found_stations_id_ISD = bar_1day.map(x => x.station_id)

		//Filter only nearest stations
		val stations_coordinates_GHCN = stations_GHCN.filter(x=> found_stations_id_GHCN contains x.id).map(x => x.coordinates).take(3)
		val stations_coordinates_ISD = stations_ISD.filter(x=> found_stations_id_ISD contains x.id).map(x => x.coordinates).take(2)
		
		//Perform interpolation based on the distances from stations 
		val tmin_3days = interpolateDaysValue(tmin_all, location, stations_coordinates_GHCN)
		val tmax_3days = interpolateDaysValue(tmax_all, location, stations_coordinates_GHCN)
		val bar_interpolated = interpolateValue(stations_coordinates_ISD, location, bar_1day.flatMap(x => x.predictions)).round.toInt 
		
		//Perform the interpolation based on the current time(because we have tmin and tmax)
		var temp_interpolated = new ArrayBuffer[Double]()
		for (i <- 0 until tmin_3days.length) {
			temp_interpolated += interpolateTemperature(tmin_3days(i), tmax_3days(i), location)
		} 
		val temp_final = temp_interpolated.toArray //Array[Double]
		
		//Form array with Weather_data class for 3 days predictions
		var weather_3days = new ArrayBuffer[Weather_data]()
		for(i <-0 to 2) {
			val temperature = trunc(temp_final(i)/10, 1)
			val weather_day_prediction = bar_interpolated match {
				case 5 => Weather_data(temperature, true, false, false, false, false)
				case 4 => Weather_data(temperature, false, true, false, false, false)
				case 3 => Weather_data(temperature, false, false, true, false, false)
				case 2 => Weather_data(temperature, false, false, false, true, false)
				case 1 => Weather_data(temperature, false, false, false, false, true)
			}
			weather_3days += weather_day_prediction
		}
		weather_3days.toArray
	}

	/** Parsing the result data */
	def getPredictionTmin(stations: Array[String]): Array[Measurement] = {
		var predictions_by_station = new ArrayBuffer[Measurement]()
		for(i <-0 to (stations.size-1)){
			try { 
			  predictions_by_station += getTminForStation(stations(i))
			} catch {
			  case e: Exception => 
			}
		}
		predictions_by_station.toArray
	}

	def getPredictionTmax(stations: Array[String]): Array[Measurement] = {
		var predictions_by_station = new ArrayBuffer[Measurement]()
		for(i <-0 to (stations.size-1)){
			try { 
			  predictions_by_station += getTmaxForStation(stations(i))
			} catch {
			  case e: Exception => 
			}
		}
		predictions_by_station.toArray
	}

	def getPredictionBar(stations: Array[String]): Array[Measurement] = {
		var predictions_by_station = new ArrayBuffer[Measurement]()
		for(i <-0 to (stations.size-1)){
			try { 
			  predictions_by_station += getBarForStation(stations(i))
			} catch {
			  case e: Exception => 
			}
		}
		predictions_by_station.toArray
	}

	def getTminForStation(station_id:String): Measurement = {
		val file = Source.fromFile("/home/rshir/tmin.txt").getLines.toList.map(x => x.split(";"))
		val line_with_predictions = file.filter(x => x(0) contains station_id).map(_(1))
		Measurement(station_id, "tmin", formDays(line_with_predictions(0)))
	}

	def getTmaxForStation(station_id:String): Measurement = {
		val file = Source.fromFile("/home/rshir/tmax.txt").getLines.toList.map(x => x.split(";"))
		val line_with_predictions = file.filter(x => x(0) contains station_id).map(_(1))
		Measurement(station_id, "tmax", formDays(line_with_predictions(0)))
	}

	def getBarForStation(station_id:String): Measurement = {
		val file = Source.fromFile("/home/rshir/pressure.txt").getLines.toList.map(x => x.split(";"))
		val prediction = file.filter(x => x(0) contains station_id).map(_(1))
		Measurement(station_id, "bar", Array(prediction(0).toDouble))
	}

	/** Convert string values from SSH String output to Array[Double] of predicted measurements. */
	def formDays(str: String):Array[Double] = {
			val data = str.split(",")
			val day1 = data(0).drop(1)
			val day2 = data(1)
			val day3 = data(2).dropRight(1)
			Array(day1, day2, day3).map(_.toDouble)
	}

	/** Searching for the nearest weather stations. */
	//Get all interesting points in the input args radius 
	//Output: Array(InterestPoint_out) - array with case class InterestPoint_out(place with intersting points)
	def getNearestStationsGHCN(location: Loc, radius: Double):Array[Station] = {
		//Connect to db and get interesting places
		val mongoClient = MongoClient("localhost", 27017)
		val db = mongoClient("bigdataproject")
		val stations_collection = db("stations_ghcn")
		
		//Location coordinates
		val lat = location.latitude
		val long = location.longtitude
		
		//Query with the range of the input radius(in geographical degrees)
		val points = "coordinates" $near (lat, long) $maxDistance radius
		val result = stations_collection.find(points).toArray
		return result.map{x => fromBson(x)}.filter(x => x.elevation.toDouble < 600.0 )
	}

	def getNearestStationsISD(location: Loc, radius: Double):Array[Station] = {
		//Connect to db and get interesting places
		val mongoClient = MongoClient("localhost", 27017)
		val db = mongoClient("bigdataproject")
		val stations_collection = db("stations_isd")
		
		//Location coordinates
		val lat = location.latitude
		val long = location.longtitude
		
		//Query with the range of the input radius(in geographical degrees)
		val points = "coordinates" $near (lat, long) $maxDistance radius
		val result = stations_collection.find(points).toArray
		return result.map{x => fromBson(x)}.filter(x => x.elevation.toDouble < 600.0 )
	}

	/** Interpolation part. */
	//Calculate interpolated values predictions for 3 following days
	//Args: value:Array[Measurement] - values from all stations for one feature measurement(TMIN, TMAX only)
	def interpolateDaysValue(values: Array[Measurement], location: Loc, stations_coordinates: Array[(Double,Double)]):Array[Double] = {
			val day1 = new ArrayBuffer[Double]()
			val day2 = new ArrayBuffer[Double]()
			val day3 = new ArrayBuffer[Double]()
			values.foreach{x =>
				day1 += x.predictions(0)
				day2 += x.predictions(1)
				day3 += x.predictions(2)
			}
			val interpolated_value_day1 = interpolateValue(stations_coordinates, location, day1.toArray)
			val interpolated_value_day2 = interpolateValue(stations_coordinates, location, day2.toArray)
			val interpolated_value_day3 = interpolateValue(stations_coordinates, location, day3.toArray)
			Array(interpolated_value_day1,interpolated_value_day2,interpolated_value_day3)
	}

	/** Calculate interpolation part for 1 day for 1 feature(e.g. TMIN). */
	def interpolateValue(stations_coordinates:Array[(Double,Double)], location:Loc, values: Array[Double]):Double = {
		//Form the array with distance to location from each stations
		val distances = stations_coordinates.map{ x => calcDistance(x, location) }
		//Calc the weight
		val weights = distances.map(x => 1/x)
		//Calc noramalization coefficient
		val K = calcK(weights)
		//Calc weight value
		val weightValue = kWeightAverage(weights, values, K)
		weightValue
	}

	/** Calulates the distance between two station location cordinates. */
	def calcDistance(station_coordinates:(Double,Double), location: Loc):Double = {
		var lat1 = station_coordinates._1
		var long1 = station_coordinates._2

		var lat2 = location.latitude
		var long2 = location.longtitude

		var R = 6371; // km
		var dLat = toRadians(lat2-lat1)
		var dLon = toRadians(long2-long1)
		lat1 = toRadians(lat1)
		lat2 = toRadians(lat2)

		var a = sin(dLat/2) * sin(dLat/2) + sin(dLon/2) * sin(dLon/2) * cos(lat1) * cos(lat2)
		var c = 2 * atan2(sqrt(a), sqrt(1-a)) 
		var distance = R * c
		
		return distance
	}

	/** Calculate normalization coefficient for inverse weighting averaging. */
	def calcK(weights: Array[Double]):Double = {
		var K = 0.0
		for(i <-0 to (weights.length-1)) {
			K = K + weights(i)
		}
		1/K 
	}

	/** Averaging the result of the calculation based on the center mass algorithm calculation. */
	def kWeightAverage(weights: Array[Double], values: Array[Double], K: Double):Double = {
		var result = 0.0
		for(i <-0 to (weights.length-1)) {
			result = result + weights(i)*values(i)
		}
		return (K*result)
	}

	/** MongoDB related functions. */
	/** Convert the Bson object from MongoDB database to case class InterestPoint_out(place with intersting points). */
   	def fromBson(o: DBObject):Station = {
    	Station(
    		o.as[String]("station_id"),
    		readCoordinates(o.as[DBObject]("coordinates").toString),
	    	o.as[String]("elevation")
	    )    	
	}

	/** Convert coordinates from Bson database output. */
	def readCoordinates(ob: String):(Double, Double) = {
  		val latitude = ob.split(",")(0).drop(1).toDouble
  		val longtitude = ob.split(",")(1).dropRight(1).toDouble
   		return (latitude,longtitude)
  	}

  	/** Temperature interpolation based on TMIN, TMAX and time for summer time. */
  	def interpolateTemperature(tmin: Double, tmax: Double, location: Loc):Double = {
  		val currentHour = getCurrentHour(location)
  		var temp = 0.0
  		if(currentHour < 8) temp = tmin
  		if((currentHour >= 8) && (currentHour <= 11)) temp = linearFuncTemp1(tmin,tmax,currentHour)  	
  		if((currentHour > 11) && (currentHour < 18)) temp = tmax
  		if((currentHour >= 18) && (currentHour <= 21)) temp = linearFuncTemp2(tmin,tmax,currentHour)
  		if(currentHour > 21) temp = tmin
  		return temp
  	}

  	/** Linear function for the hours between 8h and 11h. */ 
  	def linearFuncTemp1(tmin: Double, tmax: Double, hour: Double):Double = {
  		val a:Double = (tmax-tmin)/3
  		val b:Double = tmin - 8*a
  		val y:Double = a*hour + b
  		return y
  	}

  	/** Linear function for the hours between 18h and 21h. */ 
  	def linearFuncTemp2(tmin: Double, tmax: Double, hour: Double):Double = {
  		val a:Double = (tmin-tmax)/3
  		val b:Double = tmax - 18*a
  		val y:Double = a*hour + b
  		return y
  	}

  	/** Take the current hour. */
	def getCurrentHour(location: Loc): Int = {
	  val today = Calendar.getInstance().getTime()
	  val timezone = getTimezone(location)
	  val setup_zone = TimeZone.getTimeZone(timezone)
	  val hourFormat = new SimpleDateFormat("k")
	  hourFormat.setTimeZone(setup_zone)
	  val currentHour = hourFormat.format(today) 
	  return currentHour.toInt
	}  	

	/** Get the location timezone. */
	def getTimezone(location: Loc):String = {
		val long = location.longtitude
		var timezone = ""
		if(long < -114.0) timezone = "America/Los_Angeles"
		if( (long >= -114.0) && (long < -104.0) ) timezone = "US/Mountain"
		if( (long >= -104.0) && (long < -90.0) ) timezone = "CST"
		if(long >= -90.0) timezone = "America/New_York"
		return timezone  
	}

	//Rounding the double number function
	def trunc(x: Double, n: Int) = {
	  def p10(n: Int, pow: Long = 10): Long = if (n==0) pow else p10(n-1,pow*10)
	  if (n < 0) {
	    val m = p10(-n).toDouble
	    math.round(x/m) * m
	  }
	  else {
	    val m = p10(n).toDouble
	    math.round(x*m) / m
	  }
	}

  	/*Tests*/
  	//Test for distance calculation. The result compares with result on the online website geographic calculator
	def test_calcDistance() {
		val loc = new Loc(43.7333,-96.6333)
		var distance = calcDistance((40.5268,105.1113),loc)
		
		if(distance > 10300 && distance < 10500 ){
			println("calcDistance test: SUCCESSFUL")
		} else {
			println("calcDistance test: FAILED")
		}
	}
}
