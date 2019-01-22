<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html> 
<html>
<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script
 src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"
 type="text/javascript"></script>
<script src="http://connect.facebook.net/en_US/all.js"></script>
<script src="https://maps.googleapis.com/maps/api/js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<title>ConcertBook</title>
</head>
<body>
<script src="js/facebookApi.js"></script>

<style>
body {
    background-color: #253E66;
}
</style>

	<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">ConcertBook</a>
    </div>
    <div>
	  <ul id="progbar" class="nav navbar-nav navbar-right">
        <li id="logout"><button class="btn btn-info" onclick="logout()">Logout</button></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container">
	<div class= "jumbotron">
		<div class="row">
			<div class="col-sm-7">
			<image src="img/logo-large.jpg" class="img-rounded" alt="logo"/>
			<br/>
			
			</div>
			
			<div class="col-sm-4">
			
			<div id="progbar" style="display:none;" class="progress">
			<div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar"
			aria-valuenow="100" aria-valuemin="0" aria-valuemax="50" style="width:100%"> Loading data
			</div>
			</div>
			
			<div id="login">
			<fb:login-button scope="public_profile,email,user_likes" onlogin="checkLoginState();">
			</fb:login-button>
			</div>
			<div id="fb-root"></div>
		
			<div>
			<a id="calendar" href="calendar" ><h2></h2></a>
			<a id="singer" href="singer" ><h3></h3></a>
			</div>
			<div>
		</div>
	</div>
</div>


</body>
</html>