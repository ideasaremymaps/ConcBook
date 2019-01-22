<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String auth = session.getAttribute("auth").toString();
%>   

<!DOCTYPE html>   
<html>
<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<style>
      #map-canvas {
        width: 600px;
        height: 400px;
      }
    </style>
    <script src="https://maps.googleapis.com/maps/api/js"></script>
    <script
 src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"
 type="text/javascript"></script>
<script src="http://connect.facebook.net/en_US/all.js"></script>
<script src="js/facebookApi.js"></script>
	<script>
      function initialize() {
	  if(${lat}){
	var lat=${lat};
	var lng=${lng};	
}
	else{
		var lat=-200;
		var lng=-200;	
	}
		var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
		var fsp=getPlaces(lat,lng);
        var mapCanvas = document.getElementById('map-canvas');
        var mapOptions = {
          center: new google.maps.LatLng(lat,lng),
          zoom: 16,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        var map = new google.maps.Map(mapCanvas, mapOptions)
		var marker = new google.maps.Marker({
			position: new google.maps.LatLng(lat,lng),
		map: map,
		title: 'Concert location'
	});
		for (var i=0; i<fsp.places.length;i++) {
			var latn=fsp.places[i].lat;
			var lngn=fsp.places[i].lng;
			marker = new google.maps.Marker({
			position: new google.maps.LatLng(latn,lngn),
		map: map,
		icon: iconBase + 'info-i_maps.png',
		title: fsp.places[i].name
	});
		}
      }
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>

<title>Concert - ${name}</title>
</head>

<body>

<script type="text/javascript">
window.fbAsyncInit = function() {
	FB.init({
		appId : '861419780556033',
		cookie : true,

		xfbml : true,
		version : 'v2.2'
	});

	FB.getLoginStatus(function(response) {		
	});

};

(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id))
		return;
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));


</script>

<script>postOnWall('I wanna go to ${name} on ${date} in ${city}')</script>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">ConcertBook</a>
    </div>
    <div>
      <ul class="nav navbar-nav">  
		<li><button class="btn btn-primary" onclick="window.history.back();"><span class="glyphicon glyphicon-circle-arrow-left"></span> Back</button></li>
        <li><a href="start">Home</a></li>
        <li><a href="calendar?auth=sessionID">Calendar</a></li>
        <li><a href="singer?auth=sessionID">Artists</a></li> 
      </ul>
	  <ul class="nav navbar-nav navbar-right">
		<li>You are signed in as <b>${email}</b></li>
        <li><button class="btn btn-info" onclick="logout()">Logout</button></li>
      </ul>
    </div>
  </div>
</nav>
<style>
body {
    background-color: #253E66;
}
</style>
<div class="container">
	<div class="jumbotron">
	<div class="row">
	<c:choose>
		<c:when test="${lat==0.0}">
		<div class="col-sm-4"><h3>No map available</h3></div>
		</c:when>
		<c:otherwise>
		<div class="col-sm-4" id="map-canvas"></div>
		</c:otherwise>
	</c:choose>
		
		<div class="col-sm-4">
			<br/><h4>${name}</h4>
			<br/><h4>Date: ${date}</h4>
			<br/><h4>Location: ${adress} ${city} </h4>
		</div>
	</div>		
	</div>
</div>


<div>
</div>

</body>
</html>