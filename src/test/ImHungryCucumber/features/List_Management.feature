Feature: List Management Page

Background:

	Given I am on the Results Page

Scenario: User adds a restaurant to Favorite list and a recipe to do not show list
	
	When I add a restaurant to Favorite list
	And I add a recipe to Do Not Show list
	And I go to Favorite list management page
	Then there is restaurant name
	Then there is stars
	Then there is minutes
	Then there is address
	Then there is price
	Then there is dropdown box to select predefined lists
	Then dropdown has 4 options
        Then no list is selected in dropdown by default
	Then there is Manage List button
	Then there is Back to Results button
	Then there is Return to Search button
	Then there is move dropdown box to select predefined list
	Then there is Move button
	When I move the restaurant to To Explore list
	And I go to Do Not Show list management page
	Then there is recipe name
	Then there is stars
	Then there is cook time
	Then there is prep time
	Then there is dropdown box to select predefined lists
	Then dropdown has 4 options
        Then no list is selected in dropdown by default
	Then there is Manage List button
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
	Then there is stars
	Then there is minutes
	Then there is address
	Then there is price
	When I click on the restaurant in list management page
	Then I am in the Restaurant Details Page
	

