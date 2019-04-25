Feature: Checking the I am hungry Results page

Background:
	Given I am logged in
	Given I am on the ImHungry Results Page

Scenario: Requirements for select button/manage lists button/

	Then there is a dropdown button
	And there is a to be explored option
	And there is a do not show option
	And there is a Manage Lists button
	And there is a Return to Search button
	And there is a grocery list button

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

Scenario: Check that current link is active and results are different on different result pages
	When I am on the ImHungry Search Page
	And I enter "burger" in the search box
	And I enter "11" in the search number box
	And I enter "10" in the radius input field
	And press search
	Then there is a pagination div
	And there are "2" pagination links
	And the first pagination link is active
	And the first restaurant on the first results page is different from the first restaurant on second results page
	And the second pagination link is active

Scenario: Check that the number of pagination links per page is limited
	When I am on the ImHungry Search Page
	And I enter "chicken" in the search box
	And I enter "61" in the search number box
	And I enter "100" in the radius input field
	And press search
	Then I should see pagination links "1", "2", "3", "4", and "5"
	When I click on pagination link "3"
	Then I should see pagination links "1", "2", "3", "4", and "5"
	When I click on pagination link "4"
	Then I should see pagination links "2", "3", "4", "5", and "6"
	When I click on pagination link "5"
	Then I should see pagination links "3", "4", "5", "6", and "7"
	When I click on pagination link "6"
	Then I should see pagination links "3", "4", "5", "6", and "7"
	When I click on pagination link "7"
	Then I should see pagination links "3", "4", "5", "6", and "7"


