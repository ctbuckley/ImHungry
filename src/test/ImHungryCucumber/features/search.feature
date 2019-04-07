Feature: Checking the I am hungry search page

Background:

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

Scenario: Check that a quick access dropdown exists
	Then there is a quick access list

Scenario: Requirements for Logout Button 
	Then there is a logout button

Scenario: Requirements for radius input field
	Then there is a radius input field

Scenario: Check the transition with radius input 
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	Then I should see results for "burger"

Scenario: Check the transition with radius input 
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "-1" in the radius input field
	And press search
	Then I should be on the Search Page

Scenario: Requirements for Quick Access List for Past Searches
	Then there is a quick access list

Scenario: Check that the past searches dropdown has one result
	When I return to the Log In Page
	And I should be on the login page
	And I enter in "testUsername6" in the username field
	And I enter in "testPassword6" in the password field
	And I click on the sign up button
	When I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And press return to search
	Then I should see 1 history result

Scenario: Check that the past searches dropdown has two result
	When I return to the Log In Page
	And I should be on the login page
	And I enter in "testUsername7" in the username field
	And I enter in "testPassword7" in the password field
	And I click on the sign up button
	And I enter "burger" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And I should visit the search page
	And I enter "ramen" in the search box
	And I enter "1" in the search number box
	And I enter "2" in the radius input field
	And press search
	And press return to search
	Then I should see 2 history results