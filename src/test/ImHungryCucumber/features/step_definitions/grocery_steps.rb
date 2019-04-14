Then(/^there is a grocery list button$/) do
    sleep(0.5)
    expect(page.find_by_id("grocery_link_button", visible: false));
end