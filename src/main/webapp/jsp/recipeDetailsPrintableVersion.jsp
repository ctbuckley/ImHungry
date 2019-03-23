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
    	// Get recipe item and it's place in the list from servlet
		Recipe recipeVal = (Recipe) request.getAttribute("recipeVal");
    	Integer arrNum = (Integer) request.getAttribute("arrNum");
    %>
    <!-- Title -->
    <title>Recipe Details</title>
  </head>


  <body style="background-color:whitesmoke;">
    <!-- Main Div -->
    <div id="main">
      <!-- Title -->
      <h1 id="recipeName"><%= recipeVal.getName() %></h1>
      <!-- Holds image, prep and cook time of recipe-->
      <div id="details">
      	<% String picUrl = recipeVal.getPictureUrl(); %>
        <img id="recipePicture" src="<%= picUrl %>" alt="Recipe Image"/>
        <%     
        	// Get the proper cook time, makes sure it's not negative
			double cookTime = recipeVal.getCookTime();
			String renderCookTime = "";
			if (cookTime < 0){
				renderCookTime = "Not Available";
			}
			else{
				renderCookTime = Double.toString(cookTime) + " minutes";
			}
			// Get the proper prep time, makes sure it's not negative
			double prepTime = recipeVal.getPrepTime();
			String renderPrepTime = "";
			if (prepTime < 0){
				renderPrepTime = "Not Available";
			}
			else{
				renderPrepTime = Double.toString(prepTime) + " minutes";
			}			
        %>
        <!-- Display both times -->
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
    <div id="buttons">
      
    </div>
    <!-- Homebrew JS -->
    
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