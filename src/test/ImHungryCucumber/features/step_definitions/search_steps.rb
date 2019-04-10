Given(/^I am on the ImHungry Search Page$/) do
  visit "https://localhost:8443/FeedMe/jsp/search.jsp"
end

Then(/^there is a query input field$/) do
  page.should have_selector("input[type=text][id=queryInput][placeholder='Enter Food']")
end


Then(/^there is a numResults input field$/) do
  page.should have_selector("input[type=text][id=numResultsInput]")
end

Then(/^there is a default value 5$/) do
  expect(page.find_by_id("numResultsInput").value).to eq "5"
end

Then(/^there is a Feed Me button$/) do
  page.should have_selector("button[id=feedMeButton]")
end

Then(/^there is a Emoji Image$/) do
  page.should have_selector("img[id=emoji]")
end

Then(/^there is a grumpy Emoji Image$/) do
  page.should have_selector("img[src='https://images.emojiterra.com/twitter/v11/512px/1f620.png']")
end

Then(/^there is a happy Emoji Image$/) do
  page.should have_selector("img[src='https://buildahead.com/wp-content/uploads/2017/02/happy-emoji-smaller.png']")

end

When(/^I enter "([^"]*)" in the search box$/) do |searchArg|
  fill_in('queryInput', :with => searchArg)
end

When(/^I enter "([^"]*)" in the search number box$/) do |searchNumArg|
  fill_in('numResultsInput', :with => searchNumArg)
end

When(/^press search$/) do
  page.find_by_id("feedMeButton").click()
end

Then(/^I should see results for "([^"]*)"$/) do |arg1|
  expect(page.find_by_id("resultsForText").native.text).to eq "Results For " + arg1
end

Then(/^there is a Feed Me! button$/) do
  expect(page.find_by_id("feedMeButton").native.text).to eq "Feed Me!"
end

Then(/^the title of search page is I'm Hungry$/) do
  page.should have_content("ImHungry")
end

Then(/^there is a logout button$/) do
  expect(page.find_by_id("userButton", visible: false))
end

Then(/^there is a radius input field$/) do
  expect(page.find_by_id("radiusInput"))
end

Then(/^I enter "([^"]*)" in the radius input field$/) do |radius|
  fill_in('radiusInput', :with => radius)
end

Then(/^I should be on the Search Page$/) do
  sleep(5)
	expect(page.current_url).to include('https://localhost:8443/FeedMe/jsp/search.jsp')
end

Then(/^I should be on the Search Page after servlet$/) do
  sleep(2)
	expect(page.current_url).to include('https://localhost:8443/FeedMe/search')
end

Then(/^there is a quick access list$/) do
  expect(page.find_by_id("quickAccessDropdown", visible: false))
end

Then (/^I should visit the search page$/) do
  sleep(1)
  visit "https://localhost:8443/FeedMe/jsp/search.jsp"
end

Then(/^I should see 1 history result$/) do
  expect(page.find_by_id("quickAccessResult0", visible: false))
end

Then(/^I should see 2 history results$/) do
  expect(page.find_by_id("quickAccessResult0", visible: false))
  expect(page.find_by_id("quickAccessResult1", visible: false))
end

Then(/^I should see a recipe error message$/) do
  expect(page.find("#errorMessageRecipe").native.css_value('display')).not_to eq('none')
end

Then(/^I should see a restaurant error message$/) do
  expect(page.find("#errorMessageRestaurant").native.css_value('display')).not_to eq('none')
end
