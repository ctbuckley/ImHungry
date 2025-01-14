<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">    
    <meta charset="ISO-8859-1">
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Homebrew CSS  -->
    <link href="/FeedMe/css/details.css" rel="stylesheet" >
    <link href="/FeedMe/css/navbar.css" rel="stylesheet" type="text/css">
    <link href="/FeedMe/css/recipe_details.css" rel="stylesheet">
    <script src="/FeedMe/javascript/buttons.js"></script>
    <%@page import="java.util.*" %> 
	<%@page import="data.*"%>
    <% 
    // To check if we came from results page or list page
    String resultsOrList = (String) request.getSession().getAttribute("resultsOrList");
	Restaurant restaurantVal = (Restaurant) request.getSession().getAttribute("restaurantVal");
    
	Database db = new Database();
	int itemID = db.insertRestaurant(restaurantVal);
	
	int arrNum = (Integer)request.getSession().getAttribute("arrNum");
    // Check to see what the previous page was
	if(resultsOrList.equals("list")){
		// Get data from session
		ArrayList<Restaurant> rest = (ArrayList<Restaurant>) request.getSession().getAttribute("restaurants");
		System.out.println(rest.size());
		// Put restaurant item into local variable
		restaurantVal = rest.get(arrNum);
	}
	
	request.getSession().setAttribute("itemID", itemID);
    %>
    
    
    <!-- Title -->
    <title>Restaurant Details</title>
  </head>

<body>
  	<nav class="navbar navbar-expand-lg navbar-light bg-light" id="navBar">
		<a class="navbar-brand" onclick="goToSearchPage()">ImHungry</a>
	 	<button class="navbar-toggler" id="navToggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
	   		<span class="navbar-toggler-icon"></span>
	 	</button>
	 	<div class="collapse navbar-collapse ml-auto" id="navbarNavDropdown">
	   		<ul class="navbar-nav ml-auto">
	   			<li class="nav-item active ml-auto">
					<a class="nav-link" id="grocery_link_button" href="http://localhost:8080/FeedMe/DisplayGroceryList">
						<svg class="grocry_cart" xmlns="https://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
							<path id="grocery_icon" d="M7 18c-1.1 0-1.99.9-1.99 2S5.9 22 7 22s2-.9 2-2-.9-2-2-2zM1 2v2h2l3.6 7.59-1.35 2.45c-.16.28-.25.61-.25.96 0 1.1.9 2 2 2h12v-2H7.42c-.14 0-.25-.11-.25-.25l.03-.12.9-1.63h7.45c.75 0 1.41-.41 1.75-1.03l3.58-6.49c.08-.14.12-.31.12-.48 0-.55-.45-1-1-1H5.21l-.94-2H1zm16 16c-1.1 0-1.99.9-1.99 2s.89 2 1.99 2 2-.9 2-2-.9-2-2-2z"/>
							<path d="M0 0h24v24H0z" fill="none"/>
						</svg>
						Grocery List
					</a>
				</li>
	     		<li class="nav-item active ml-auto">
	       			<a class="nav-link" id="userButton" href="http://localhost:8080/FeedMe/jsp/login.jsp">Log Out</a>
	     		</li>
	     	</ul>
	 	</div>
	</nav>
    <!-- Row -->
    <div class="restaurant-container">
	    <div class="row">
		    <div class="col-sm-8">
		       <!-- Title -->
		       <h1 id="restaurantName"><%= restaurantVal.getName() %></h1>
		       <!-- Holds image, prep and cook time of recipe-->
		       <div id="details">
		         <a id="address" href="<%= "https://www.google.com/maps/dir/?api=1&origin=Tommy+Trojan%2C+Childs+Way%2CLos+Angeles+CA&origin_place_id=ChIJIfdecuPHwoARKagsKQF16io&destination=" + restaurantVal.getAddress()%>"><strong>Address:</strong> <%= restaurantVal.getAddress() %></a>
		         <p id="phoneNumber"><strong>Phone Number:</strong><%= restaurantVal.getPhoneNumber() %></p>
		         <a id="website" href="<%= restaurantVal.getWebsiteUrl() %>"><strong>Website Address: </strong><%= restaurantVal.getWebsiteUrl() %></a>
		       </div>
		    </div>
		    <!-- Holds all the buttons -->
		    <div class="buttons col-sm">
		      <!-- Brings user to a printable version of the page -->
		      
		      <button id="printButton" class="btn btn-outline-primary" onclick="hideButtons()">Printable Version</button>
		      <!-- Brings user back to results page -->
		      
	          <!-- Brings user back to results page -->
		      <form action="/FeedMe/results" method="POST">
		        <button id="backToResults" class="btn btn-outline-primary">Back To Results</button>
		      </form>
		      <!-- This is the drop-down menu -->
		      <form method="POST" onsubmit="return addToList(this)">
		      <input type="hidden" name="arrNum" value="<%= arrNum %>">
		      <select name="listType" id="dropDownBar" class="dropDownBar">
	          	<option disabled selected value id="defaultOption"> -- select an option -- </option>
	          	<option id="favoriteOption" value="f" >Favorites</option>
	          	<option id="toExploreOption" value="t">To Explore</option>
	          	<option id="doNotShowOption" value="d">Do Not Show</option>
	      	  </select>
		      <!-- Button to add item to selected list, doesn't do anything if choice is empty -->
		      <button type="submit" id="addToList" class="btn btn-outline-primary">Add to List</button>
		      </form>
		    </div>
		 </div>
	</div>
    <!-- Homebrew JS -->
    <script>
    // Adds the item to the specified list, if the proper one is selected
    function addToList(form){
    	var userInput = document.getElementById('listType').value;
    	console.log(userInput);
    	if (userInput == null || userInput.length == 0){
    		return false;	
    	}
    	else{
    		form.action = "/FeedMe/restaurantDetails";
    	}
    	
    	function goToSearchPage() {
			window.location.href = "http://localhost:8080/FeedMe/jsp/search.jsp";
		}
    }
    </script>
    <!-- Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  </body> 
  <!-- Homebrew CSS  -->
  <style>
    <%@ include file="/css/buttons.css"%>
    <%@ include file="/css/details.css"%>
  </style>
</html>