Given(/^I am on the Search Page$/) do
  visit "https://localhost:8443/FeedMe/jsp/search.jsp"
  fill_in('queryInput', :with => "pizza")
  fill_in('numResultsInput', :with => "3")
  fill_in('radiusInput', :with => "12")
  page.find_by_id("feedMeButton").click()
 end
 When(/^I add a restaurant to Favorite list$/) do
  sleep(1)
  page.find_by_id("Restaurant0").click()
  page.select 'Favorites', from: "dropDownBar"
  sleep(1)
  page.find_by_id("addToList").click()
  sleep(1)
  page.find_by_id("backToResults").click()
 end

 When(/^I add a restaurant to Do Not Show list$/) do
  sleep(1)
  page.find_by_id("Restaurant0").click()
  page.select 'Do Not Show', from: "dropDownBar"
  sleep(1)
  page.find_by_id("addToList").click()
  sleep(1)
  page.find_by_id("backToResults").click()
 end

 When(/^I add a restaurant to To Explore list$/) do
  sleep(1)
  page.find_by_id("Restaurant0").click()
  page.select 'To Explore', from: "dropDownBar"
  sleep(1)
  page.find_by_id("addToList").click()
  sleep(1)
  page.find_by_id("backToResults").click()
 end

 When(/^I add a recipe to Favorite list$/) do
  sleep(1)
  page.find_by_id("Recipe0").click()
  page.select 'Favorites', from: "dropDownBar"
  page.find_by_id("addToList").click()
  page.find_by_id("backToResults").click()
 end

 When(/^I add the first recipe to Do Not Show list$/) do
  sleep(1)
  page.find_by_id("Recipe0").click()
  page.select 'Do Not Show', from: "dropDownBar"
  page.find_by_id("addToList").click()
 end

 When(/^I add a recipe to To Explore list$/) do
  sleep(1)
  page.find_by_id("Recipe0").click()
  page.select 'To Explore', from: "dropDownBar"
  page.find_by_id("addToList").click()
  page.find_by_id("backToResults").click()
 end

 When(/^I add the second restaurant to Favorite list$/) do
  sleep(1)
  page.find_by_id("Restaurant1").click()
  page.select 'Favorites', from: "dropDownBar"
  page.find_by_id("addToList").click()
  page.find_by_id("backToResults").click()
 end

 When(/^I add the second recipe to Favorite list$/) do
  sleep(1)
  page.find_by_id("Recipe1").click()
  page.select 'Favorites', from: "dropDownBar"
  page.find_by_id("addToList").click()
  page.find_by_id("backToResults").click()
 end

 When(/^I add a recipe to Do Not Show list$/) do
  page.find_by_id("Recipe2").click()
  page.select 'Do Not Show', from: "dropDownBar"
  page.find_by_id("addToList").click()
  page.find_by_id("backToResults").click()
 end

 When(/^I go to Favorite list management page$/) do
  page.find_by_id("navToggler").click();
  page.find_by_id("navbarDropdownMenuLink").click();
  page.find_by_id("fOptionButton").click();
  sleep(1)
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
  page.should have_selector("select[id=moveDropDownRest0]")
 end
 Then(/^there is Restaurant Move button$/) do
  page.should have_selector("button[id=moveButtonRest0]")
 end

 Then(/^there is Recipe Move button$/) do
  page.should have_selector("button[id=moveButton]")
 end

 When(/^I move the restaurant to To Explore list$/) do
  page.select 'To Explore', from: "moveDropDownRest0"
  page.find_by_id("moveButtonRest0").click()
 end
 When(/^I go to Do Not Show list management page$/) do
  page.find_by_id("navToggler").click();
  page.find_by_id("navbarDropdownMenuLink").click();
  page.find_by_id("dOptionButton").click();
 end
 When(/^I trash the recipe$/) do
  page.select 'Trash', from: "moveDropDownRec0"
  page.find_by_id("moveButtonRec0").click()
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
  expect(page).to have_no_content("Cook Time");
 end
 Then(/^there is no prep time$/) do
  expect(page).to have_no_content("Prep Time");
 end
 When(/^I go to To Explore list management page$/) do
  page.find_by_id("navToggler").click();
  page.find_by_id("navbarDropdownMenuLink").click();
  sleep(1)
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

Then(/^the Recipe and Restaurant titles should not be visible$/) do
  expect(page).to have_no_content("Restaurants");
  expect(page).to have_no_content("Recipes");
end


