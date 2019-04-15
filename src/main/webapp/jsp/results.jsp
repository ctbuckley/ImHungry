<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@page import="java.util.*"%>
<%@page import="data.*"%>
<%
	// set the session to declare whether it is on results page or list management page
	request.getSession().setAttribute("resultsOrList", "results");
	// get the google image data from the google custom search api
	String[] imageUrlVec = (String[]) request.getAttribute("imageUrlVec");
	// get the search term from the request
	String searchTerm = (String) request.getAttribute("searchTerm");
	// get the search number from the request 
	Integer resultCount = (Integer) request.getAttribute("resultCount");
	// get the array of the restaurants from the request 
	Restaurant[] restaurantArr = (Restaurant[]) request.getAttribute("restaurantArr");
	// get the array of recipes from the request
	Recipe[] recipeArr = (Recipe[]) request.getAttribute("recipeArr");
	// get the number of pages for pagination
	Integer resultPageCount = (Integer) request.getSession().getAttribute("pageCount");
	String pageNumberRaw = request.getParameter("pageNumber");
	Integer currentPageNumber = 1;
	if (pageNumberRaw != null) currentPageNumber = Integer.parseInt(request.getParameter("pageNumber"));
	Integer totalResultsRequested = (Integer) request.getSession().getAttribute("n");
	String visibleRestaurant = "none";
	String visibleRecipe = "none";
	if(restaurantArr[0] == null) {
		visibleRestaurant = "inherit";
	}
	if(recipeArr[0] == null) {
		visibleRecipe = "inherit";
	}

%>
<!-- Bootstrap CSS  -->
    	<!-- Bootstrap CSS file linkage -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<!-- Bootstrap JS file linkage -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<!-- Homebrew CSS  -->
<link
	href='http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.min.css'
	rel='stylesheet' type='text/css'>
<link href="/FeedMe/css/results.css" rel="stylesheet" type="text/css">
<link href="/FeedMe/css/buttons.css" rel="stylesheet" type="text/css">
<link href="/FeedMe/css/navbar.css" rel="stylesheet" type="text/css">
<!-- Javascript -->
<script type="text/javascript"
	src="/FeedMe/javascript/manageListButton.js"></script>
	<script type="text/javascript" src="/FeedMe/javascript/login.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title><%=searchTerm%></title>
</head>

