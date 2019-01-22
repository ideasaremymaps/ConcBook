
//INICIJALIZACIJA LOGIRANJE ODLOGIRAVANJE I POST,FACEBOOK POSLA

//pokreće se pri prvom loginu.PROVJERAVA DALI SI SPOJEN NA FACEBOOK
function statusChangeCallback(response) {

	if (response.status === 'connected') {

		// šalješ ime,prezime,mail i auth u  auth servlet i sprema u sesiju i 
		//onda šalje facebook podatke u save servlet i dovlači iz lastfm podatke i sprema u mongo
		
		//getFacebookUserData->sendAuth(ajax šalje u auth servlet)->getFacebookSinger->sendFacebookDataAjax->setAuthRef
		
		getFacebookUserData("sessionID");
				

	} else if (response.status === 'not_authorized') {
		document.getElementById('logout').style.visibility = 'hidden';
		document.getElementById('login').style.visibility = 'visible';
	} else {
		document.getElementById('logout').style.visibility = 'hidden';
		document.getElementById('login').style.visibility = 'visible';
	}
}

//pokreće se u normalnih okolnostima.PROVJERAVA DALI SI SPOJEN NA FACEBOOK
function statusChangeCallbackNormal(response) {

	if (response.status === 'connected') {
		document.getElementById('logout').style.visibility = 'visible';
		document.getElementById('login').style.visibility = 'hidden';
		//response.authResponse.accessToken
		
		//postavlja sessiju u link na kalendar i singere
		setAuthRef("sessionID");
		

	} else if (response.status === 'not_authorized') {
		document.getElementById('logout').style.visibility = 'hidden';
		document.getElementById('login').style.visibility = 'visible';
	} else {
		document.getElementById('logout').style.visibility = 'hidden';
		document.getElementById('login').style.visibility = 'visible';
	}
}



function checkLoginState() {
	FB.getLoginStatus(function(response) {
		statusChangeCallback(response);
	});
}

//OVO SE POKREĆE U BODY U start.jsp i to onda se spoji na facebook
window.fbAsyncInit = function() {
	FB.init({
		appId : '861419780556033',
		cookie : true,

		xfbml : true,
		version : 'v2.2'
	});

	FB.getLoginStatus(function(response) {
		statusChangeCallbackNormal(response);
	});

};
//OVO SE POKREĆE U BODY U start.jsp i to onda se spoji na facebook
(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id))
		return;
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function logout() {
	FB.logout(function(response) {
		deleteMongoDataAjax();
		location.href = "http://localhost:8080/ConcertBook2015/start";

	});
}

//NIJE ISPROBANA
function postOnWall(data) {
	FB.login(function() {
		FB.api('/me/feed', 'post', {
			message : data
		});
	}, {
		scope : 'publish_actions'
	});
}
// INICIJALIZACIJA LOGIRANJE ODLOGIRAVANJE I POST

// UREĐIVANJE SADRŽAJA NA POČETKU IZRADE APLIKACIJE-NE UPOTREBLJAVAJU SE 
//IZVAN UPOTREBE
function listMusic(data,auth) {
	//var json=JSON.parse(data);
	
	var music = new Array();

	for (var i = 0; i < json.length; i++) {
		music.push(data[i].name);
	}

	makelist(music,auth);
}

//IZVAN UPOTREBE
function makeList(data){
	var music = new Array();

	for (var i = 0; i < data.length; i++) {
		music.push(data[i].name);
	}
	
	return music;
} 

//IZVAN UPOTREBE
function makelist(listData,auth) {

	// Make a container element for the list - which is a <div>
	// You don't actually need this container to make it work
	var listContainer = document.getElementById('music'); // document.createElement("div");

	// add it to the page
	document.getElementsByTagName("body")[0].appendChild(listContainer);

	// Make the list itself which is a <ul>
	var listElement = document.createElement("ul");

	// add it to the page
	listContainer.appendChild(listElement);

	// Set up a loop that goes through the items in listItems one at a time
	var numberOfListItems = listData.length;

	for (var i = 0; i < numberOfListItems; ++i) {

		// create a <li> for each one.
		var listItem = document.createElement("li");

		var a = document.createElement('a');
		var linkText = document.createTextNode(listData[i]);
		a.appendChild(linkText);
		a.href = "concert?name=" + listData[i] + "&auth="+auth+"";
		// add the item text
		listItem.appendChild(a);

		// add listItem to the listElement
		listElement.appendChild(listItem);

	}

}

//IZVAN UPOTREBE
function listConcerts(data,auth)
{
	var concerts = new Array();

	for (var i = 0; i < data.length; i++) {
		concerts.push(data);
	}

	makeConcertList(concerts,auth);
}

//IZVAN UPOTREBE
function makeConcertList(listData,auth) {

	// Make a container element for the list - which is a <div>
	// You don't actually need this container to make it work
	var listContainer = document.getElementById('music'); // document.createElement("div");

	// add it to the page
	document.getElementsByTagName("body")[0].appendChild(listContainer);

	// Make the list itself which is a <ul>
	var listElement = document.createElement("ul");

	// add it to the page
	listContainer.appendChild(listElement);

	// Set up a loop that goes through the items in listItems one at a time
	var numberOfListItems = listData.length;

	for (var i = 0; i < numberOfListItems; ++i) {

		// create a <li> for each one.
		var listItem = document.createElement("li");

		var a = document.createElement('a');
		var linkText = document.createTextNode(listData[i].name+" "+listData[i].date );
		a.appendChild(linkText);
		a.href = "place?name=" + listData[i].id + "&auth="+auth+
		"&lat="+listData[i].latitude+
		"&lng="+listData[i].longitude+
		"&adress="+listData[i].adress+
		"&date="+listData[i].date+"";
		// add the item text
		listItem.appendChild(a);

		// add listItem to the listElement
		listElement.appendChild(listItem);

	}

}



