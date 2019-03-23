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
    <link href="../css/buttons.css" rel="stylesheet" >
    <link href="../css/details.css" rel="stylesheet" >
    <%@page import="java.util.*" %>
	<%@page import="data.*"%>
    <% 
    	// Get restaurant item from servlet
		Restaurant restaurantVal = (Restaurant) request.getAttribute("restaurantVal");
    %>
    
    
    <!-- Title -->
    <title>Restaurant Details</title>
  </head>


  <body style="background-color:whitesmoke;">
    <!-- Main Div -->
    <div id="main">
       <!-- Title -->
       <h1 id="restaurantName"><%= restaurantVal.getName() %></h1>
       <!-- Holds image, prep and cook time of recipe-->
       <div id="details">
         <a id="address" href="<%= "http://maps.google.com/maps?q=" + restaurantVal.getAddress()%>"><strong>Address:</strong> <%= restaurantVal.getAddress() %></a>
         <p id="phoneNumber"><strong>Phone Number:</strong><%= restaurantVal.getPhoneNumber() %></p>
         <a id="website" href="<%= restaurantVal.getWebsiteUrl() %>"><strong>Website Address: </strong><%= restaurantVal.getWebsiteUrl() %></a>
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