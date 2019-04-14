Feature: Checking that I am on the grocery list Page

Background:
	Given I am on the ImHungry Search Page

Scenario: Check the features of the grocery List
    When I return to the Log In Page
    And I enter in "testUsername10" in the username field
    And I enter in "testPassword10" in the password field
    And I click on the sign up button
    And I enter "burger" in the search box
	And I enter "2" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I should see results for "burger"
    And I click on the grocery link
    And I should see the grocery list title
    And I should see the clear list button
    And I should see the log out link
    Then I should see the return to search link

Scenario: Check the grocery link works on the search page
    When I visit the search page
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the results page
    When I enter "burger" in the search box
	And I enter "2" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the recipe details page
    When I enter "burger" in the search box
	And I enter "2" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I view the first recipe
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the restaurant details page
    When I enter "burger" in the search box
	And I enter "2" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I view the first restaurant
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the list management page
    When I enter "burger" in the search box
	And I enter "2" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I go to Favorite list management page 
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check that an item can be added to and removed from the grocery list
	When I return to the Log In Page
    And I enter in "testUsername11" in the username field
    And I enter in "testPassword11" in the password field
    And I click on the sign up button
	When I enter "burger" in the search box
	And I enter "2" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I view the first recipe
	And I click on the first ingredient
	And I add selected ingredients to the grocery list
	And I click on the grocery link
	And I should see the first ingredient in the grocery list
	And I remove the first ingredient from the grocery list
	Then I should not see the first ingredient in the grocery list



