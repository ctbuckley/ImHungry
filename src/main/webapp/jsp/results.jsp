<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<%@page import="java.util.*" %>
	<%@page import="data.*"%>
	<%
	// set the session to declare whether it is on results page or list management page
	request.getSession().setAttribute("resultsOrList", "results");
	// get the google image data from the google custom search api
	String[] imageUrlVec = (String[])request.getAttribute("imageUrlVec");
	// get the search term from the request
	String searchTerm =  (String) request.getAttribute("searchTerm");
	// get the search number from the request 
	Integer resultCount =  (Integer) request.getAttribute("resultCount");
	// get the array of the restaurants from the request 
	Restaurant[] restaurantArr = (Restaurant[]) request.getAttribute("restaurantArr");
	// ge the array of recipes from the request
	Recipe[] recipeArr = (Recipe[]) request.getAttribute("recipeArr");
	
	%>
	  <!-- Bootstrap CSS  -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	  <!-- Homebrew CSS  -->
    <link href="../css/buttons.css" rel="stylesheet" type="text/css">
    <link href="../css/details.css" rel="stylesheet" type="text/css">
    <link href="../css/list.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet"  href="/css/results.css"  type="text/css">
    <style>
    <%@ include file="/css/buttons.css"%>
    </style>
	  <!-- Javascript -->
	<script type="text/javascript" src="../javascript/manageListButton.js"></script>
	 <title><%=searchTerm %></title>
</head>

