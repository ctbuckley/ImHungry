
require 'capybara'
require 'capybara/cucumber'
require 'rspec'
require 'selenium-webdriver'

Capybara.register_driver :chrome do |app|
	capabilities = Capybara::Selenium::WebDriver::Remote::Capabilities.chrome(
	  chromeOptions: { args: %w(headless disable-gpu) }
	)
    Capybara::Selenium::Driver.new(app, :browser => :chrome, :driver_path=>'./features/support/chromedriver', :desired_capabilities => capabilities)
end

Capybara.default_driver = :chrome
