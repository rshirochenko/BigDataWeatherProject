The RESTservice(web-service). Developed based on the Spray Framework(scala).

Listening for the GET request: http://ip_address:/location/lat/long 
Where (lat - user location latitude; long - user location longtitude)

After receiving the GET request from web-interface(location of the web-interface in the github repository: WebApplication/WebInterface)
The the RESTservice(BasicSample.scala) calls the Location.scala(recommendation package) and sends the user location data to recommedation part.
After in Location.scala we call the InterestPlaces class for searching the neareast nearest interest places from MongoDB database. Then we call
Weather class where we search the nearest weather stations from MongoDB, make interpolations and return predicted weathers values.
After we call Recommendation class that filters the places based on the current predicted weather`s values.
The last step is to convert recomended places in the GeoJSON format and reponse on the request with them.

All the parts of the RESTService except Recomendation.scala developed by Roman Shirochenko.
Recommendation.scala developed by <anonymous>.
	
