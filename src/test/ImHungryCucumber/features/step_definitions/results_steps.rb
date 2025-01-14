Given(/^I am on the ImHungry Results Page$/) do
  visit "https://localhost:8443/FeedMe/results?q=burger&n=5&radiusInput=3&feedMeButton=Feed+Me&fromSearch=true"
end

Then(/^there is a dropdown button$/) do
  expect(page.find_by_id("listName", visible: false))
end

Then(/^there is a favorites option$/) do
  expect(page.find_by_id("fOptionButton", visible: false))
end

Then(/^there is a to be explored option$/) do
  expect(page.find_by_id("tOptionButton", visible: false))
end

Then(/^there is a do not show option$/) do
  expect(page.find_by_id("dOptionButton", visible: false))
end

Then(/^there is a Manage Lists button$/) do
  expect(page.find_by_id("listName", visible: false))
end

Then(/^there is a Return to Search button$/) do
  expect(page.find_by_id("returntoSearch").native.text).to eq "ImHungry"
end

When(/^press return to search$/) do
  page.find_by_id("returntoSearch").click()
end

Then(/^I should see search page title$/) do
  page.should have_content("ImHungry")
end

Then(/^I should see Restaurant title$/) do
  expect(page.find_by_id("restaurantTitle").native.text).to eq "Restaurants"
end

Then(/^I should see Recipe title$/) do
  expect(page.find_by_id("recipeTitle").native.text).to eq "Recipes"
end

Then(/^there is the name of Restaurant list elements$/) do
  expect(page.find_by_id("restaurantName0"))
end

Then(/^there is the Stars of Restaurant list elements$/) do
  expect(page.find_by_id("restaurantRating0"))
end

Then(/^there is the minutes of Restaurant list elements$/) do
  page.should have_content("Min(s) Away")
end

Then(/^there is the address of Restaurant list elements$/) do
  expect(page.find_by_id("restaurantAddress0"))
end

Then(/^there is the price of Restaurant list elements$/) do
  expect(page.find_by_id("restaurantPrice0"))
end

Then(/^there is the name of Recipe list elements$/) do
  expect(page.find_by_id("recipeName0"))
end

Then(/^there is the Stars of Recipe list elements$/) do
  expect(page.find_by_id("recipeRating0"))
end

Then(/^there is the Cook Time of Recipe list elements$/) do
  page.should have_content("Cook Time:")
end

Then(/^there is the Prep Time of Recipe list elements$/) do
  page.should have_content("Prep Time:")
end

Then(/^there is a pagination div$/) do
  expect(page.find_by_id("page_bar"))
end

Then(/^there are "([^"]*)" pagination links$/) do |links|
  expect(page.all(".page-link").count).to eq (links.to_i+2)
end

When(/^the first restaurant on the first results page is different from the first restaurant on second results page$/) do
  $result1 = page.find_by_id("recipeName0").native.text
  page.find_by_id("paginationLink2").click()
  sleep(1)
  expect(page.find_by_id("recipeName0").native.text).not_to eq $result1
end

When(/^the first pagination link is active$/) do
  expect(page.find_by_id("paginationLink1").find(:xpath, '..')['class']).to have_content 'active'
  expect(page.find_by_id("paginationLink2").find(:xpath, '..')['class']).not_to have_content 'active'
end

Then(/^the second pagination link is active$/) do
  expect(page.find_by_id("paginationLink1").find(:xpath, '..')['class']).not_to have_content 'active'
  expect(page.find_by_id("paginationLink2").find(:xpath, '..')['class']).to have_content 'active'
end

When(/^I should see pagination links "([^"]*)", "([^"]*)", "([^"]*)", "([^"]*)", and "([^"]*)"$/) do |link1, link2, link3, link4, link5|
  expect(page.find_by_id("paginationLink" + link1))
  expect(page.find_by_id("paginationLink" + link2))
  expect(page.find_by_id("paginationLink" + link3))
  expect(page.find_by_id("paginationLink" + link4))
  expect(page.find_by_id("paginationLink" + link5))
end

When(/^I click on pagination link "([^"]*)"$/) do |link|
  page.find_by_id("paginationLink" + link).click()
end

When(/^I click on the pagination next button$/) do
  page.find_by_id("paginationNext").click()
end

When(/^I click on the pagination previous button$/) do
  page.find_by_id("paginationPrevious").click()
end

Then(/^the previous pagination link is disabled$/) do
  expect(page.find_by_id("paginationPrevious").find(:xpath, '..')['class']).to have_content 'disabled'
end

Then(/^the next pagination link is disabled$/) do
  expect(page.find_by_id("paginationNext").find(:xpath, '..')['class']).to have_content 'disabled'
end






