Feature: Checking the I am hungry login page

Background:

    Given I am on the ImHungry Login Page

Scenario: Check that the page has an I'm Hungry header

    Then there is a header that says I'm Hungry

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

Scenario: Check that search persist when the user logs out
    When I enter in "testUsername12" in the username field
    And I enter in "testPassword12" in the password field
    And I click on the sign up button
    And I enter "ramen" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I should see results for "ramen"
    And I should see 1 history result
    And I click on the log out button
    And I enter in "testUsername12" in the username field
    And I enter in "testPassword12" in the password field
    And I click on the log in button
    And I enter "chicken" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I should see 2 history results

Scenario: Check that the user lists persist when the user logs out
    When I enter in "testUsername13" in the username field
    And I enter in "testPassword13" in the password field
    And I click on the sign up button
    And I enter "ramen" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I should see results for "ramen"
    And I add a recipe to Favorite list
    And I click on the log out button
    And I enter in "testUsername13" in the username field
    And I enter in "testPassword13" in the password field
    And I click on the log in button
    And I enter "chicken" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I go to Favorite list management page
    And there is a recipe name in the card
    And there is recipe stars
    And there is cook time
    Then there is prep time

Scenario: Check that the grocery list persist when the user logs out
    When I enter in "testUsername14" in the username field
    And I enter in "testPassword14" in the password field
    And I click on the sign up button
    And I enter "ramen" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I should see results for "ramen"
    And I view the first recipe
	And I click on the first ingredient
	And I add selected ingredients to the grocery list
	And I click on the grocery link
	And I should see the first ingredient in the grocery list
    And I click on the log out button
    And I enter in "testUsername14" in the username field
    And I enter in "testPassword14" in the password field
    And I click on the log in button
    And I enter "chicken" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I click on the grocery link
    Then I should see the first ingredient in the grocery list