require 'capybara'
require 'capybara/cucumber'
require 'rspec'


 Capybara.register_driver :chrome do |app|
  Capybara::Selenium::Driver.new(app, :browser => :chrome, :driver_path=>'C:\Users\Julia\Downloads\chromedriver_win32 (1)\chromedriver.exe')
end

 Capybara.default_driver = :chrome