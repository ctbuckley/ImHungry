Then(/^there is a grocery list button$/) do
    expect(page.find_by_id('grocery_link_button'));
end