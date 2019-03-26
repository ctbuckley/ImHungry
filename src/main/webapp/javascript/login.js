/**
	JavaScript for the log in
*/

function noSignIn() {
	window.location.href = "http://localhost:8080/FeedMe/search"
}

function validate() {
	var username = document.getElementById("usernameInput").value;
	console.log(username);
	var password = document.getElementById("passwordInput").value;
	console.log(password);
	if(username != "" && password != "") {
		//servlet stuff
	} else {
		document.getElementById("errorField").innerHTML = "Username and Password Fields Cannot Be Empty";
		document.getElementById("errorField").style.visibility = "visible";
	}
}