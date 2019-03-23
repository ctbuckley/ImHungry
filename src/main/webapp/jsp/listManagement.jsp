<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">    
    <meta charset="ISO-8859-1">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Homebrew CSS" -->
    
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
  <body style="background-color:whitesmoke;">
    <div id="main" class="d-inline-flex p-1">
      <div class="p-2 ml-2">
      <!-- Restaurants and Recipes lists  -->
      	<h1><%=listName %> List</h1>
      		<% // Used to alternate colors
   			int j = 0;
          	while(j < restaurantArr.size()){ 
          	String colorStyle = "";
        	if (j%2 == 0){
          		colorStyle = "silver";
        	}
          	else{
          		colorStyle = "grey";
          	}
          	%>
          	<!-- This is the restaurant div -->
          	<div class="col-12" id="Restaurant<%=j%>">
         			<div class="row no-gutters border rounded overflow-hidden flex-md-row mb-8 shadow-sm h-md-250 position-relative">
        			<div style="background-color:<%=colorStyle %>;"class="col p-4 d-flex flex-column position-static">
          			<div class="container">
  						<div class="row">
    					<div class="col-sm">
							<strong>Name:</strong> <br><p> <%= restaurantArr.get(j).getName() %> </p>
   						</div>

    					<div class="col-sm">
     	 						<strong>Stars:</strong> <br> <p> <%=restaurantArr.get(j).getRating() %> </p>
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
      							<strong>Minutes:</strong> <br> <p><%=restaurantArr.get(j).getDrivingTime() %> </p>
   							</div>

    					<div class="col-sm">
     	 						<strong>Address: </strong><br> <p><%=restaurantArr.get(j).getAddress() %></p>
    					</div>
    					<div class="col-sm text-right">
    							<%
        							String restaurantPrice = "";
        							int price = (int)restaurantArr.get(j).getPrice();
        							if (price == 1){
        							restaurantPrice = "$";
        							}
        							else if (price == 2){
        							restaurantPrice = "$$";
        							}
        							else if (price == 3) {
        								restaurantPrice = "$$$";
        							}
        							else {
        								restaurantPrice = "$$$$";
        							}
        							%>
     	 						<strong>Price: <%=restaurantPrice%></strong>
    					</div>
  						</div>
					</div>

          			<a href="/FeedMe/restaurantDetails?arrNum=<%=j%>" class="stretched-link"></a>
        			</div>
      				</div>
      				<!-- This form takes the item to the specified list page -->
      				<form style="display:flex;flex-direction:column;justify-content:center;" method="POST" action="/FeedMe/listManagement">
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
          	String colorStyle = "";
        	if     (k%2 == 0 && restaurantArr.size() % 2 == 0){
          		colorStyle = "silver";
        	}
        	else if(k%2 != 0 && restaurantArr.size() % 2 == 0){
          		colorStyle = "grey";
          	}
        	else if(k%2 == 0 && restaurantArr.size() % 2 != 0){
        		colorStyle = "grey";
        	}
          	else if(k%2 != 0 && restaurantArr.size() % 2 != 0){
          		colorStyle = "silver";	
          	}
          	%>
          	<!-- Where the recipes start -->
    		<div class="col-12" id="Recipe<%=k%>">
         			<div class="row no-gutters border rounded overflow-hidden flex-md-row mb-8 shadow-sm h-md-250 position-relative">
        			<div style="background-color:<%=colorStyle %>;" class="col p-4 d-flex flex-column position-static">
          			<div class="container">
  						<div class="row">
    						<div class="col-sm">
      							<strong>Name:</strong> <br><p><%=recipeArr.get(k).getName() %></p>
   							</div>

    					<div class="col-sm">
    						<% String recipeRating = String.format("%.1f",recipeArr.get(k).getRating()); %>
     	 						<strong>Stars:</strong> <br> <p> <%=recipeRating %> </p>
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
    								// Check the cook time for proper values
    								double cookTime = recipeArr.get(k).getCookTime();
    								String renderCookTime = "";
    								if (cookTime < 0){
    									renderCookTime = "Not Available";
    								}
    								else{
    									renderCookTime = Double.toString(cookTime);
    								}
    								// Check the prep time for proper values
    								double prepTime = recipeArr.get(k).getPrepTime();
    								String renderPrepTime = "";
    								if (prepTime < 0){
    									renderPrepTime = "Not Available";
    								}
    								else{
    									renderPrepTime = Double.toString(prepTime);
    								}			
    							%>
      							<strong>Cook Time:</strong> <br> <p><%=renderCookTime %></p>
   							</div>

    					<div class="col-sm">
     	 						<strong>Prep Time: </strong><br> <p><%=renderPrepTime%></p>
    					</div>
    				
  						</div>
					</div>


					<!-- Link that takes user to recipe details page -->
          			<a href="/FeedMe/recipeDetails?arrNum=<%=k%>" class="stretched-link"></a>
        			</div>
        			<div class="col-auto d-none d-lg-block">
          			</div>
      				</div>
      				<!-- Moves the recipe to the specified list -->
      				<form style="display:flex;flex-direction:column;justify-content:center;" method="POST" action="/FeedMe/listManagement">
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
		<form name="list" onsubmit="return manageList(this);">
      	<select id="dropDownBar" name="listName" class="dropDownBar">
      		<option disabled selected value> -- select an option -- </option>
       		<option value ="f" >Favorites</option>
        	<option value ="t">To Explore</option>
        	<option value ="d">Do Not Show</option>
      	</select>
     	<!-- Button to add item to selected list, doesn't do anything if choice is empty -->
     	<button class="Button" id="manageListButton">Manage List</button> <br>

       </form>
	  <!-- Takes user to the search page -->
      <form action ="/FeedMe/jsp/search.jsp">
      	<button class="Button" id="returnToSearch" onclick="javascript:location.href = this.value;">Return to Search</button>
      </form>
      <!-- Takes the user to the results page -->
	  <form action ="/FeedMe/results">
      	<button class="Button" id="backToResults" onclick="javascript:location.href = this.value;">Return to Results</button>
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
	else{
		form.action = "/FeedMe/listManagement";
	}
} 
</script>
  </body>
  <style>
    <%@ include file="/css/buttons.css"%>
  </style>
</html>