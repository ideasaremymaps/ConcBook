<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String auth = session.getAttribute("auth").toString();
	String json = request.getAttribute("jsonSingers").toString();
%>

<!DOCTYPE html> 
<html>


<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"
	type="text/javascript"></script>
<script src="http://connect.facebook.net/en_US/all.js"></script>
<script src="js/facebookApi.js"></script>
<title>Artists</title>
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
        <li><a href="calendar?auth=sessionID">Calendar</a></li>
        <li class="active"><a href="#">Artists</a></li> 
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

<script>
if(document.referrer=="http://localhost:8080/ConcertBook2015/start") $(back).prop('disabled', true);</script>
	<c:choose>
		<c:when test="${similar==1}">
			<c:forEach var="s" items="${singers}">
			<div class="container" style="background-color:#E3DFD7">
			<table class="table">
			<tr>
				<td><b>Name:</b></td><td>${s.name}</td>
			</tr>
			<tr>
				<td><b>Bio:</b></td><td>${s.bio}</td>
			</tr>
			<tr>
				<td/><td><a href="concert?name=${s.name}&auth=${auth}">Concerts</a>
				 | 
				<a href="https://www.youtube.com/results?search_query=${s.name}">Search Youtube</a>
				</td>	
			</tr>
			</table>
			<hr/>
			</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:forEach var="s" items="${singers}">
			<div class="container" style="background-color:#E3DFD7">
			<table class="table">
			<tr>
				<td><b>Name:</b></td><td>${s.name}</td>
			</tr>
			<tr>
				<td><b>Bio:</b></td><td>${s.bio}</td>
			</tr>
			<tr>
				<td/><td><a href="concert?name=${s.name}&auth=${auth}">Concerts</a>
				 | 
				<a href="similarsinger?name=${s.name}&auth=${auth}">Similar artists</a>
				 | 
				<a href="https://www.youtube.com/results?search_query=${s.name}">Search Youtube</a>
				</td>
			</tr>
			</table>
			<hr/>
			</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>

</body>
</html>