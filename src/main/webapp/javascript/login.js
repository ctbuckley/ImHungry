/**
	JavaScript for the log in
*/

function noSignIn() {
	window.location.href = "http://localhost:8080/FeedMe/search"
}

function validate() {
	var username = document.getElementById("usernameInput").value;
	var password = document.getElementById("passwordInput").value;
	if(username != "" || password !="") {
		//servlet stuff
	} else {
		document.getElementById("errorMessage").innerHTML = "Username and Password Fields Cannot Be Empty";
		document.getElementById("errorMessage").style.visibility = "visible";
	}
}