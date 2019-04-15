Feature: List Management Page

Background:
    
    Given I am logged in
	Given I am on the Search Page

Scenario: User adds a restaurant to Favorite list and a recipe to do not show list
	
    When I add a restaurant to Favorite list
    And I add a recipe to Do Not Show list
    And I go to Favorite list management page
    Then there is restaurant name
    Then there is restaurant stars
    Then there is minutes
    Then there is address
    Then there is price
    Then there is Back to Results button
    Then there is Return to Search button
    Then there is a grocery list button
    Then there is move dropdown box to select predefined list
    Then there is Move button
    When I move the restaurant to To Explore list
    And I go to Do Not Show list management page
    Then there is a recipe name in the card
    Then there is recipe stars
    Then there is cook time
    Then there is prep time
    Then there is a dropdown button
    Then there is a favorites option
    Then there is a to be explored option
    Then there is a do not show option
    Then there is Back to Results button
    Then there is Return to Search button
    Then there is move dropdown box to select predefined list
    Then there is Move button
    When I trash the recipe
    Then there is no recipe name
    Then there is no stars
    Then there is no cook time
    Then there is no prep time
    When I go to To Explore list management page
    Then there is restaurant name
    Then there is restaurant stars
    Then there is minutes
    Then there is address
    Then there is price
    When I click on the restaurant in list management page
    Then I am in the Restaurant Details Page

Scenario: User can reorder a restaurant in a list
    When I add a restaurant to Favorite list
    And I add the second restaurant to Favorite list
    And I go to Favorite list management page
    And I move the first restaurant to the second position
    Then the second restaurant should be first in the list


Scenario: User can reorder a recipe in a list
    When I add a recipe to Favorite list
    And I add the second recipe to Favorite list
    And I go to Favorite list management page
    And I move the first recipe to the second position
    Then the second recipe should be first in the list