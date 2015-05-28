package recommendation

import scala.collection.mutable.ArrayBuffer
import recommendation._

case class Recomended_places (
	places: Array[InterestPoint_reformed],
	weather: Weather_data,
	predicted_day: Int
)


object Recommendation {

	def makeRecommendationFor3Days(places:Array[InterestPoint_reformed], weather_3days: Array[Weather_data]):Array[Recomended_places] = {
		var recomendation_3days = new ArrayBuffer[Recomended_places]()
		for(i <-0 to 2){
			recomendation_3days += Recomended_places(makeRecommendation(places,weather_3days(i)), weather_3days(i), i)
		}
		recomendation_3days.toArray
	}

	def makeRecommendation(places: Array[InterestPoint_reformed], weather: Weather_data):Array[InterestPoint_reformed] = {  
		var coolActivityTags:List[String] = List("skiing");
      	var hotActivityTags:List[String] = List("viewpoint", "theme_park","picnic_park", "zoo", "marketplace",
          "canoe", "cliff_diving", "scuba_diving", "sailing", "safety_training", "surfing", "water_ski", "swimming");
		if (weather.fair || weather.dry){
			return places.filter{ interestPoint => 
				(
					(weather.temp <= 5 && interestPoint.sport.contains("skiing")) ||
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