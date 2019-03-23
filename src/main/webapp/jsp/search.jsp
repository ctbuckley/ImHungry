
<html>
<head>
	<!-- Bootstrap CSS file linkage -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	
	<!-- Customised CSS file linkage -->
	<link href="../css/search.css" rel="stylesheet" type="text/css">

	<!-- Javascript -->
	<script type="text/javascript" src="../javascript/searchEmoji.js"></script>
	<!-- Title -->
	<title id="searchTitle">Search Page</title>
</head>

<!-- Html body -->
<body style="background-color:whitesmoke;">
	<div class="container">
  		<div class="row">
    		<div class="col">
    		</div>
    		<div class="col-6">
      			<h2 class="text-center">I'm Hungry</h2>
      			<p>
      			<div class="col-md-12 text-center"> 
      				<!-- The form including search term/search number -->
    				<form id="form" onsubmit= "return changeEmoji(this);"> <!-- Calls the js function that changes the emoji -->
  					<!-- input textbox for search term -->
  					<input id="userInput" type="text" name="q" placeholder="Enter Food">
  		 			<!-- input textbox for search number -->
  		 			<input id="searchTermTest" title="Number of items to show in results" type="text" name="n" value="5" >
  					<br>
  					<!-- Feed Me button with the emoji image -->
  					<button class="text-center" type="submit" value="Feed Me" name ="feedMeButton"id="feedMeButton" style="color: red;"><p>Feed Me!</p><img src="https://images.emojiterra.com/twitter/v11/512px/1f620.png" id ="emoji" height = 20 width = 20><br></button>
  				</div>
				</form>
    			</p>
    		</div>
    		<div class="col">

    		</div>
  		</div>
	</div>
</body>
</html>
