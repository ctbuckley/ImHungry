Given(/^I am on the Search Page$/) do
  visit "http://localhost:8080/FeedMe/jsp/search.jsp"
  fill_in('queryInput', :with => "pizza")
  fill_in('numResultsInput', :with => "2")
  fill_in('radiusInput', :with => "12")
  page.find_by_id("feedMeButton").click()
 end
 When(/^I add a restaurant to Favorite list$/) do
  sleep(3)
  page.find_by_id("Restaurant0").click()
  page.select 'Favorites', from: "dropDownBar"
  page.find_by_id("addToList").click()
  sleep(5)
  page.find_by_id("backToResults").click()
 end

 When(/^I add a recipe to Favorite list$/) do
  sleep(3)
  page.find_by_id("Recipe0").click()
  page.select 'Favorites', from: "dropDownBar"
  page.find_by_id("addToList").click()
  sleep(5)
  page.find_by_id("backToResults").click()
 end

 When(/^I add the second restaurant to Favorite list$/) do
  sleep(5)
  page.find_by_id("Restaurant1").click()
  page.select 'Favorites', from: "dropDownBar"
  sleep(5)
  page.find_by_id("addToList").click()
  sleep(5)
  page.find_by_id("backToResults").click()
 end

 When(/^I add the second recipe to Favorite list$/) do
  sleep(5)
  page.find_by_id("Recipe1").click()
  page.select 'Favorites', from: "dropDownBar"
  sleep(5)
  page.find_by_id("addToList").click()
  sleep(5)
  page.find_by_id("backToResults").click()
 end

 When(/^I add a recipe to Do Not Show list$/) do
  page.find_by_id("Recipe0").click()
  page.select 'Do Not Show', from: "dropDownBar"
  page.find_by_id("addToList").click()
  sleep(5)
  page.find_by_id("backToResults").click()
 end

 When(/^I go to Favorite list management page$/) do
  page.find_by_id("navToggler").click();
  page.find_by_id("navbarDropdownMenuLink").click();
  page.find_by_id("fOptionButton").click();
  sleep(5)
 end

 Then(/^there is restaurant stars$/) do
  expect(page.find_by_id("innerRestaurantRating0"))
 end

 Then(/^there is recipe stars$/) do
  expect(page.find_by_id("innerRecipeRating0"))
 end

 Then(/^there is minutes$/) do
  expect(page.find_by_id("restaurantDistance0"))
 end
 Then(/^there is address$/) do
  expect(page.find_by_id("restaurantAddress0"))
 end
 
 Then(/^there is price$/) do
  expect(page.find_by_id("restaurantPrice0"))
 end
 Then(/^there is Manage List button$/) do
  page.should have_selector("button[id=manageListButton]")
 end
 Then(/^there is Return to Search button$/) do
  page.should have_selector("button[id=returnToSearch]")
 end
 Then(/^there is move dropdown box to select predefined list$/) do
  page.should have_selector("select[id=moveDropDown]")
 end
 Then(/^there is Move button$/) do
  page.should have_selector("button[id=moveButton]")
 end
 When(/^I move the restaurant to To Explore list$/) do
  page.select 'To Explore', from: "moveDropDown"
  page.find_by_id("moveButton").click()
 end
 When(/^I go to Do Not Show list management page$/) do
  page.find_by_id("navToggler").click();
  page.find_by_id("navbarDropdownMenuLink").click();
  page.find_by_id("dOptionButton").click();
 end
 When(/^I trash the recipe$/) do
  page.select 'Trash', from: "moveDropDown"
  page.find_by_id("moveButton").click()
  sleep(5)
 end
 Then (/^there is a recipe name in the card$/) do
  page.find_by_id("recipeName0")
 end
 Then(/^there is no recipe name$/) do
  expect(page).to have_no_content("Pear and Gorgonzola Cheese Pizza");
 end
 Then(/^there is no stars$/) do
  expect(page).to have_no_content("Stars");
 end
 Then(/^there is no cook time$/) do
  sleep(5)
  expect(page).to have_no_content("Cook Time");
 end
 Then(/^there is no prep time$/) do
  expect(page).to have_no_content("Prep Time");
 end
 When(/^I go to To Explore list management page$/) do
  page.find_by_id("navToggler").click();
  page.find_by_id("navbarDropdownMenuLink").click();
  sleep(5)
  page.find_by_id("tOptionButton").click();
 end 
 When(/^I click on the restaurant in list management page$/) do
  page.find_by_id("Restaurant0").click()
 end
 Then(/^I am in the Restaurant Details Page$/) do
  expect(page).to have_title("Restaurant Details")
 end
# https://gist.github.com/dwt/1406218
 def drag_to(source, target)
  builder = page.driver.browser.action
  source = source.native
  target = target.native
  
  builder.click_and_hold source
  builder.move_to        target, 1, 11
  builder.move_to        target
  builder.release        target
  builder.perform
end

Then(/^I move the first restaurant to the second position$/) do
  drag_to find('#Restaurant1'), find('#Restaurant0')
end

Then(/^I move the first recipe to the second position$/) do
  drag_to find('#Recipe1'), find('#Recipe0')
end

Then(/^the second restaurant should be first in the list$/) do
    expect(first('.restaurant_cont')[:id]).to eq "Restaurant1"
end

Then(/^the second recipe should be first in the list$/) do
  expect(first('.recipe_cont')[:id]).to eq "Recipe1"
end