// UREĐIVANJE SADRŽAJA

//RAD S PODACIMA
function sendAuth(session) {

	if (typeof jQuery !== 'undefined') {
		console.log('jQuery Loaded');
	} else {
		console.log('not loaded yet');
	}
	
	
	$.ajax({
		url : "auth",
		type : 'POST',
		dataType : 'json',
		data : JSON.stringify(session),
		contentType : 'application/json',
		mimeType : 'application/json',
		success : function(data) {
			$(progbar).show();
			$(login).hide();
			getFacebookSinger(session[0]);
			
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + " " + jqXHR.statusText +""+errorThrown + " auth data");
		}

	});

}

function getFacebookUserData(auth){
	
	FB.api('/me', function(response) {	      
			var session=[auth,response.first_name ,response.last_name, response.email ];
	        //response.authResponse.accessToken
			
			sendAuth(session);
	});
	
	
}

function getFacebookSinger(auth) {

	FB.api('/me/music', {
		'limit' : '5000'
	}, function(response) {
		if (response || !response.error) {
			if (response.data.length > 0) {
				// document.getElementById('status').innerHTML
				// =response.data.length;
                var data= makeList(response.data);
				sendFacebookDataAjax(data,auth);
			}
		}
	});

}

function sendFacebookDataAjax(data,response) {
	if (typeof jQuery !== 'undefined') {
		console.log('jQuery Loaded');
	} else {
		console.log('not loaded yet');
	}
	// postavi poslje ime i prezime korisnika
	$.ajax({
		url : "save",
		type : 'POST',
		dataType : 'json',
		data : JSON.stringify(data),
		contentType : 'application/json',
		mimeType : 'application/json',
		success : function(data) {

			// location.href = "http://localhost:8080/ConcertBook2015/start"
			$(progbar).hide();
			document.getElementById('logout').style.visibility = 'visible';
		document.getElementById('login').style.visibility = 'hidden';
			setAuthRef(response);
			
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + " " + jqXHR.statusText +""+errorThrown +" facebook data");
		}

	});
}

function deleteMongoDataAjax() {
	if (typeof jQuery !== 'undefined') {
		console.log('jQuery Loaded');
	} else {
		console.log('not loaded yet');
	}
	// postavi poslje ime i prezime korisnika
	$.ajax({
		url : "delete",
		type : 'POST',
		dataType : 'json',
		data : JSON.stringify("delete"),
		contentType : 'application/json',
		mimeType : 'application/json',
		success : function(data) {

			// location.href = "http://localhost:8080/ConcertBook2015/start"
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR.status + " " + jqXHR.statusText + " ne");
		}

	});
}

function setAuthRef(response){
	var calendar = document.getElementById('calendar');
	var singer = document.getElementById('singer');
	
	var linkTextCalendar = document.createTextNode("Calendar of events");
	var linkTextSinger = document.createTextNode("List of artists");
	
	
	calendar.appendChild(linkTextCalendar);
	calendar.href = "calendar?auth="+response+"";
	
	singer.appendChild(linkTextSinger);
	singer.href = "singer?auth="+response+"";
}


function isEmpty(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop))
            return false;
    }

    return true;
}

//RAD S PODACIMA





//RAD S FOURSQARE


function getPlaces(latitude,longitude){
	
	if(latitude>-190){
		var placeData={}
		   var urls="https://api.foursquare.com/v2/venues/search?ll="+latitude+"%2C"+longitude+"&client_id=HZCRZ5KKEQYK2PP2KGH0A1DLWAQDN3MH1LXISJBPICVH4LA5&client_secret=YPGMILZKZNVCTT0R5BOQPYHBM11A5O5O2J25BTLMV0IMVS2B&v=20120609";
		   if(typeof jQuery!=='undefined'){
		   console.log('jQuery Loaded');
		   }
		   else{
		   console.log('not loaded yet');
		   }
		   placeData=getJSON(urls);
		   placeData=editLocation(placeData); 
		
	}
		
	   //izmjeni json 
	   return placeData;
	
}


function editLocation(jsonData)
{
    var p=jsonData.response.venues;
    var loc=new Object();
	loc.places=new Array();
      	
	for(var i=0;i<p.length;i++)
	{  var data=new Object();
	   data.id=p[i].id;
	   data.name=p[i].name;
	   data.lat=p[i].location.lat;
	   data.lng=p[i].location.lng;
	   data.country=p[i].location.country;
	   data.city=p[i].location.city;
	   loc.places.push(data);
    }	   
	
	return loc;
}

function getJSON(url) {
    var resp ;
    var xmlHttp ;

    resp  = '' ;
    xmlHttp = new XMLHttpRequest();

    if(xmlHttp != null)
    {
        xmlHttp.open( "GET", url, false );
        xmlHttp.send( null );
        resp = xmlHttp.responseText;
    }
    var json=JSON.parse(resp); 
    return json ;
}
//RAD S FOURSQARE











