Feature: Checking the I am hungry search page

Background:
	
	Given I am logged in
	Given I am on the ImHungry Search Page

Scenario: Requirements for search Term field

	Then there is a query input field

Scenario: Requirements for search Number field

	Then there is a numResults input field
	And there is a default value 5 

Scenario: Requirements for Feed Me button
	
	Then there is a Feed Me button
	And there is a Feed Me! button

Scenario: Requirements for the Emoji
	
	Then there is a Emoji Image
	And there is a grumpy Emoji Image

Scenario: Check the Transition to the results page

	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	Then I should see results for "burger"

Scenario: Check the Transition while input wrong search number

	When I enter "burger" in the search box
	And I enter "-1" in the search number box
	And I enter "2" in the radius input field
	And press search
	Then I should be on the Search Page

Scenario: Check the title of search page
	Then the title of search page is I'm Hungry

Scenario: Requirements for Logout Button 
	Then there is a logout button

Scenario: Requirements for radius input field
	Then there is a radius input field

Scenario: Check the transition with good radius input 
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	Then I should see results for "burger"

Scenario: Check the transition with bad radius input 
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "-1" in the radius input field
	And press search
	Then I should be on the Search Page

Scenario: Check that the past searches dropdown has no results
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	Then there is not a quick access list

Scenario: Check that the past searches dropdown has one result
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I should visit the search page
	And I enter "ramen" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	Then I should see 1 history result

Scenario: Check that the past searches dropdown has two results
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I should visit the search page
	And I enter "ramen" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I should visit the search page
	And I enter "chicken" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	Then I should see 2 history results
	When I click on the first item in the quick access list
	Then I should see results for "ramen"

Scenario: Check that an error message appears when no restaurants found
	When I enter "halfond" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	Then I should see a recipe error message
	And I should see a restaurant error message

Scenario: Check that there is a horizontal scroll bar for the past searches
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I should visit the search page
	And I enter "ramen" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I should visit the search page
	And I enter "chicken" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I should visit the search page
	And I enter "cookie" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	Then I need to scroll to see the third search history item