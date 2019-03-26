<html>
<head>
	<!-- Bootstrap CSS file linkage -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	
	<!-- Customised CSS file linkage -->
 	<link href="../css/login.css" rel="stylesheet" type="text/css">
	<!-- Javascript -->
	<script type="text/javascript" src="../javascript/searchEmoji.js"></script>
	<!-- Title -->
	<title>Log In</title>
</head>

<!-- Html body -->
<body style="background-color:#ffffff;">
	<div class="container">
  		<div class="text-center align-middle hungry_cont">
  			<h1 id="hungryText">I'm Hungry</h1>
  		</div>
	</div>
	<div class="log_in_cont">
		<div class="log_in_col_left">
			<div class="no_sign_in_cont">
				<button id="guestLogInButton" onclick="noSignIn()">Continue without signing in</button>
			</div>
		</div>
		<div class="log_in_col_right">
			<div class="log_in_input_cont">
				<div class="username_input_cont">
					<input type="text" class="log_in_input" id="usernameInput" placeholder="Username">
				</div>
				<div class="password_input_cont">
					<input type="text" class="log_in_input" id="passwordInput" placeholder="Password">
				</div>
			</div>
			<div class="log_in_error_cont" id="errorField">
				a;ofijrapaoijgm;rgijagj
			</div>
			<div class="right_btns_cont">
				<div class="log_in_btn_cont">
					<button id="userLogInButton" onclick="validate()">Log In</button>
				</div>
				<div class="sign_up_btn_cont">
					<button id="signUpButton" onclick="validate()">Sign Up</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="../javascript/login.js"></script>
</body>
</html>
