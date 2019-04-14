package datatests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 *  Test suite containing for the data structures in the data package.
 */
@RunWith(Suite.class)
@SuiteClasses({ RecipeTest.class, RestaurantTest.class, UserListTest.class,  DatabaseTests.class})
public class DataTests {

}
