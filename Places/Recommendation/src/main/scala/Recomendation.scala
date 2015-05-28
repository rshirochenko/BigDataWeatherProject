package recomendation

import interestpoints.{InterestPoint_reformed, InterestPoint}
import weather.{Weather, Weather_data}
import scala.collection.mutable.ArrayBuffer

/*
Looking for interest places based on the weather prediction

Input args: places: Array[InterestPount_removed] - all the nearest interested places
			weather: Weather_data - predicted weather class

To get only tourism nearest places:
	val only_tourist_places = InterestPoint.getTourismPoints(places)

To get only cultural nearest places:
	val only_cultural_places = InterestPoint.getCulturalPoints(places)

To get only leisure nearest places:
	val only_leisure_places = InterestPoint.getLeisurePoints(places)

And so on... (check other functiion in InterestPoint.scala)

tourism tags: "attraction","information", "viewpoint"
cultural tags : "arts_centre", "planetarium", "theatre"
parks tags: "picnic_site"									 
leisure tags: "theme_park","zoo","cinema","gym","marketplace","restaurant"
nightlife tags: "nightclub","casino","striplub","cinema","bar","pub"
sport tags: "9pin","10pin","american_football","base","baseball","basketball","bmx","boxing","canoe","cliff_diving",
	"climbing_adventure","roller_skating","scuba_diving","sailing", "safety_training", "skateboard", "skiing", "surfing",
	"swimming","water_ski"


*/

case class Recomended_places (
	places: Array[InterestPoint_reformed],
	weather: Weather_data,
	predicted_day: Int
)

object Recomendation {

	def makeRecomendationFor3Days(places:Array[InterestPoint_reformed], weather_3days: Array[Weather_data]):Array[Recomended_places] = {
		var recomendation_3days = new ArrayBuffer[Recomended_places]()
		for(i <-0 to 2){
			recomendation_3days += Recomended_places(makeRecomendation(places,weather_3days(i)), weather_3days(i), i)
		}
		recomendation_3days.toArray
	}

	def makeRecomendation(places: Array[InterestPoint_reformed], weather: Weather_data):Array[InterestPoint_reformed] = {  
		var coolActivityTags:List[String] = List("skiing");
      	var hotActivityTags:List[String] = List("viewpoint", "theme_park","picnic_park", "zoo", "marketplace",
          "canoe", "cliff_diving", "scuba_diving", "sailing", "safety_training", "surfing", "water_ski", "swimming");
		if (weather.fair || weather.dry){
			return places.filter{ interestPoint => 
				(
					(weather.temp <= 5 && interestPoint.sport.contains("american_football")) ||
					{
						(weather.temp >= 10 && weather.temp <= 30) &&
						(hotActivityTags.contains(interestPoint.cultural) || hotActivityTags.contains(interestPoint.leisure) || hotActivityTags.contains(interestPoint.sport) || hotActivityTags.contains(interestPoint.parks) || hotActivityTags.contains(interestPoint.tourism))
					}
				)
			}   
		} else { 
			return places.filter{ interestPoint => 
				(
					interestPoint.cultural.contains("arts_centre") || interestPoint.cultural.contains("planetarium") || interestPoint.leisure.contains("cinema") || interestPoint.leisure.contains("gym") ||
					interestPoint.sport.contains("9pin") || interestPoint.sport.contains("10pin") || interestPoint.sport.contains("boxing") || interestPoint.nightlife.contains("nightclub")
				)
            } 
		}
	}
}