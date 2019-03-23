Given(/^I am on the ImHungry Results Page$/) do
  visit "http://localhost:8080/FeedMe/results?q=burger&n=5&feedMeButton=Feed+Me"
end

Then(/^there is a dropdown button$/) do
  page.should have_selector("select[id=listName]")
end

Then(/^there is a not selected option$/) do
  page.should have_selector("option[id=nOptionButton]")
end

Then(/^there is a favorites option$/) do
  page.should have_selector("option[id=fOptionButton]")
end

Then(/^there is a to be explored option$/) do
  page.should have_selector("option[id=tOptionButton]")
end

Then(/^there is a do not show option$/) do
  page.should have_selector("option[id=dOptionButton]")
end

Then(/^there is a Manage Lists button$/) do
  expect(page.find_by_id("addToList").native.text).to eq "Manage Lists"
end

Then(/^there is a Return to Search button$/) do
  expect(page.find_by_id("returntoSearch").native.text).to eq "Return to Search"
end

When(/^press return to search$/) do
  page.find_by_id("returntoSearch").click()
end

Then(/^I should see search page title$/) do
  page.should have_content("I'm Hungry")
end

Then(/^I should see Restaurant title$/) do
  expect(page.find_by_id("restaurantTitle").native.text).to eq "Restaurants"
end

Then(/^I should see Recipe title$/) do
  expect(page.find_by_id("recipeTitle").native.text).to eq "Recipes"
end

Then(/^there is the name of Restaurant list elements$/) do
  page.should have_content("Name:")
end

Then(/^there is the Stars of Restaurant list elements$/) do
  page.should have_content("Stars:")
end

Then(/^there is the minutes of Restaurant list elements$/) do
  page.should have_content("Minutes:")
end

Then(/^there is the address of Restaurant list elements$/) do
  page.should have_content("Address:")
end

Then(/^there is the price of Restaurant list elements$/) do
  page.should have_content("Price:")
end

Then(/^there is the name of Recipe list elements$/) do
  page.should have_content("Name:")
end

Then(/^there is the Stars of Recipe list elements$/) do
  page.should have_content("Stars:")
end

Then(/^there is the Cook Time of Recipe list elements$/) do
  page.should have_content("Cook Time:")
end

Then(/^there is the Prep Time of Recipe list elements$/) do
  page.should have_content("Prep Time:")
end

Then(/^the background color is smoke white$/) do
  page.should have_selector("body[style='background-color:whitesmoke;']")
end

When(/^press manage list button$/) do
  page.find_by_id("addToList").click()
end







