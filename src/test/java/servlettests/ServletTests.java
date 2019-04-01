package servlettests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * Contains all of the tests for each of the servlets in the servlets package.
 */
@RunWith(Suite.class)
@SuiteClasses({ ListManagementPageServletTest.class, RecipeDetailsPageServletTest.class, 
	RestaurantDetailsPageServletTest.class, ResultsPageServletTest.class, SearchPageServletTest.class })
public class ServletTests {

}
