<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="data.Recipe"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">    
    <meta charset="ISO-8859-1">
    <!-- Bootstrap CSS file linkage -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- Bootstrap JS file linkage -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    
    <link href="/FeedMe/css/recipe_details.css" rel="stylesheet">
    <script src="/FeedMe/javascript/buttons.js"></script>
    <%@page import="java.util.*" %>
	<%@page import="data.*"%>
    <% 
    String resultsOrList = (String) request.getSession().getAttribute("resultsOrList");
	Recipe recipeVal = (Recipe) request.getAttribute("recipeVal");
    int arrNum = Integer.parseInt((String) request.getParameter("arrNum"));
	if(resultsOrList.equals("list")){
		ArrayList<Recipe> rest = (ArrayList<Recipe>) request.getSession().getAttribute("recipes");
		recipeVal = rest.get(arrNum);
	}
    %>
    <!-- Title -->
    <title>Recipe Details</title>
  </head>


<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light" id="navBar">
		<a class="navbar-brand" onclick="goToSearchPage()">ImHungry</a>
	 	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
	   		<span class="navbar-toggler-icon"></span>
	 	</button>
	 	<div class="collapse navbar-collapse ml-auto" id="navbarNavDropdown">
	   		<ul class="navbar-nav ml-auto">
<!--   			<li class="nav-item dropdown ml-auto" id="listName">
		        	<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          		Add to List
		        	</a>
		        	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
			      		<a class="dropdown-item" id="fOptionButton" href="/FeedMe/recipeDetails?listType=f&arrNum=<%= arrNum %>">Favorites</a>
			      		<a class="dropdown-item" id="tOptionButton" href="/FeedMe/recipeDetails?listType=t&arrNum=<%= arrNum %>">To Explore</a>
			      		<a class="dropdown-item" id="dOptionButton" href="/FeedMe/recipeDetails?listType=d&arrNum=<%= arrNum %>">Do Not Show</a>     
		        	</div>
		    	</li>  -->
		   		<li class="nav-item dropdown ml-auto" id="quickAccessDropdown">
	        		<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          			Past Searches
	        		</a>
	        		<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
	  <!--    <a class="dropdown-item" id="quickAccessResult10" href="#">Past Search 1</a>   -->   
	        		</div>
	      		</li>
	     		<li class="nav-item active ml-auto">
	       			<a class="nav-link" id="userButton" href="#">Log In</a>
	     		</li>
	     	</ul>
	 	</div>
	</nav>
	<% String picUrl = recipeVal.getPictureUrl(); %>
	<div class="recipe_picture_cont">
		<img id="recipePicture" src="<%= picUrl %>" alt="Recipe Image"/>
	</div>
	<div class="bg_cont" style="background-image: url(<%=picUrl%>); background-repeat: no-repeat;"></div>	
	        
    </div>	
    <!-- Title -->
    <h1 id="recipeName"><%= recipeVal.getName() %></h1>
    <!-- Row -->
    <div class="row">
	    <div class="col-sm-10 outer_detail_cont">
			<!-- Holds image, prep and cook time of recipe-->
			<div id="details">
				<%     
					double cookTime = recipeVal.getCookTime();
					String renderCookTime = "";
					if (cookTime < 0){
						renderCookTime = "Not Available";
					}
					else{
						renderCookTime = Double.toString(cookTime) + " minutes";
					}
					
					double prepTime = recipeVal.getPrepTime();
					String renderPrepTime = "";
					if (prepTime < 0){
						renderPrepTime = "Not Available";
					}
					else{
						renderPrepTime = Double.toString(prepTime) + " minutes";
					}			
				%>
		        <div class="recipe_time_cont">
			        <p id="prepTime"><strong>Prep Time: </strong><%=renderPrepTime %></p>
			        <p id="cookTime"><strong>Cook Time: </strong><%=renderCookTime %></p>
			    </div>
			</div>
		      <!-- Ingredients -->
		      <div id="ingredientsBloc" class="">
		        <h2 class="recipe_section_title">Ingredients</h2>
		        <ul id="ingredients" class="r-inline-flex clearfix">
		          <% ArrayList<String> ingredients = (ArrayList<String>) recipeVal.getIngredients();%>
		          <% for(int i = 0; i < ingredients.size(); i++){ %>
		          	<li class="" style="width:45%;float:left;margin-right:5%;"><p><%=ingredients.get(i) %></p></li>
		          <% } %>
		        </ul>
		      </div>
		      <!-- Instructions -->
		      <div id="instructionsBloc" class="">
		        <h2 class="recipe_section_title">Instructions</h2>
		        <ol id="instructions" class="r-inline-flex clearfix">
		          <% ArrayList<String> ins = (ArrayList<String>) recipeVal.getInstructions();%>
		          <% for(int i = 0; i < ins.size(); i++) { %>
		          	<li class=""><p><%=ins.get(i) %></p></li>
		          <% } %>
		        </ol>
		        <br/>
		      </div>
	    </div>
	    <!-- Holds all the buttons -->
	    <div class="buttons col-sm">
	      <!-- Brings user to a printable version of the page -->
	      	<button id="printButton" class="btn btn-outline-primary" onclick="hideButtons()">Printable Version</button>
	      
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
    <!-- Homebrew JS -->
    <script>
    // Adds the item to the specified list, if the user specifies the proper list
    function addToList(form){
    	var userInput = document.getElementById('listType').value;
    	console.log(userInput);
    	if (userInput == null || userInput.length == 0){
    		return false;	
    	}
    	else{
    		form.action = "/FeedMe/recipeDetails";
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