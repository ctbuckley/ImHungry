<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<%
			String[] groceryList = (String[]) request.getSession().getAttribute("groceryList");
		%>
		<!-- Bootstrap CSS file linkage -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<!-- Bootstrap JS file linkage -->
		<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		<meta charset="ISO-8859-1">
		<link rel="stylesheet" href="/FeedMe/css/groceryList.css">
		<link rel="stylesheet" href="/FeedMe/css/navbar.css">
		<title>Insert title here</title>
	</head>
	<body>
		<!-- Navigation Bar -->
		<nav class="navbar navbar-expand-lg navbar-light bg-light" id="navBar">
		<a class="navbar-brand" id="returnToSearch" onclick="goToSearchPage()">ImHungry</a>
	 	<button class="navbar-toggler" id="navToggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
	   		<span class="navbar-toggler-icon"></span>
	 	</button>
	 	<div class="collapse navbar-collapse ml-auto" id="navbarNavDropdown">
	   		<ul class="navbar-nav ml-auto">
	   			<li class="nav-item active ml-auto">
					<a class="nav-link" id="grocery_link_button" href="http://localhost:8080/FeedMe/DisplayGroceryList">
						<svg class="grocry_cart" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
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
	
		<!-- Main Body  -->
		<div class="main_body_cont">
			<!-- Title and Button -->
			<div class="grocery_header">
				<h1 id="groceryListTitle">Grocery List</h1>
				<button class="btn btn-outline-primary" id="clearListBtn">Clear List</button>
			</div>
			<!-- List of Ingredients -->
			<div class="grocery_list_cont">
					<%
						for(int i = 0; i < groceryList.length; i++) {
					%>
							<div class="grocery_item_cont" id="groceryItemContainer<%=i%>">
								<div class="grocery_item" id="groceryItem<%=i%>">
									<p><%=groceryList[i]%></p>
								</div>
								<div id="removeBtn<%=i%>"onclick="removeIngredientFromGroceryList('<%=groceryList[i]%>', '<%=i%>');">
									<object class="x-icon" data="/FeedMe/img/x-icon.svg" type="image/svg+xml" style="pointer-events:none"></object>
								</div>
							</div>
					<%
						}
					%>
			</div>
		</div>
		<script>
			function goToSearchPage() {
				window.location.href = "http://localhost:8080/FeedMe/jsp/search.jsp";
			}
			
			function removeIngredientFromGroceryList(ingredientToRemove, indexOfIngredientToRemove) {
				console.log("In removeIngredientFromGroceryList with " + ingredientToRemove);
		    	var xhttp = new XMLHttpRequest();
		    	xhttp.onreadystatechange = function() {
		    		console.log("In removeIngredientFromGroceryList callback function.");
		    	}
		    	xhttp.open("POST", "/FeedMe/DeleteFromGroceries?item=" + ingredientToRemove);
		    	xhttp.send();
		    	let elementToRemove = document.getElementById("groceryItem" + indexOfIngredientToRemove);
		    	elementToRemove.parentNode.removeChild(elementToRemove);
		    }
		</script>
	</body>
</html>