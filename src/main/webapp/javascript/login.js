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
    			document.getElementById("errorField").style.display = "inherit"
    			document.getElementById("errorField").innerHTML = result.data.errorMsg
    		}
    	},
    })
	
}

function onLogIn(username) {
	//set up any javascript we need for loading the new user
	
	
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
	
	if (sessionStorage.getItem("loggedIn")) {
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
	    		
	    		document.getElementById("dropdown-menu-populate").innerHTML = "";
	    		
	    		var imgLinks = [
	    			"https://images.pexels.com/photos/248797/pexels-photo-248797.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
	    			"https://images.unsplash.com/photo-1535498730771-e735b998cd64?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80",
	    			"https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
	    			"https://images.unsplash.com/photo-1500382017468-9049fed747ef?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80",
	    			"https://cdn.pixabay.com/photo/2018/02/09/21/46/rose-3142529__340.jpg",
	    			"https://i.kinja-img.com/gawker-media/image/upload/s--PnSCSSFQ--/c_scale,f_auto,fl_progressive,pg_1,q_80,w_800/z7jcryloxjedsztssw39.jpg",
	    			"https://cdn.pixabay.com/photo/2018/10/30/16/06/water-lily-3784022__340.jpg",
	    			"https://www.w3schools.com/howto/img_forest.jpg",
	    			"https://www.w3schools.com/w3css/img_lights.jpg",
	    			"https://media.wired.com/photos/5c1ae77ae91b067f6d57dec0/master/pass/Comparison-City-MAIN-ART.jpg",	    			
	    		]
	    		
	    		for (var i = 0; i < numItemsToLoad; i++) {
	    			
	    			var radius = sessionStorage.getItem("radius" + i);
	    			var query = sessionStorage.getItem("searchQuery" + i);
	    			var numResults = sessionStorage.getItem("numResults" + i);
	    			
	    			var link = "http://localhost:8080/FeedMe/results?q=" + query + "&n=" + numResults + "&radiusInput=" + radius + "&pageNumber=1";
	    					
	    			var newHTML = "<div class=\"search_cont\" id=\"quickAccessResult" + i + "\" + onclick=\"window.location.href=\'" + link + "\'\"> " +
										"<div class=\"pastSearchGallery\">";
	    			for(var j = 0; j <imgLinks.length; j++ ) {
	    				newHTML = newHTML + "<figure class=\"gallery_item gallery_item_" + j + "\">" +
										"<img src=\"" + imgLinks[j] + "\" class=\"gallery_img\" alt=\"\"/>" +
								  "</figure>";
	    			}
						
	    			newHTML = newHTML + "</div>" + 
								"<div class=\"search_params_cont\">" + 
									"<div class=\"search_term\">" +
										"<p class=\"subtitle\">Search Term: </p>" +
										"<p>" + query + "</p>" + 
									"</div>" + 
									"<div class=\"search_others\">" +
										"<div class=\"search_info\">" +
											"<p class=\"subtitle\">Number of Results: </p>" +
											"<p>" + numResults + "</p>" +
										"</div>" + 
										"<div class=\"search_info\">" + 
											"<p class=\"subtitle\">Radius: </p>" + 
											"<p>" + radius + "</p>" +
										"</div>" +
									"</div>" + 
								"</div>" +
							"</div>";
	    				    			
	    			document.getElementById("dropdown-menu-populate").innerHTML = document.getElementById("dropdown-menu-populate").innerHTML + newHTML;
	    		}
	    		
	    		
	    	}
	    })
		
	}
	
}

function addToSearchHistory() {
	
	if (sessionStorage.getItem("loggedIn")) {
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
    
}

