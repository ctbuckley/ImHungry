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
	When I enter in "halfond" in the username field
	And I enter in "documentation" in the password field
	And I click on the sign up button
	And I return to the Log In Page
	And I enter in "halfond" in the username field 
	And I enter in "documentation" in the password field
	And I click on the log in button
	Then I should be on the Search Page
