Feature: Checking the I am hungry Results page

Background:

	Given I am on the ImHungry Results Page

Scenario: Requirements for select button/manage lists button/

	Then there is a dropdown button
	And there is a to be explored option
	And there is a do not show option
	And there is a Manage Lists button
	And there is a Return to Search button

Scenario: Check the Transition to the search page

	When press return to search
	Then I should see search page title

Scenario: Check the titles on the page

	Then I should see results for "burger"
	And I should see Restaurant title
	And I should see Recipe title

Scenario: Check the Restaurant list elements
	
	Then there is the name of Restaurant list elements
	And there is the Stars of Restaurant list elements
	And there is the minutes of Restaurant list elements
	And there is the address of Restaurant list elements
	And there is the name of Restaurant list elements
	And there is the price of Restaurant list elements

Scenario: Check the Recipe list elements
	
	Then there is the name of Recipe list elements
	And there is the Stars of Recipe list elements
	And there is the Cook Time of Recipe list elements
	And there is the Prep Time of Recipe list elements

Scenario: Check the Pagination elements
	Then there is a pagination div

Scenario: Check for three pagination elements
	When I am on the ImHungry Search Page
	And I enter "burger" in the search box
	And I enter "11" in the search number box
	And I enter "10" in the radius input field
	And press search
	And there is a pagination div
	And there are "3" pagination links
