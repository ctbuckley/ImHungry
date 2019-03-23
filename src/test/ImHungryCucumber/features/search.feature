Feature: Checking the I am hungry search page

Background:

	Given I am on the ImHungry Search Page

Scenario: Requirements for search Term field

	Then there is a searchTerm input field

Scenario: Requirements for search Number field

	Then there is a searchNumber input field
	And there is a default value 5 

Scenario: Requirements for Feed Me button
	
	Then there is a Feed Me button
	And there is a Feed Me! button
	And there is a red Feed Me button

Scenario: Requirements for the Emoji
	
	Then there is a Emoji Image
	And there is a grumpy Emoji Image

Scenario: Check the Transition to the results page

	When I enter "burger" in the search box
	And I enter "10" in the search number box
	And press search
	Then I should see results for "burger"

Scenario: Check the Transition while input wrong search number

	When I enter "burger" in the search box
	And I enter "-1" in the search number box
	And press search
	Then I should see search page title

Scenario: Check the background color
	
	Then the background color is smoke white

Scenario: Check the title of search page
	Then the title of search page is I'm Hungry


