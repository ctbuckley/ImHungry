Given(/^I am on the Restaurant Details Page$/) do
  visit "http://localhost:8080/FeedMe/jsp/search.jsp"
  fill_in('queryInput', :with => "pizza")
  fill_in('numResultsInput', :with => "1")
  fill_in('radiusInput', :with => "10000")
  page.find_by_id("feedMeButton").click()
  page.find_by_id("Restaurant0").click()
end

Then(/^there is restaurant name$/) do
    page.should have_content("California Pizza Kitchen");
end

Then(/^there is restaurant address$/) do
    page.should have_content("Address");
end

Then(/^there is phone number$/) do
    page.should have_content("Phone Number");
end

Then(/^there is website link$/) do
    page.should have_content("Website Address");
end

Then(/^there is Printable Version button$/) do
    page.should have_selector("button[id=printButton]");
end

Then(/^there is Back to Results button$/) do
    page.should have_selector("button[id=backToResults]");
end

Then(/^there is dropdown box to select predefined lists$/) do
    page.should have_selector("select[id=dropDownBar]");
end
Then(/^dropdown has 4 options$/) do
    page.find_by_id("dropDownBar").should have_content("Favorites");
    page.find_by_id("dropDownBar").should have_content("To Explore");
    page.find_by_id("dropDownBar").should have_content("Do Not Show");
    page.find_by_id("dropDownBar").should have_content("select an option");
end
Then(/^no list is selected in dropdown by default$/) do
    expect(page).to have_select('dropDownBar', selected: '-- select an option --');
end
Then(/^there is Add to List button$/) do
    page.should have_selector("button[id=addToList]");
end

When(/^I click Printable Version button$/) do
    page.find_by_id("printButton").click()
end

Then(/^there is no Printable Version button$/) do
    expect(page).not_to have_selector("button[id=printButton]");
end
Then (/^there is no Back to Results button$/) do
    expect(page).not_to have_selector("button[id=backToResults]");
end
Then(/^there is no dropdown box$/) do
    expect(page).not_to have_selector("button[id=dropDownBar]");
end
Then(/^there is no Add to List button$/) do
    expect(page).not_to have_selector("button[id=addToList]");
end

When(/^I click Back to Results button$/) do
   page.find_by_id("backToResults").click();
end

Then(/^I see Results Page$/) do
   expect(page).to have_title("pizza")
end
