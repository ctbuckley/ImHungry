<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">    
    <meta charset="ISO-8859-1">
    <!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<!-- Bootstrap JS file linkage -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
   
	<link href='http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.min.css' rel='stylesheet' type='text/css'>

    <!-- CSS -->
    <link href="/FeedMe/css/listManagement.css" rel="stylesheet" type="text/css">
    <link href="/FeedMe/css/navbar.css" rel="stylesheet" type="text/css">
    <!-- Import java data structures -->
	<%@page import="java.util.*" %>
	<%@page import="data.*"%>
  	<% 
  	// Set the session attribute to indicate that this page was last seen
    request.getSession().setAttribute("resultsOrList", "list");
  	// Name of the list
    String listName = "";
  	// List name value pull from servlet
    String listID = (String) request.getAttribute("listName");
    // If value doesn't exist, we shouldn't be here on this page
    if(listID == null){
    	listName = "error";
    }
    else{
    	listName = listID;
    }
	// Get the List that is needed from servlet
    UserList lists = (UserList) request.getAttribute("listVal");
    ArrayList<Restaurant> restaurantArr = null;
    ArrayList<Recipe> recipeArr = null;
    // Check if the list exists
    if(lists != null){ // If it does, get the two different lists from it
    	restaurantArr = lists.getRestaurants();
        recipeArr = lists.getRecipes();
    }
    
  %>

    <!-- Title -->
    <title>List Management</title>
  </head>
  <body onload="setStars();">
  	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" onclick="goToSearchPage()">ImHungry</a>
	 	<button class="navbar-toggler" id="navToggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
	   		<span class="navbar-toggler-icon"></span>
	 	</button>
	 	<div class="collapse navbar-collapse ml-auto" id="navbarNavDropdown">
	   		<ul class="navbar-nav ml-auto">
	   			<li class="nav-item active ml-auto">
					<a class="nav-link" id="grocery_link_button" href="http://localhost:8080/FeedMe/jsp/groceryList.jsp">
						<svg class="grocry_cart" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
							<path id="grocery_icon" d="M7 18c-1.1 0-1.99.9-1.99 2S5.9 22 7 22s2-.9 2-2-.9-2-2-2zM1 2v2h2l3.6 7.59-1.35 2.45c-.16.28-.25.61-.25.96 0 1.1.9 2 2 2h12v-2H7.42c-.14 0-.25-.11-.25-.25l.03-.12.9-1.63h7.45c.75 0 1.41-.41 1.75-1.03l3.58-6.49c.08-.14.12-.31.12-.48 0-.55-.45-1-1-1H5.21l-.94-2H1zm16 16c-1.1 0-1.99.9-1.99 2s.89 2 1.99 2 2-.9 2-2-.9-2-2-2z"/>
							<path d="M0 0h24v24H0z" fill="none"/>
						</svg>
						Grocery List
					</a>
				</li>
	   			<li class="nav-item dropdown ml-auto" id="listName">
		        	<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          		Manage Lists
		        	</a>
		        	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
			      		<a class="dropdown-item" id="fOptionButton" href="/FeedMe/listManagement?listName=f">Favorites</a>
			      		<a class="dropdown-item" id="tOptionButton" href="/FeedMe/listManagement?listName=t">To Explore</a>
			      		<a class="dropdown-item" id="dOptionButton" href="/FeedMe/listManagement?listName=d">Do Not Show</a>     
		        	</div>
		    	</li>
	     		<li class="nav-item active ml-auto">
	       			<a class="nav-link" id="userButton" href="http://localhost:8080/FeedMe/jsp/login.jsp">Log Out</a>
	     		</li>
	     	</ul>
	 	</div>
	</nav>
    <div id="main">
    	<div class=" resultList">
      	<!-- Restaurants and Recipes lists  -->
      		<h1 class="pageTitle col-12"><%=listName %> List</h1>
      		<% // Used to alternate colors
   			int j = 0;
          	while(j < restaurantArr.size()){
					System.out.println("Rest Arr: " + j + " " + (restaurantArr.get(j) == null));
					String restaurantPrice = "";
					int price = (int) restaurantArr.get(j).getPrice();
					if (price == 1) {
						restaurantPrice = "$";
					} else if (price == 2) {
						restaurantPrice = "$$";
					} else if (price == 3){
						restaurantPrice = "$$$";
					} else if (price == 4){
						restaurantPrice = "$$$$";
					}
          	%>
          	<!-- This is the restaurant div -->
          	<div class="col-12" id="Restaurant<%=j%>">
         			<div class="row no-gutters border rounded overflow-hidden flex-md-row mb-8 shadow-sm h-md-250 position-relative results-card mb-3"
         			onclick="window.location='/FeedMe/restaurantDetails?arrNum=<%=j%>'">
        			<div class="col p-4 position-static">
          			<div class="container">
  						<div class="row">
							<div class="col-10">
								<h4 id="restaurantName<%=j%>"><strong><%=restaurantArr.get(j).getName()%></strong></h4>
							</div>
							<div class="col-2 text-right">
								<h4 id="restaurantPrice<%=j%>"><%=restaurantPrice%></h4>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<div class="ratings">
									<div id="restaurantRating<%=j%>" class="outer_stars"
										data-stars="<%=restaurantArr.get(j).getRating()%>">
										<div class="inner_stars" id="innerRestaurantRating<%=j%>"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-6">
								<p id="restaurantAddress<%=j%>"><%=restaurantArr.get(j).getAddress()%></p>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<p id="restaurantDistance<%=j%>" class="minutes-away"><%=restaurantArr.get(j).getDrivingTime()%> Min(s) Away</p>
							</div>
						</div>	
					</div>

          			<%-- <a href="/FeedMe/restaurantDetails?arrNum=<%=j%>" class="stretched-link"></a> --%>
        			</div>
      				</div>
      				<!-- This form takes the item to the specified list page -->
      				<form style="display:flex;flex-direction:column;justify-content:center;" class="mb-3" method="POST" action="/FeedMe/listManagement">
             			<input type="hidden" name="listName" value="<%=listName.toLowerCase().charAt(0)%>">
	 	            	<input type="hidden" name="fromList" value="<%=listName.toLowerCase().charAt(0)%>">
    	            	<input type="hidden" name="recOrRest" value="rest">
        	        	<input type="hidden" name="arrNum" value="<%=j%>">
            	    	<% request.setAttribute("item", restaurantArr.get(j)); %>
                		<select id="moveDropDown" class="form-control" name="opType">
	                		<option value="f">Favorites</option>
    	            		<option value="t">To Explore</option>
        	        		<option value="d">Do Not Show</option>
            	    		<option value="r">Trash</option>
                		</select>
	                	<button id="moveButton" class="form-control" type="submit">Move</button>
					</form>
    	</div>
         <% j++;} %>

    		<!-- Recipes -->
    		<%
    		// Use the number of items in the restauarant array to coordinate the background color for the recipes
   			int k = 0;
          	while(k < recipeArr.size()){
          	%>
          	<!-- Where the recipes start -->
    		<div class="col-12" id="Recipe<%=k%>">
         			<div class="row no-gutters border rounded overflow-hidden flex-md-row mb-8 shadow-sm h-md-250 position-relative results-card mb-3"
         			onclick="window.location='/FeedMe/recipeDetails?arrNum=<%=k%>'">
        			<div class="col p-4 d-flex flex-column position-static">
        				<div class="row">
								<div class="col-10">
									<h4 id="recipeName<%=k%>"><strong><%=recipeArr.get(k).getName()%></strong></h4>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<div class="ratings">
										<div id="recipeRating<%=k%>" class="outer_stars"
											data-stars="<%=recipeArr.get(k).getRating()%>">
											<div class="inner_stars" id="innerRecipeRating<%=k%>"></div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-6">
									<%
										// set cook time to not available if no value is given back from API
												int cookTime = (int)recipeArr.get(k).getCookTime();
												String renderCookTime = "";
												if (cookTime < 0) {
													renderCookTime = "Not Available";
												} else {
													renderCookTime = Integer.toString(cookTime);
												}
												// set prep time to not available if no value is given back from API
												int prepTime = (int)recipeArr.get(k).getPrepTime();
												String renderPrepTime = "";
												if (prepTime < 0) {
													renderPrepTime = "Not Available";
												} else {
													renderPrepTime = Integer.toString(prepTime);
												}
									%>
									<p id="recipeCookTime<%=k%>">Cook Time: <%=renderCookTime%> min(s)</p>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<p id="recipePrepTime<%=k%>" class="minutes-away"> Prep Time: <%=renderPrepTime%> min(s)</p>
								</div>
							</div>
						<a href="/FeedMe/recipeDetails?arrNum=<%=k%>"
							class="stretched-link"></a>

					<!-- Link that takes user to recipe details page -->
          			<%-- <a href="/FeedMe/recipeDetails?arrNum=<%=k%>" class="stretched-link"></a> --%>
        			</div>
        			<div class="col-auto d-none d-lg-block">
          			</div>
      				</div>
      				<!-- Moves the recipe to the specified list -->
      				<form style="display:flex;flex-direction:column;justify-content:center;" class="" method="POST" action="/FeedMe/listManagement">
             			<input type="hidden" name="listName" value="<%=listName.toLowerCase().charAt(0)%>">
	 	            	<input type="hidden" name="fromList" value="<%=listName.toLowerCase().charAt(0)%>">
    	            	<input type="hidden" name="recOrRest" value="rec">
        	        	<input type="hidden" name="arrNum" value="<%=k%>">
            	    	<% request.setAttribute("item", recipeArr.get(k)); %>
                		<select id="moveDropDown" class="form-control" name="opType">
	                		<option value="f">Favorites</option>
    	            		<option value="t">To Explore</option>
        	        		<option value="d">Do Not Show</option>
            	    		<option value="r">Trash</option>
                		</select>
	                	<button id="moveButton" class="form-control" type="submit">Move</button>
					</form>
    	</div>
    	
        <%k++; }%>
        </div>
        <!-- Takes the user to the specified list -->
		<div id="buttons" class="buttons align-middle p-1">
		  <!-- Takes user to the search page -->
	      <form action ="/FeedMe/jsp/search.jsp">
	      	<button class="btn btn-outline-primary" id="returnToSearch" onclick="javascript:location.href = this.value;">Return to Search</button>
	      </form>
	      <!-- Takes the user to the results page -->
		  <form action ="/FeedMe/results">
	      	<button class="btn btn-outline-primary" id="backToResults" onclick="javascript:location.href = this.value;">Return to Results</button>
	      </form>
		
		</div>
	</div>

    <!-- Homebrew JS -->
    <script>
	function restaurantRedirect(form){
		form.submit();
	}
	function recipeRedirect(form){
		form.submit();
	}
	// Makes sure that the page does nothing if the default value for which list to manage is still there
	function manageList(form){
		var userInput = document.getElementById('dropDownBar').value;
		if (userInput == null || userInput.length == 0){
			return false;	
		}
		else {
			form.action = "/FeedMe/listManagement";
		}
	} 
	
	function setStars() {
		const starTotal = 5;
		allOuterRatings = document.getElementsByClassName("outer_stars");
		for ( var rating in allOuterRatings) {
			console.log(rating);
			var elementID = allOuterRatings.item(rating).id;
			var val = document.getElementById(elementID).getAttribute(
					"data-stars");
			console.log(elementID);
			console.log(val);
			const starPercentage = val / starTotal * 100;
			const starPercentageRounded = Math.round(starPercentage / 10) * 10;
			console.log(starPercentageRounded);
			document.getElementById(elementID).getElementsByClassName(
					"inner_stars")[0].style.width = starPercentageRounded
					+ "%";
		};
	}
	
	function goToSearchPage() {
		window.location.href = "http://localhost:8080/FeedMe/jsp/search.jsp";
	}
	
</script>
  </body>
  <style>
    <%@ include file="/css/buttons.css"%>
  </style>
</html>