<!-- Body Html --> 
<body style="background-color:whitesmoke;">
	<div class="container mt-5">
		<!-- Row for collage and buttons -->
		<div class = "row align-items-start">
			<div class="col-sm-2 order-3">
			
				<!--Buttons -->
		 		<div class="buttons">
		 			<form id="listDropDown" name="list" onsubmit="return manageList(this);">
      					<select id="listName" name="listName" class="dropDownBar">
      					<!--Default selection is nothing -->
      					<option id="nOptionButton" disabled selected value> -- select an option -- </option>
       				    <option id="fOptionButton" value ="f">Favorites</option>
        				<option id="tOptionButton" value ="t">To Explore</option>
        				<option id="dOptionButton" value ="d">Do Not Show</option>
      					</select> <br>
      					<!-- Button to add item to selected list, doesn't do anything if choice is empty -->
     					<button id="addToList" class="Button">Manage Lists</button> <br>
      				</form>
      				<!--Click on the button will redirect you to the search page -->
      				<form action ="/FeedMe/jsp/search.jsp">
      					<button id="returntoSearch" onclick="javascript:location.href = this.value;" class="Button">Return to Search</button>
      				</form>

		 		</div>
		 	</div>

		 	<div id="collageDiv" style=" max-width: 60vw; min-width:40vw; max-height: 50vh;text-align: center; min-height: 35vh; border: 2px solid black;" class="col-sm-6 order-2 pt-3 overflow-hidden">
		 	<% 
		 	// creating random angle for each of the image in the collage
		 	for (int i = 0; i < 10; i++) {
		 		Random rand = new Random();
		 		int angle = rand.nextInt(90) -45;
		 	%>
			<img style =" vertical-align: middle; transform: rotate(<%=angle%>deg);" src="<%=imageUrlVec[i] %>" height="100" width="100">
		 	<% } %>
		 	<%--

					<% for (int i = 0; i < imageUrlVec.length; ++i) { %>
					<img src="<%=imageUrlVec[i] %>" height="100" width="100">
					<% } %>
		 	--%>

			</div>
			<div class="col-sm-3 order-1"></div>
		</div>

		<!-- Search For xx  -->
		<div class="py-5 text-center">
   			<h2 id="resultsForText">Results For <%=searchTerm %></h2>
   		</div>

   		<!-- Restaurants lists rendering -->
   		<div class="row md-2">
   			<div class="col-md-6">
      			<h2 id="restaurantsText" class="text-center"> Restaurants</h2>
          		<%
				// rendering the list of restauratns while applying alternating grey color on each of the items
          		for(int i = 0; i < resultCount; i++){
          			String colorStyle = "";
          			if (i%2 == 0){
          				colorStyle = "silver";
          			}
          			else{
          				colorStyle = "grey";
          			}
          		%>
          			<% if(restaurantArr[i] != null){ %>
          			<% System.out.println("Rest Arr: " + i + " " + (restaurantArr[i] == null));  %>
         			<!-- Restaurant item rendering -->
         			<div class="row no-gutters border rounded overflow-hidden flex-md-row md-4 shadow-md h-md-250 position-relative" id="Restaurant<%=i%>">
        			<div style="background-color:<%=colorStyle %>;"class="col p-4 d-flex flex-column position-static">
          			<div class="container">
  						<div class="row">
    					<div class="col-sm">
							<strong id="restaurantName<%=i%>">Name:</strong> <br><p><%=restaurantArr[i].getName() %> </p>
   						</div>

    					<div class="col-sm">
     	 						<strong id="restaurantRating<%=i%>">Stars:</strong> <br> <p> <%=restaurantArr[i].getRating() %> </p>
    					</div>
    					<div class="col-sm">
     	 						
    					</div>
  						</div>
  						<div class="row">
    						<div class="col-sm">

   							</div>

    					<div class="col-sm">

    					</div>
  						</div>
  						<div class="row">
    						<div class="col-sm">
      							<strong id="restaurantDistance<%=i%>">Minutes:</strong> <br> <p><%=restaurantArr[i].getDrivingTime() %> </p>
   							</div>

    					<div class="col-sm">
     	 						<strong id="restaurantAddress<%=i%>">Address: </strong><br> <p><%=restaurantArr[i].getAddress() %></p>
    					</div>
    					<div class="col-sm text-right">
    							<%
    								// Translating the price from number to $ signs
        							String restaurantPrice = "";
        							int price = (int)restaurantArr[i].getPrice();
        							if (price == 1){
        							restaurantPrice = "$";
        							}
        							else if (price == 2){
        							restaurantPrice = "$$";
        							}
        							else{
        								restaurantPrice = "$$$";
        							}
        							%>
     	 						<strong id="restaurantPrice<%=i%>">Price: <%=restaurantPrice%></strong>
    					</div>
  						</div>
					</div>

          			<a href="/FeedMe/restaurantDetails?arrNum=<%=i%>" class="stretched-link" title="restaurantDetailsLink<%=i%>"></a>
        			</div>
        			<div class="col-auto d-none d-lg-block">
        			
          			</div>
      				</div>
      				
          		<% }} %>
		
    	</div>
    	

    	<!-- Recipes lists rendering -->
    		<div class="col-md-6">
      			<h2 id="recipesText" class= "text-center"> Recipes</h2>
          		<% 
          		// rendering the list of recipes while applying alternating grey color on each of the items
          		for(int i = 0; i < resultCount; i++){ 
          			String colorStyle = "";
          			if (i%2 == 0){
          				colorStyle = "silver";
          			}
          			else{
          				colorStyle = "grey";
          			}
          		%>
          			<% if(recipeArr[i] != null){ %>
          			<% System.out.println("Rest Arr: " + i + " " + (restaurantArr[i] == null));  %>
         			<div class="row no-gutters border rounded overflow-hidden flex-md-row md-4 shadow-md h-md-250 position-relative" id="Recipe<%=i%>">
        			<div style="background-color:<%=colorStyle %>;" class="col p-4 d-flex flex-column position-static">
          			<div class="container">
  						<div class="row">
    						<div class="col-sm">
      							<strong id="recipeName<%=i%>">Name:</strong> <br><p><%=recipeArr[i].getName() %></p>
   							</div>
    					<div class="col-sm">
    						<% String recipeRating = String.format("%.1f",recipeArr[i].getRating()); %>
     	 						<strong id="recipeRating<%=i%>">Stars:</strong> <br> <p> <%=recipeRating %> </p>
    					</div>
  						</div>
  						<div class="row">
    						<div class="col-sm">
   							</div>
    					<div class="col-sm">
    					</div>
  						</div>
  						<div class="row">
    						<div class="col-sm">
    							<%
    								// set cook time to not available if no value is given back from API
    								double cookTime = recipeArr[i].getCookTime();
    								String renderCookTime = "";
    								if (cookTime < 0){
    									renderCookTime = "Not Available";
    								}
    								else{
    									renderCookTime = Double.toString(cookTime);
    								}
    								// set prep time to not available if no value is given back from API
    								double prepTime = recipeArr[i].getPrepTime();
    								String renderPrepTime = "";
    								if (prepTime < 0){
    									renderPrepTime = "Not Available";
    								}
    								else{
    									renderPrepTime = Double.toString(prepTime);
    								}			
    							%>
      							<strong id="recipeCookTime<%=i%>">Cook Time:</strong> <br> <p><%=renderCookTime %></p>
   							</div>

    					<div class="col-sm">
     	 						<strong id="recipePrepTime<%=i%>">Prep Time: </strong><br> <p><%=renderPrepTime%></p>
    					</div>			
  						</div>
					</div>
          			<a href="/FeedMe/recipeDetails?arrNum=<%=i%>" class="stretched-link"></a>
        			</div>
        			<div class="col-auto d-none d-lg-block">
          			</div>
      				</div>
          		<% }} %>
    	</div>
   		</div>
	</div>
<script>
// manaageList(form) function check the selection and make sure the redirection is correct
function manageList(form){
	var userInput = document.getElementById('listName').value;
	console.log(userInput);
	// if the user didn't choose any selection, stays on the page
	if (userInput == null || userInput.length == 0){
		return false;
	}
	// if the user choose one of the predefined lists, go to list management page
	else{
		form.action = "/FeedMe/listManagement";
		return true;
	}
} 
</script>
</body>