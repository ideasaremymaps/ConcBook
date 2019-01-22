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
<script
 src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"
 type="text/javascript"></script>
   <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="http://connect.facebook.net/en_US/all.js"></script>
<script src="js/facebookApi.js"></script>
<script src="https://maps.googleapis.com/maps/api/js"></script>

<script>
      function initialize() {
	  var lat;
	  var lng;
	  if(${concerts[0].latitude!=0}){
	  
	<c:forEach var="s" items="${concerts}">
	if(${s.latitude}!=0.0) {
	lat=${s.latitude};
	lng=${s.longitude};	

}
</c:forEach>
}

	else{
		lat=-200;
		lng=-200;	
	}
        var mapCanvas = document.getElementById('map-canvas');
        var mapOptions = {
          center: new google.maps.LatLng(30,-10),
          zoom: 2,
		  minZoom: 2,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        var map = new google.maps.Map(mapCanvas, mapOptions)
		<c:forEach var="s" items="${concerts}">
		if(${s.latitude}!=0.0) {
		var marker = new google.maps.Marker({
			position: new google.maps.LatLng(${s.latitude},${s.longitude}),
		map: map,
		url: "place?name=${s.name}&lat=${s.latitude}&lng=${s.longitude}&adress=${s.adress}&auth=${auth}&date=${s.date}",
		title: '${s.city}, ${s.date}'
	});
	}
		</c:forEach>
      }
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>

<title>Concerts - ${singer}</title>
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
#map-canvas {
        width: 600px;
        height: 400px;
      }
</style>

<div class="container">
<c:choose>
<c:when test="${exists=='no'}">
<div class="container" style="background-color:#FFFF99"><h3>No  ${singer} concerts at this time.</h3></div>
</c:when>
<c:otherwise>
<div class="container" style="background-color:#FFFF99"><h3>Concerts by ${singer}</h3></div>
</c:otherwise>
</c:choose>
<div class="container" style="background-color:#E3DFD7">
<div id="map-canvas"></div>
<br/>
<c:forEach var="s" items="${concerts}">
	<table class="table">
	<div class="container" style="background-color:#E3DFD7">
		<tr>
			<td><b>City:</b></td><td>${s.city}, ${s.date}</td>
		</tr>
		<tr>
			<td><b>Event:</b></td><td>${s.name}</td> 
		</tr>
		<br/>
		<tr>
			<td/>
			<td><a href="place?name=${s.name}&lat=${s.latitude}&lng=${s.longitude}&adress=${s.adress}&auth=${auth}&date=${s.date}">Show details and location</a>	
			</td>
		</tr>	
		</div>
	</table>
	<hr/>
</c:forEach>
</div>


</body>
</html>