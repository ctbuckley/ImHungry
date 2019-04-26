
Given(/^I am logged in$/) do
  visit "http://localhost:8080/FeedMe/jsp/login.jsp"
  if defined?($current_user) == nil
  	$current_user = 0
  	puts "First user"
  else
  	$current_user += 1
  end
  username = "user" + $current_user.to_s
  puts username
  fill_in('usernameInput', with: username)
  fill_in('passwordInput', with: "password")	
  find('#signUpButton').click
  sleep(2)
end

When(/^I log out and log back in as the same user$/) do
	sleep(5)
	page.find_by_id("navToggler").click();
    page.find_by_id('userButton').click();
	username = "user" + $current_user.to_s
	fill_in('usernameInput', with: username)
  	fill_in('passwordInput', with: "password")
	find('#userLogInButton').click
end

When(/^I search for "([^"]*)" with "([^"]*)" results and a radius of "([^"]*)" miles$/) do |searchArg, numResultsArg, radiusArg|
	fill_in('queryInput', :with => searchArg)
	fill_in('numResultsInput', :with => numResultsArg)
	fill_in('radiusInput', :with => radiusArg)
	page.find_by_id("feedMeButton").click()
	expect(page.find_by_id("resultsForText").native.text).to eq "Results For " + searchArg
end

Given(/^I am on the ImHungry Login Page$/) do
  visit "http://localhost:8080/FeedMe/jsp/login.jsp"
end

Then(/^there is a header that says I'm Hungry$/) do
	expect(page.find_by_id("hungryText"))
	expect(page.find_by_id("hungryText").native.text).to eq "ImHungry"
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
  expect(page.find("#errorField", visible: false).native.css_value('display')).to eq('none')
end

When(/^I enter in "([^"]*)" in the username field$/) do |username|
	fill_in('usernameInput', with: username)	
end

When(/^I enter in "([^"]*)" in the password field$/) do |password|
	fill_in('passwordInput', with: password)	
end

When(/^I click on the sign up button$/) do
	find('#signUpButton').click
	sleep(0.5)
end

When(/^I return to the Log In Page$/) do
	sleep(0.5)
	visit 'http://localhost:8080/FeedMe/jsp/login.jsp'
end

When(/^I click on the log in button$/) do
	find('#userLogInButton').click
	sleep(0.5)
end

Then(/^I should be on the Login Page and I should see an error message$/) do
	expect(page.find("#errorField").native.css_value('display')).not_to eq('none')
	expect(page.current_url).to include('http://localhost:8080/FeedMe/jsp/login')
end

Then(/^I should be on the login page$/) do
	expect(page.current_url).to include('http://localhost:8080/FeedMe/jsp/login')
end

When(/^I click on the guest log in button$/) do
  find('#guestLogInButton').click
end

Then(/^I click on the log out button$/) do
	page.find_by_id("navToggler").click();
    page.find_by_id('userButton').click
end