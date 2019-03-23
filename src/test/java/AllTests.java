import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import apitests.APITests;
import datatests.DataTests;
import servlettests.ServletTests;

/*
 *  Test suite for running all of the tests at once.
 */
@RunWith(Suite.class)
@SuiteClasses({ APITests.class, DataTests.class, ServletTests.class })
public class AllTests {
	
	@BeforeClass
	public static void test() {
		System.out.println("Starting tests");
	}

}
