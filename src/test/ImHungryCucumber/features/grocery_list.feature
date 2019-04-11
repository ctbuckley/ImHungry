Feature: Checking that I am on the grocery list Page

Background:
	Given I am on the ImHungry Login Page
    And I enter in "testUsername10" in the username field
    And I enter in "testPassword10" in the password field
    And I click on the log in button
    And I enter "burger" in the search box
	And I enter "2" in the search number box
	And I enter "2" in the radius input field
	And press search
    Then I should see results for "burger"

Scenario: Check the features of the grocery List
    When I click on the grocery link
    And I should see the grocery list title
    And I should see the clear list button
    And I should see the log out link
    Then I should see the return to search link

Scenario: Check the grocery link works on the search page
    When I visit the search page
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the results page
    When I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the recipe details page
    When I view the first recipe
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the restaurant details page
    When I view the first restaurant
    And I click on the grocery link
    Then I should see the grocery list title

Scenario: Check the grocery link works on the list management page
    When I go to Favorite list management page 
    And I click on the grocery link
    Then I should see the grocery list title