Given(/^I am on the Recipe Details Page$/) do
  visit "localhost:8080/FeedMe/jsp/search.jsp"
  fill_in('userInput', :with => "pizza")
  fill_in('searchTermTest', :with => "1")
  page.find_by_id("feedMeButton").click()
  page.find_by_id("Recipe0").click()
end
Then(/^there is recipe name$/) do
  page.should have_content("Pear and Gorgonzola Cheese Pizza");
end
Then(/^there is recipe picture$/) do
    expect(page.find('#recipePicture')['src']).to have_content('https://images.media-allrecipes.com/userphotos/560x315/2258878.jpg');
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
