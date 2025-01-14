
<html>
<head>
	<!-- Bootstrap CSS file linkage -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<!-- Bootstrap JS file linkage -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<!-- Customised CSS file linkage -->
 	<link href="/FeedMe/css/search.css" rel="stylesheet" type="text/css">
	<!-- Javascript -->
	<script type="text/javascript" src="/FeedMe/javascript/searchEmoji.js"></script>
	<script type="text/javascript" src="/FeedMe/javascript/login.js"></script>
	<!--  jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<!-- Title -->
	<title id="searchTitle">Search Page</title>
	
</head>

<!-- Html body -->
<body>
	<div class="bg_cont"></div>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	 <a class="navbar-brand" id="returntoSearch" href="http://localhost:8080/FeedMe/search">ImHungry</a>
	 <button id="navToggler" class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
	   <span class="navbar-toggler-icon"></span>
	 </button>
	 <div class="collapse navbar-collapse ml-auto" id="navbarNavDropdown">
	   <ul class="navbar-nav ml-auto">
			
	     <li class="nav-item active ml-auto">
	       <a class="nav-link" id="userButton" href="http://localhost:8080/FeedMe/jsp/login.jsp">Log out</a>
	     </li>
	   </ul>
	 </div>
	</nav>

	<div class="container">
  		<div class="search_input_cont text-center ">
  			<h1 id="hungryText">ImHungry</h1>
  			<div class="col-md-12 text-center outer_search_cont"> 
  				<!-- The form including search term/search number -->
				<form id="form" onsubmit= "return changeEmoji(this);"> <!-- Calls the js function that changes the emoji -->
					<input type="hidden" name="fromSearch" value="true">
					<!-- input textbox for search term -->
					<input class="form-control large_input" id="queryInput" type="text" name="q" aria-label="Default" aria-describedby="inputGroup-sizing-default" placeholder="Enter Food">					
			 		<div class="bottom_search_cont">
			 			<!-- input textbox for search number -->
			 			<input class="form-control small_input" id="numResultsInput" title="Number of items to show in results"  aria-label="Default" aria-describedby="inputGroup-sizing-default" type="text" name="n" value="5" >
			 			<!-- input textbox for radius -->
			 			<input class="form-control small_input" id="radiusInput" type="text" name="radiusInput" aria-label="Default" aria-describedby="inputGroup-sizing-default" placeholder="Radius (mi)">
		 			</div>
		 			<input type="text" name="pageNumber" style="display: none" value="1">
					<br>
					<!-- Feed Me button with the emoji image -->
					<button onclick="addToSearchHistory()" class="text-center btn btn-outline-primary" type="submit" value="Feed Me" name ="feedMeButton" id="feedMeButton"><p id="feedMeText">Feed Me!</p><img src="https://images.emojiterra.com/twitter/v11/512px/1f620.png" id ="emoji"><br></button>
				</form>
			</div>
  		</div>
	</div>
</body>
</html>
