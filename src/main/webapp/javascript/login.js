/**
	JavaScript for the log in
*/

function noSignIn() {
	window.location.href = "http://localhost:8080/FeedMe/search"
}

function validate() {
	
	var username = document.getElementById("usernameInput").value;
	var password = document.getElementById("passwordInput").value;
	
	$.ajax({
    	type: "POST",
    	url: "../ValidateLogin",
    	async: true,
    	data: {
			username: username,
			pass: password
    	},
    	success: function(result) {
    		if (result.success == "true") {
    			onLogIn(result.data.username)
    		}
    		else {
    			document.getElementById("errorField").style.display = "inherit"
    			document.getElementById("errorField").innerHTML = result.data.errorMsg
    		}
    	},
    })
	
}

function addUser() {
	
	var username = document.getElementById("usernameInput").value;
	var password = document.getElementById("passwordInput").value;
	
	$.ajax({
    	type: "POST",
    	url: "../AddUser",
    	async: true,
    	data: {
			username: username,
			pass: password
    	},
    	success: function(result) {
    		if (result.success == "true") {
    			onLogIn(result.data.username)
    		}
    		else {
    			document.getElementById("errorField").style.display = "inheirt"
    			document.getElementById("errorField").innerHTML = result.data.errorMsg
    		}
    	},
    })
	
}

function onLogIn(username) {
	//set up any javascript we need for loading the new user
	
	console.log("Logged in!")
	
	
	sessionStorage.setItem("loggedIn", true);
	sessionStorage.setItem("username", username);
	
	$.ajax({
    	type: "POST",
    	url: "../GetSearchHistory",
    	async: true,
    	data: {
			username: username
    	},
    	success: function(result) {
	    		console.log(result)
	    		
	    		if (result.length === 0) {
	    			sessionStorage.setItem("numSearchHistory", 0);
	    			console.log("TESTING NO SEARCHES")
	    		} else {
	    			
	    			sessionStorage.setItem("numSearchHistory", result.length);
	    			console.log("FOUND PREVIOUS SEARCHES")
	    			
	    			for (var i = 0; i < result.length; i++) {
	    			sessionStorage.setItem("searchQuery" + i, result[i].searchQuery);
	    			sessionStorage.setItem("radius" + i, result[i].radius);
	    			sessionStorage.setItem("numResults" + i, result[i].numResults);
	    		}
    			
    		}
    		
    		
    	},
    })
	
    window.location.replace("http://localhost:8080/FeedMe/search")
	
	//call servlets to load their lists and data?
	
	
	   
	
}

function loadSearchHistory() {
	
	
	
	$.ajax({
    	type: "POST",
    	url: "/FeedMe/GetSearchHistory",
    	async: true,
    	data: {
			username: sessionStorage.getItem("username")
    	},
    	success: function(result) {
    		console.log(result)
    		
    		if (result.length === 0) {
    			sessionStorage.setItem("numSearchHistory", 0);
    			console.log("TESTING NO SEARCHES")
    		} else {
    			
    			sessionStorage.setItem("numSearchHistory", result.length);
    			console.log("FOUND PREVIOUS SEARCHES")
    			
    			for (var i = 0; i < result.length; i++) {
    				sessionStorage.setItem("searchQuery" + i, result[i].searchQuery);
    				sessionStorage.setItem("radius" + i, result[i].radius);
    				sessionStorage.setItem("numResults" + i, result[i].numResults);
    			}
   
    		}
    		
    		var numItemsToLoad = sessionStorage.getItem("numSearchHistory");
    		
    		console.log("Attempting to load " + numItemsToLoad + " into search history")
    		
    		document.getElementById("dropdown-menu-Populate").innerHTML = "";
    		
    		for (var i = 0; i < numItemsToLoad; i++) {
    			
    			var radius = sessionStorage.getItem("radius" + i);
    			var query = sessionStorage.getItem("searchQuery" + i);
    			var numResults = sessionStorage.getItem("numResults" + i);
    			
    			var link = "http://localhost:8080/FeedMe/results?q=" + query + "&n=" + numResults + "&radiusInput=" + radius + "&pageNumber=1";
    					
    			
    			var newData = "<a href=\"" + link + "\" class=\"dropdown-item quickAccessLink\" id=\"quickAccessResult" + i + "\"><div class=\"top_past_history\"> <p>" + query + "</p> <p>" + numResults + "</p> </div><p>" + radius + "</p></a>"
    			
    			document.getElementById("dropdown-menu-Populate").innerHTML = document.getElementById("dropdown-menu-Populate").innerHTML + newData;
    		}
    		
    		
    	}
    })
	
	//load the following structure in for each item from "GetSerachHistoryServlet.java"
	
	
	
	
}

function addToSearchHistory() {
	
	var searchQuery = document.getElementById("queryInput").value;
	var numResults = document.getElementById("numResultsInput").value;
	var radius = document.getElementById("radiusInput").value;
	
	console.log("Query" + searchQuery)
	console.log("NumResults" + numResults)
	console.log("Radius" + radius)
	
	$.ajax({
    	type: "POST",
    	url: "/FeedMe/AddSearchHistory",
    	async: true,
    	data: {
			username: sessionStorage.getItem("username"),
			searchQuery: searchQuery,
			numResults: numResults,
			radius: radius
    	},
    	success: function(result) {
	  
    	},
    })
    
}

