package apitests;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Vector;

import org.junit.Test;

import api.AccessYelpAPI;
import data.Restaurant;

/*
 * Tests for the AccessYelpAPI class.
 */
public class YelpTest {


	/*
	 *  Test to make sure that the Restaurants will be returned correctly with multiple words in the input.
	 */
	@Test
	public void testMultipleWordInput() throws IOException {
		
		// This is necessary for complete coverage, even though the functions are supposed to be accessed statically.
		AccessYelpAPI y = new AccessYelpAPI();
		
		int count = 4;
		Vector<Restaurant> arr = AccessYelpAPI.YelpRestaurantSearch("mexican chicken", count);
		assertEquals(count, arr.size());
		
		for (int i = 0; i < count; i++) {
			System.out.println(i);
			System.out.println("name: " + arr.get(i).getName());
			System.out.println("driveTime: " + arr.get(i).getDrivingTime());
		}
	}
	
	/*
	 *  Test to make sure that no errors will be thrown if there are no results from the input.
	 */
	@Test
	public void testBadInput() throws IOException {
		int count = 4;
		Vector<Restaurant> arr = AccessYelpAPI.YelpRestaurantSearch("qwertyuioiuytrewwdc", count);
		assertEquals(0, arr.size());
		
	}
	
	/*
	 *  Test to make sure that enough Restaurants will be returned 
	 *  if the requested amount is more than the limit of the API for one query.
	 */
	@Test
	public void testLargeInput() throws IOException {
		int count = 55;
		Vector<Restaurant> arr = AccessYelpAPI.YelpRestaurantSearch("pizza", count);
		assertEquals(count, arr.size());
		
	}
	
	/*
	 * Test to make sure that a small radius will correctly truncate the results of the search.
	 */
	@Test
	public void testSmallRadius() throws IOException{
		Vector<Restaurant> arr = AccessYelpAPI.YelpRestaurantSearch("pizza", 5, 0);
		assertEquals(0, arr.size());
	}
	
	/*
	 * Test to make sure that a large radius will correctly truncate the results of the search.
	 */
	@Test
	public void testLargeRadius() throws IOException{
		Vector<Restaurant> arr = AccessYelpAPI.YelpRestaurantSearch("pizza", 5, 50000);
		assertEquals(0, arr.size());
	}

}
