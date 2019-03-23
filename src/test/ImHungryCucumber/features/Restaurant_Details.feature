Feature: Restaurant Details Page
Background:

	Given I am on the Restaurant Details Page

Scenario: User reads restaurant information, clicks printable version, and goes back to Results Page

	Then there is restaurant name
	Then there is restaurant address
	Then there is phone number
	Then there is website link
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
	When I go back
	And click Back to Results button
	Then I see Results Page


