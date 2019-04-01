Feature: Checking the I am hungry login page

Background:

	Given I am on the ImHungry Login Page

Scenario: Check that the page has an I'm Hungry header

	Then there is a header that says I'm Hungry
	
Scenario: Check that the page has a guest login button

	Then there is a guest login button

Scenario: Check that the page has username and password fields as well as a login and sign in button

	Then there is a username and password field as well as a login and sign in button
	
Scenario: Check that the page has an invisible error field

	Then there should be an invisible error field
	
Scenario: Check that a registered user can log in
	
	When I enter in "testUsername1" in the username field
	And I enter in "testPassword1" in the password field
	And I click on the sign up button
	And I return to the Log In Page
	And I enter in "testUsername1" in the username field 
	And I enter in "testPassword1" in the password field
	And I click on the log in button
	Then I should be on the Search Page after servlet

Scenario: Check that an unregistered user can't log in

	When I enter in "testUsername2" in the username field
	And I enter in "testPassword2" in the password field
	And I click on the log in button
	Then I should be on the Login Page and I should see an error message

Scenario: Check that the guest login button redirects the user to the search page

	When I click on the guest log in button
	Then I should be on the Search Page after servlet

Scenario: Check that an unregistered user can sign up

	When I enter in "testUsername3" in the username field
	And I enter in "testPassword3" in the password field
	And I click on the sign up button
	Then I should be on the Search Page after servlet

Scenario: Check that a registered user can't sign up

	When I enter in "testUsername4" in the username field
	And I enter in "testPassword4" in the password field
	And I click on the sign up button
	And I return to the Log In Page
	And I enter in "testUsername4" in the username field
	And I enter in "testPassword4" in the password field
	And I click on the sign up button
	Then I should be on the Login Page and I should see an error message

Scenario: Check that both the username and the password field need to be populated for a user to sign up

	When I click on the sign up button
	Then I should be on the Login Page and I should see an error message

Scenario: Check that both the username and the password field need to be populated for a user to login

	When I click on the log in button
	Then I should be on the Login Page and I should see an error message

Scenario: Check that the username field needs to be populated for a user to login
	
	When I enter in "testPassword5" in the password field
	And I click on the log in button
	Then I should be on the Login Page and I should see an error message

Scenario: Check that the password field needs to be populated for a user to login

	When I enter in "testUsername5" in the username field
	And I click on the log in button
	Then I should be on the Login Page and I should see an error message



