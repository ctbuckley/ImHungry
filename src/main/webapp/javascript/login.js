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
    			window.location.href = "http://localhost:8080/FeedMe/search"
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
    			window.location.href = "http://localhost:8080/FeedMe/search"
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
	
	
	//set up sessionStorage for the user?
	
	
	//call servlets to load their lists and data?
}