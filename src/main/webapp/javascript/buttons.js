/**
 * Javascript for the buttons
 */
var currPage = window.location.href;

function returnToSearch(){
	window.location.href = "http://localhost:8080/FeedMe/";
}

function returnToResults(){
	window.location.href = "http://localhost:8080/FeedMe/results"
}

function addToList(){
	
}

function hideButtons(){
	document.getElementById('printButton').style.display = "none";
	document.getElementById('backToResults').style.display = "none";
	document.getElementById('dropDownBar').style.display = "none";
	document.getElementById('navbarNavDropdown').style.display = "none";	
	document.getElementById('addToList').style.display = "none";	
	document.getElementById('navBar').style.display = "none";	
	document.getElementById('buttonsCont').style.visibility = "hidden";
}