
require 'capybara'
require 'capybara/cucumber'
require 'rspec'
require 'selenium-webdriver'

Capybara.register_driver :chrome do |app|
	options = Selenium::WebDriver::Chrome::Options.new
	options.add_argument('--headless')
	options.add_argument('--disable-gpu')
	options.add_argument('--window-size=750,750')
    Capybara::Selenium::Driver.new(app, browser: :chrome, driver_path: './features/support/chromedriver.exe', options: options)
end

Capybara.default_driver = :chrome
