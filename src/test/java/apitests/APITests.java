package apitests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 *  Test suite containing all of the tests for the files in the api package.
 */
@RunWith(Suite.class)
@SuiteClasses({ GoogleDirectionsTest.class, GoogleImageSearchTest.class, ScrapperTest.class, YelpTest.class })
public class APITests {

}