<!-- Body Html -->
<body onload="init();">
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" id="returntoSearch" onclick="goToSearchPage()">ImHungry</a>
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
	   			<li class="nav-item dropdown ml-auto" id="listName">
		        	<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          		Manage Lists
		        	</a>
		        	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
			      		<a class="dropdown-item" id="fOptionButton" href="/FeedMe/listManagement?listIndex=0">Favorites</a>
			      		<a class="dropdown-item" id="tOptionButton" href="/FeedMe/listManagement?listIndex=2">To Explore</a>
			      		<a class="dropdown-item" id="dOptionButton" href="/FeedMe/listManagement?listIndex=1">Do Not Show</a>     
		        	</div>
		    	</li>
	     		<li class="nav-item active ml-auto">
	       			<a class="nav-link" id="userButton" href="http://localhost:8080/FeedMe/jsp/login.jsp">Log Out</a>
	     		</li>
	     	</ul>
	 	</div>
	</nav>
	<div class="container mt-5">
		<!-- Row for collage and buttons -->
		<div class="row align-items-start">
			<div class="gallery">
				<%
					// creating random angle for each of the image in the collage
					for (int i = 0; i < 10; i++) {
						Random rand = new Random();
						int angle = rand.nextInt(90) - 45;
				%>
				<figure class="gallery_item gallery_item_<%=i%>">
					<img src="<%=imageUrlVec[i]%>" class="gallery_img" alt=""/>
				</figure>
				<%
					}
				%>
			</div>
		</div>

		<!-- Search For xx  -->
		<div class="py-5 text-center">
			<h2 id="resultsForText">
				Results For
				<%=searchTerm%></h2>
		</div>

		<!-- Restaurants lists rendering -->
		<div class="row md-2">
			<div class="col-md-6">
				<h2 id="restaurantTitle" class="text-center">Restaurants</h2>
				<div id="errorMessageRestaurant" class="errorCont text-center" style="display: <%= visibleRestaurant %>">No Restaurants available with provided parameters</div>
				<%
					// rendering the list of restauratns while applying alternating grey color on each of the items
					for (int i = 0; i < resultCount; i++) {
						if (restaurantArr[i] != null) {
							System.out.println("Rest Arr: " + i + " " + (restaurantArr[i] == null));
							String restaurantPrice = "";
							int price = (int) restaurantArr[i].getPrice();
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
				<!-- Restaurant item rendering -->
				<div
					class="row no-gutters rounded overflow-hidden flex-md-row md-4 shadow-md h-md-250 position-relative results-card mb-3"
					id="Restaurant<%=i%>"
					onclick="window.location='/FeedMe/restaurantDetails?arrNum=<%=i%>'">
					<div class="p-4 d-flex flex-column position-static container list_element">
							<div class="row">
								<div class="col-10">
									<p id="restaurantName<%=i%>"><strong><%=restaurantArr[i].getName()%></strong></p>
								</div>
								<div class="col-2 text-right">
									<h4 id="restaurantPrice<%=i%>"><%=restaurantPrice%></h4>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<div id="restaurantRating<%=i%>" class="outer_stars"
										data-stars="<%=restaurantArr[i].getRating()%>">
										<div class="inner_stars" id="innerRestaurantRating<%=i%>"></div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<p id="restaurantAddress<%=i%>"><%=restaurantArr[i].getAddress()%></p>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<p id="restaurantDistance<%=i%>" class="minutes-away"><%=restaurantArr[i].getDrivingTime()%> Min(s) Away</p>
								</div>
							</div>
						</div>
					<div class="col-auto d-none d-lg-block"></div>
				</div>
				<%
					}
					}
				%>
			</div>
			<!-- Recipes lists rendering -->
			<div class="col-md-6">
				<h2 id="recipeTitle" class="text-center">Recipes</h2>
				<div id="errorMessageRecipe" class="errorCont text-center" style="display: <%= visibleRecipe %>">No Recipes available with provided parameters</div>
				<%
					// rendering the list of recipes while applying alternating grey color on each of the items
					for (int i = 0; i < resultCount; i++) {
						if (recipeArr[i] != null) {
							System.out.println("Rest Arr: " + i + " " + (restaurantArr[i] == null));
				%>
				<div class="row no-gutters rounded overflow-hidden flex-md-row md-4 shadow-md h-md-250 position-relative results-card mb-3"
					id="Recipe<%=i%>"
					onclick="window.location='/FeedMe/recipeDetails?arrNum=<%=i%>'">
					<div class="p-4 d-flex flex-column position-static container list_element">
							<div class="row">
								<div class="col-10">
									<p id="recipeName<%=i%>"><strong><%=recipeArr[i].getName()%></strong></p>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<div id="recipeRating<%=i%>" class="outer_stars"
										data-stars="<%=recipeArr[i].getRating()%>">
										<div class="inner_stars" id="innerRecipeRating<%=i%>"></div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-6">
									<%
										// set cook time to not available if no value is given back from API
												int cookTime = (int)recipeArr[i].getCookTime();
												String renderCookTime = "";
												if (cookTime < 0) {
													renderCookTime = "Not Available";
												} else {
													renderCookTime = Integer.toString(cookTime);
												}
												// set prep time to not available if no value is given back from API
												int prepTime = (int)recipeArr[i].getPrepTime();
												String renderPrepTime = "";
												if (prepTime < 0) {
													renderPrepTime = "Not Available";
												} else {
													renderPrepTime = Integer.toString(prepTime);
												}
									%>
									<p id="recipeCookTime<%=i%>">Cook Time: <%=renderCookTime%> min(s)</p>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<p id="recipePrepTime<%=i%>" class="minutes-away"> Prep Time: <%=renderPrepTime%> min(s)</p>
								</div>
							</div>
						<a href="/FeedMe/recipeDetails?arrNum=<%=i%>"
							class="stretched-link"></a>
					</div>
					<div class="col-auto d-none d-lg-block"></div>
				</div>
				<%
					}
					}
				%>
			</div>
		</div>
		<!-- PAST SEARCHES -->
		<div class="pastSearches_outer_cont">
			<h3>Past Searches</h3>
			<div class="pastSearchesCont" id="dropdown-menu-populate">
			</div>
		</div>
	</div>
	<div class="pagination_cont">
		<nav aria-label="Page navigation example" class="pagination_nav"
			id="page_bar">
			<ul class="pagination">
				<%
					String previousLinkActive = "";
					String nextLinkActive = "";
					if(currentPageNumber == 1) {
						previousLinkActive = "disabled";
					}
					if(currentPageNumber == resultPageCount) {
						nextLinkActive = "disabled";
					}
				%>
				 <li class="page-item <%= previousLinkActive %>"><a id="paginationPrevious" class="page-link" href="/FeedMe/results?pageNumber=<%=currentPageNumber - 1%>">Previous</a></li>
	 			<!-- Where to insert new page items when generating the page -->
	 			<%
	 				int numPaginationLinksCreated = 0;
	 				int startLinkCreationIndex = 1;
	 				if (currentPageNumber <= 3) startLinkCreationIndex = 1;
	 				else if (resultPageCount - currentPageNumber <= 2) startLinkCreationIndex = resultPageCount - 4;
	 				else startLinkCreationIndex = currentPageNumber - 2;
	 				
	 				for(int i=1; i<=resultPageCount; i++) {
	 					String paginationLinkActive="";
	 					if(currentPageNumber == i) paginationLinkActive = "active";
	 					if (numPaginationLinksCreated >= 5 || i < startLinkCreationIndex) continue;
	 					else numPaginationLinksCreated++;
	 			%>
	 					<li class="page-item <%= paginationLinkActive %>"><a class="page-link" id="paginationLink<%= i %>" href="/FeedMe/results?pageNumber=<%=i%>"><%=i%></a></li>
	 			<%
	 				
	 				}
	 			%>
				<li class="page-item <%= nextLinkActive %>"><a id="paginationNext" class="page-link" href="/FeedMe/results?pageNumber=<%= currentPageNumber + 1%>">Next</a></li>
			</ul>
		</nav>
	</div>
	<script>
	
		function init() {
			setStars();
			setPreviousPagination();
			setNextPagination();
			loadSearchHistory();
		}
		
		function setPreviousPagination() {
			let previousPageNumber = (<%=currentPageNumber%> <= 1) ? 1 : <%=currentPageNumber%> - 1;
			let previousPageLink = "/FeedMe/results?pageNumber=" + previousPageNumber;
			document.getElementById("paginationPrevious").href = previousPageLink;
		}
		
		function setNextPagination() {
			let nextPageNumber = (<%=currentPageNumber%> >= <%=resultPageCount%>) ? <%=currentPageNumber%> : <%=currentPageNumber%> + 1;
			let nextPageLink = "/FeedMe/results?pageNumber=" + nextPageNumber;
			document.getElementById("paginationNext").href = nextPageLink;
		}
		
		// manageList(form) function check the selection and make sure the redirection is correct
		function manageList(form) {
			var userInput = document.getElementById('listName').value;
			console.log(userInput);
			// if the user didn't choose any selection, stays on the page
			if (userInput == null) {
				return false;
			}
			// if the user choose one of the predefined lists, go to list management page
			else {
				form.action = "/FeedMe/listManagement";
				return true;
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
			}
			;
		}
		
		function goToSearchPage() {
			window.location.href = "http://localhost:8080/FeedMe/jsp/search.jsp";
		}
		
		
	</script>
</body>