Given(/^I am on the Recipe Details Page$/) do
  visit "https://localhost:8443/FeedMe/jsp/search.jsp"
  fill_in('queryInput', :with => "pizza")
  fill_in('numResultsInput', :with => "1")
  fill_in('radiusInput', :with => "12")
  page.find_by_id("feedMeButton").click()
  page.find_by_id("Recipe0").click()
end
Then(/^there is recipe name$/) do
  expect(page.find_by_id('recipeName'))
end
Then(/^there is recipe picture$/) do
    expect(page.find('#recipePicture'));
end
Then(/^there is prep time$/) do
    page.should have_content("Prep Time");    
end

Then(/^there is cook time$/) do
    page.should have_content("Cook Time");
end

Then(/^there is ingredients$/) do
    page.should have_content("Ingredients");
end

Then(/^there is intsructions$/) do
    page.should have_content("Instructions");
end

Then(/^there is a checkbox for each instruction$/) do
    page.all('#ingredient_list_item').each do |el|
      expect(el.find("input[type='checkbox']"))
    end
end