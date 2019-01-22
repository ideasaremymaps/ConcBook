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
<link rel='stylesheet' href='css/fullcalendar.css' />
<script
 src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"
 type="text/javascript"></script>
<script src="http://connect.facebook.net/en_US/all.js"></script>
<script src="js/facebookApi.js"></script>
<script src='js/moment.min.js'></script>
<script src='js/fullcalendar.js'></script>
<script type='text/javascript'>


$(document).ready(function() {
    $('#calendar').fullCalendar({
	 aspectRatio: 2,
	 header: {
		left:'prev,next today',
		center: 'title',
		right: 'month, agendaWeek, agendaDay'
	 },
	 events: [
	 <c:forEach var="s" items="${concerts}"> 
	    {
            title  : '${s.singerId} in ${s.city}',
            start  : '${s.date}',
			url : "place?name=${s.name}&lat=${s.latitude}&lng=${s.longitude}&adress=${s.adress}&auth=${auth}&date=${s.date}"
        },
	
	</c:forEach>
	],
	eventClick: function(calEvent, jsEvent, view) {
		}
});
});
</script>
<title>Calandar</title>
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
	  <li><button id="back" class="btn btn-primary" onclick="window.history.back();"><span class="glyphicon glyphicon-circle-arrow-left"></span> Back</button></li>
        <li><a href="start">Home</a></li>
        <li class="active"><a href="#">Calendar</a></li>
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
    background-color: #F3FAB6;
}
</style>
<script>
if(document.referrer=="http://localhost:8080/ConcertBook2015/start") $(back).prop('disabled', true);
</script>

<div id='calendar'></div>
<script>$('#calendar').fullCalendar('option', 'aspectRatio', 0.4);
</script>

</body>
</html>