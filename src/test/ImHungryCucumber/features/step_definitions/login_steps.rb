Given(/^I am on the ImHungry Login Page$/) do
  visit "http://localhost:8080/FeedMe/jsp/login.jsp"
end

Then(/^there is a header that says I'm Hungry$/) do
	expect(page.find_by_id("hungryText"))
	expect(page.find_by_id("hungryText").native.text).to eq "I'm Hungry"
end

Then(/^there is a guest login button$/) do
	expect(page.find_by_id("guestLogInButton"))
	expect(page.find_by_id("guestLogInButton").native.text).to eq "Continue without signing in"
end

Then(/^there is a username and password field as well as a login and sign in button$/) do
	page.should have_selector("input[type=text][id=usernameInput][placeholder='Username']")
	page.should have_selector("input[type=password][id=passwordInput][placeholder='Password']")
	page.should have_selector("button[id=userLogInButton]")
	page.should have_selector("button[id=signUpButton]")
end

Then(/^there should be an invisible error field$/) do
  expect(page.find("#errorField", visible: false).native.css_value('visibility')).to eq('hidden')
end

When(/^I enter in "halfond" in the username field$/) do
	fill_in('usernameInput', with: "halfond")	
end

When(/^I enter in "documentation" in the password field$/) do
	fill_in('passwordInput', with: "documentation")	
end

When(/^I click on the sign up button$/) do
	find('#signUpButton').click
end

When(/^I return to the Log In Page$/) do
	visit 'http://localhost:8080/FeedMe/jsp/login.jsp'
end

When(/^I click on the log in button$/) do
	find('#userLogInButton').click
end

Then(/^I should be on the Search Page$/) do
	expect(page).to have_current_path('http://localhost:8080/FeedMe/jsp/search.jsp')
end


	