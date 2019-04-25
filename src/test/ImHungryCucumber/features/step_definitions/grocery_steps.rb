Then(/^there is a grocery list button$/) do
    sleep(0.5)
    expect(page.find_by_id("grocery_link_button", visible: false));
end

When(/^I view the first recipe$/) do
    page.find_by_id("Recipe0").click()
    $firstIngredient = page.find_by_id("ingredient0").native.text
end

Then(/^I view the first restaurant$/) do
    page.find_by_id("Restaurant0").click()
end

Then(/^I view the second recipe$/) do
    page.find_by_id("Recipe1").click()
end

Then(/^I add the first "([^"]*)" ingredients$/) do
    page.find_by_id("Recipe1").set(true)
end

Then(/^I click on the grocery link$/) do
    page.find_by_id("navToggler").click();
    page.find_by_id("grocery_link_button").click();
end

Then(/^I should see the grocery list title$/) do
    expect(page.find_by_id("groceryListTitle"))
end

Then(/^I should see the clear list button$/) do
    expect(page.find_by_id("clearListBtn"))
end

Then(/^I should see the log out link$/) do
    expect(page.find_by_id("userButton", visible: false))
end

Then(/^I should see the return to search link$/) do
    expect(page.find_by_id("returnToSearch"))
end

Then(/^I visit the search page$/) do
    visit "http://localhost:8080/FeedMe/jsp/search.jsp"
end

Then(/^I click on the first ingredient$/) do
    page.find_by_id("customCheck0").click();
end

Then(/^I click on the second ingredient$/) do
    page.find_by_id("customCheck1").click();
end

Then(/^I add selected ingredients to the grocery list$/) do
    page.find_by_id("confirmAddBtn").click();
end

Then(/^I should see the first ingredient in the grocery list$/) do
    expect(page).to have_content($firstIngredient)
end

Then(/^I remove the first ingredient from the grocery list$/) do
    page.find_by_id("removeBtn0").click();
end

Then(/^I should not see the first ingredient in the grocery list$/) do
    expect(page).not_to have_content($firstIngredient)
end

When(/^I add the first ingredient of the first recipe to the grocery list$/) do
    page.find_by_id("Recipe0").click()
    $firstIngredient = page.find_by_id("ingredient0").native.text
    page.find_by_id("customCheck0").click();
    page.find_by_id("confirmAddBtn").click();
end

Then(/^I expect the second ingredient to be checked$/) do
    expect(page.find("input#customCheck1")).to be_checked
    expect(page.find("input#customCheck0")).not_to be_checked
end
