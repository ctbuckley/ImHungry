Feature: Recipe Details Page
Background:

	Given I am on the Recipe Details Page

Scenario: User reads recipe information, and clicks printable version

	Then there is recipe name
	Then there is recipe picture
	Then there is prep time
	Then there is cook time
	Then there is ingredients
	Then there is intsructions
	Then there is Printable Version button
	Then there is Back to Results button
	Then there is dropdown box to select predefined lists
        Then dropdown has 4 options
        Then no list is selected in dropdown by default
	Then there is Add to List button
	When I click Printable Version button
	Then there is no Printable Version button
	Then there is no Back to Results button
	Then there is no dropdown box
	Then there is no Add to List button

Scenario: Check go back to results page

	When I click Back to Results button
	Then I see Results Page


