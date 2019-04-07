Given(/^I am on the Search Page$/) do
  visit "http://localhost:8080/FeedMe/jsp/search.jsp"
  fill_in('queryInput', :with => "pizza")
  fill_in('numResultsInput', :with => "1")
  fill_in('radiusInput', :with => "12")
  page.find_by_id("feedMeButton").click()
 end
 When(/^I add a restaurant to Favorite list$/) do
  page.find_by_id("Restaurant0").click()
  page.select 'Favorites', from: "dropDownBar"
  page.find_by_id("addToList").click()
  page.find_by_id("backToResults").click()
 end
 When(/^I add a recipe to Do Not Show list$/) do
  page.find_by_id("Recipe0").click()
  page.select 'Do Not Show', from: "dropDownBar"
  page.find_by_id("addToList").click()
  page.find_by_id("backToResults").click()
 end
 When(/^I go to Favorite list management page$/) do
  page.find_by_id("navbarDropdownMenuLink").click();
  page.find_by_id("fOptionButton").click();
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
  page.select 'Do Not Show', from: "dropDownBar"
  page.find_by_id("manageListButton").click()
 end
 When(/^I trash the recipe$/) do
  page.select 'Trash', from: "moveDropDown"
  page.find_by_id("moveButton").click()
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
  page.select 'To Explore', from: "dropDownBar"
  page.find_by_id("manageListButton").click()
 end    
 When(/^I click on the restaurant in list management page$/) do
  page.find_by_id("Restaurant0").click()
 end
 Then(/^I am in the Restaurant Details Page$/) do
  expect(page).to have_title("Restaurant Details")
 end