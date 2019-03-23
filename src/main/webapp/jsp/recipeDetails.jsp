<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="data.Recipe"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">    
    <meta charset="ISO-8859-1">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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


  <body style="background-color:whitesmoke;">
    <!-- Row -->
    <div class="row">
	    <div class="col-sm-10">
	      <!-- Title -->
	      <h1 id="recipeName"><%= recipeVal.getName() %></h1>
	      <!-- Holds image, prep and cook time of recipe-->
	      <div id="details">
	      	<% String picUrl = recipeVal.getPictureUrl(); %>
	        <img id="recipePicture" src="<%= picUrl %>" alt="Recipe Image"/>
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
	        <p id="prepTime"><strong>Prep Time: </strong><%=renderPrepTime %></p>
	        <p id="cookTime"><strong>Cook Time: </strong><%=renderCookTime %></p>
	      </div>
	      <!-- Ingredients -->
	      <div id="ingredientsBloc" class="">
	        <h2>Ingredients</h2>
	        <ul id="ingredients" class="r-inline-flex clearfix">
	          <% ArrayList<String> ingredients = (ArrayList<String>) recipeVal.getIngredients();%>
	          <% for(int i = 0; i < ingredients.size(); i++){ %>
	          	<li class="" style="width:45%;float:left;margin-right:5%;"><p><%=ingredients.get(i) %></p></li>
	          <% } %>
	        </ul>
	      </div>
	      <!-- Instructions -->
	      <div id="instructionsBloc" class="">
	        <h2 class="">Instructions</h2>
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
	      <form action="/FeedMe/recipeDetailsPagePrintableVersion?arrNum=<%= arrNum%>" method= "POST">
	      	<button id="printButton" class="Button">Printable Version</button>
	      </form>
	      
	      <!-- Brings user back to results page -->
	       <form action="/FeedMe/results" method="POST">
	        <button id="backToResults" class="Button">Back To Results</button>
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
	      <button type="submit" id="addToList" class="Button">Add to List</button>
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