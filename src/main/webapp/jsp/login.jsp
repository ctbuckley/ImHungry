<html>
<head>
	<!-- Bootstrap CSS file linkage -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- Bootstrap JS file linkage -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<!-- Customised CSS file linkage -->
 	<link href="/FeedMe/css/login.css" rel="stylesheet" type="text/css">
	<!-- Javascript -->
	<script type="text/javascript" src="/FeedMe/javascript/searchEmoji.js"></script>
	<script type="text/javascript" src="/FeedMe/javascript/login.js"></script>
	<!--  jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<!-- Title -->
	<title>Log In</title>
</head>

<!-- Html body -->
<body style="background-color:#ffffff;">
	<div class="container">
  		<div class="text-center align-middle hungry_cont">
  			<h1 id="hungryText">ImHungry</h1>
  		</div>
	</div>
	<div class="log_in_cont">
		<div class="log_in_col_left">
			<div class="no_sign_in_cont">
				<button id="guestLogInButton" class="btn btn-outline-primary" onclick="noSignIn()">Continue without signing in</button>
			</div>
		</div>
		<div class="log_in_col_right">
			<div class="log_in_input_cont">
				<div class="username_input_cont form-group">
					 <input type="text" class="form-control log_in_input" aria-label="Default" aria-describedby="inputGroup-sizing-default" id="usernameInput" placeholder="Username">
				</div>
				<div class="password_input_cont form-group">
  					<input type="password" class="form-control log_in_input" aria-label="Default" aria-describedby="inputGroup-sizing-default" id="passwordInput" placeholder="Password">
				</div>
			</div>
			<div class="log_in_error_cont" id="errorField">
				a;ofijrapaoijgm;rgijagj
			</div>
			<div class="right_btns_cont">
				<div class="log_in_btn_cont">
					<button id="userLogInButton" class="btn btn-outline-primary" onclick="validate()">Log In</button>
				</div>
				<div class="sign_up_btn_cont">
					<button id="signUpButton" class="btn btn-outline-primary" onclick="addUser()">Sign Up</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="../javascript/login.js"></script>
</body>
</html>
