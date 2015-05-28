/**

Hey mate, I'm just writing the scenario below, tell me if you agree 


1) The server send a GeoJSON file to the client
2) Using Angular, you are going to create an array of Point of Interest (POI)
3) In a loop, for each POI, we have to generate a new Google Maps Marker, right ? TUTORAIL : http://jsfiddle.net/72C6g/
4) Then, every POI is displayed on the map



==========

Then we have to filter the POI displayed using angular :

On the menu on the left, the user will check and unchecked boxes (e.g. : 'tourism : checked', 'sport : unchecked')
Then we have to create a listener on theses buttons to filter the markers displayed on the map (maybe just with the filter function in Angular ?)

TUTORIAL : https://www.codersclan.net/ticket/337


=====


I'll generate the code for the geolocalisation part.



Do you agree with this scenario ?

**/





$(document).ready(function(){/* google maps -----------------------------------------------------*/
google.maps.event.addDomListener(window, 'load', initialize);

function initialize() {


/*
Creation of the map
*/

  var mapOptions = {
    center: latlng,
    scrollWheel: false,
    zoom: 13
  };
  

  var markers = [];

  var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);


  /*
 Map created
  */



/* 
GEOLOCALISATION 
*/

if (navigator.geolocation)
  var watchId = navigator.geolocation.watchPosition(successCallback,
                            null,
                            {enableHighAccuracy:true});
else
  alert("Your browser can't display your current location");   
 
function successCallback(position){
  map.panTo(new google.maps.LatLng(34.0204989,-118.4117325));
  //Normally : new google.maps.LatLng(position.coords.latitude, position.coords.longitude) instead of
  var myPos = new google.maps.Marker({
    position: new google.maps.LatLng(34.0204989,-118.4117325),
    map: map,
    animation: google.maps.Animation.DROP,
    title: 'You\'re here ! '
  });
}


/* 
 end GEOLOCALISATION 
*/










/*
SEARCHBAR
*/





// ==== SEARCHBAR ====

// Create the search box and link it to the UI element.
  var input = /** @type {HTMLInputElement} */(
      document.getElementById('pac-input'));
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

  var searchBox = new google.maps.places.SearchBox(
    /** @type {HTMLInputElement} */(input));

  // Listen for the event fired when the user selects an item from the
  // pick list. Retrieve the matching places for that item.
  google.maps.event.addListener(searchBox, 'places_changed', function() {
    var places = searchBox.getPlaces();

    if (places.length == 0) {
      return;
    }
    for (var i = 0, marker; marker = markers[i]; i++) {
      marker.setMap(null);
    }

    // For each place, get the icon, place name, and location.
    markers = [];
    var bounds = new google.maps.LatLngBounds();
    for (var i = 0, place; place = places[i]; i++) {
      var image = {
        url: place.icon,
        size: new google.maps.Size(71, 71),
        origin: new google.maps.Point(0, 0),
        anchor: new google.maps.Point(17, 34),
        scaledSize: new google.maps.Size(25, 25)
      };

      // Create a marker for each place.
      var marker = new google.maps.Marker({
        map: map,
        icon: image,
        title: place.name,
        position: place.geometry.location
      });

      markers.push(marker);

      bounds.extend(place.geometry.location);
    }

  });




/*
End SEARCHBAR
*/











/* 
Creation of one marker 
*/


  /* latLong */

//for example :  
  var latlng = new google.maps.LatLng(40.719675, -73.996375);

//real one : 
  //var latlng[i] = new google.maps.LatLng(poi[i].geometry.coordinates[0], poi[i].geometry.coordinates[0]);


  var marker = new google.maps.Marker({
      position: latlng,
      map: map,
      title: 'Hello World!'
  });


  var contentString = '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<h4 id="firstHeading" class="firstHeading">{{poi[i].properties.name}}</h4>'+
      '<div id="bodyContent">'+
      '<p>'+
        '<ul>'+
          '<li>'+ '<b>Type</b> :  {{poi.properties.tourism}}' + '</li>'+
          '<li>'+ '<b>Building</b> :  {{poi.properties.building}}' + '</li>'+
          '<li>'+ '<b>Amenity</b> :  {{poi.properties.amenity}}' + '</li>'+
          '<li>'+ '<b>Historic</b> :  {{poi.properties.historic}}' + '</li>'+
          '<br>'+
        '</ul>'+
      '</p>'
      '</div>'+
      '</div>';

  var infowindow = new google.maps.InfoWindow({
      content: contentString
  });


  google.maps.event.addListener(marker, 'click', function() {
    infowindow.open(map,marker);
  });


marker.setVisible(true);






};
/* end google maps -----------------------------------------------------*/
});