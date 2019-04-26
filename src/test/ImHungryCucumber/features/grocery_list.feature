Feature: Checking that I am on the grocery list Page

Background:
    Given I am logged in
	Given I am on the ImHungry Search Page

Scenario: Check the features of the grocery List
    When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I should see results for "burger"
    And I click on the grocery link
    And I should see the grocery list title
    And I should see the log out link
    Then I should see the return to search link

Scenario: Check the grocery link works on the results page
    When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the recipe details page
    When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I view the first recipe
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the restaurant details page
    When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I view the first restaurant
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the list management page
    When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
    And I go to Favorite list management page 
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check that an item can be added to and removed from the grocery list
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I view the first recipe
	And I click on the first ingredient
	And I add selected ingredients to the grocery list
	And I click on the grocery link
	And I should see the first ingredient in the grocery list
	And I remove the first ingredient from the grocery list
	Then I should not see the first ingredient in the grocery list

Scenario: Check that each item can be checked off
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I view the first recipe
	And I click on the first ingredient
	And I click on the second ingredient
	And I add selected ingredients to the grocery list
	And I click on the grocery link
	And I click on the first ingredient
	And I click on the second ingredient
	And I click on the first ingredient
	Then I expect the second ingredient to be checked

Scenario: Check that duplicate items in the lists merge
	When I enter "chicken" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I view the first recipe
	And I click on the first ingredient
	And I add selected ingredients to the grocery list
	And I click on the grocery link
	Then I should see the first ingredient in the grocery list
	When I go back to the results page
	And I view the first recipe
	And I click on the first ingredient
	And I add selected ingredients to the grocery list
	And I click on the grocery link
	Then I should see the first ingredient with twice the amount in the grocery list
