package datatests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import data.Recipe;
import data.Restaurant;

/*
 *  Tests for the Restaurant data structure.
 */
public class RestaurantTest {
	
	/*
	 *  Test that the Restaurant can be constructed and correctly stores necessary information.
	 */
	@Test
	public void testConstructor() {
		
		String name = "A Good Restaurant";
		String websiteUrl = "https://www.mcdonalds.com/us/en-us.html";
		int price = 1;
		String address = "Everywhere";
		String phoneNumber = "(123)456-7890";
		double rating = 2.25;
		int drivingTime = 5;
		
		Restaurant restaurant = new Restaurant(name, websiteUrl, price, address, phoneNumber, rating, drivingTime);
		
		assertEquals(name, restaurant.getName());
		assertEquals(websiteUrl, restaurant.getWebsiteUrl());
		assertEquals(price, restaurant.getPrice());
		assertEquals(address, restaurant.getAddress());
		assertEquals(phoneNumber, restaurant.getPhoneNumber());
		assertEquals(rating, restaurant.getRating(), 0);
		assertEquals(drivingTime, restaurant.getDrivingTime());
		
	}
	
	/*
	 * Test that the compares functions will correctly compare Restaurant objects when calling Collections.sort().
	 */
	@Test
	public void testCompare() {
		
		String name = "A Good Restaurant";
		String websiteUrl = "https://www.mcdonalds.com/us/en-us.html";
		int price = 1;
		String address = "Everywhere";
		String phoneNumber = "(123)456-7890";
		double rating = 2.25;
		int drivingTime = 5;
		
		Restaurant restaurant1 = new Restaurant(name, websiteUrl, price, address, phoneNumber, rating, drivingTime);
		Restaurant restaurant2 = new Restaurant(name, websiteUrl, price, address, phoneNumber, rating, drivingTime - 5);
		Restaurant restaurant3 = new Restaurant(name, websiteUrl, price, address, phoneNumber, rating, drivingTime + 5);
		Restaurant restaurant4 = new Restaurant(name, websiteUrl, price, address, phoneNumber, rating, drivingTime);
		
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		restaurants.add(restaurant1);
		restaurants.add(restaurant2);
		restaurants.add(restaurant3);
		restaurants.add(restaurant4);
		
		assertEquals(restaurant1, restaurants.get(0));
		assertEquals(restaurant2, restaurants.get(1));
		assertEquals(restaurant3, restaurants.get(2));
		assertEquals(restaurant4, restaurants.get(3));
		
		Collections.sort(restaurants);
		
		assertEquals(restaurant2, restaurants.get(0));
		assertEquals(restaurant1, restaurants.get(1));
		assertEquals(restaurant4, restaurants.get(2));
		assertEquals(restaurant3, restaurants.get(3));
		
		
	}
	
	/*
	 * Test that the equals functions will correctly compare Restaurant objects when calling contains. Also makes sure that Restaurants and Recipes cannot be compared.
	 */
	@Test
	public void testEquals() {
		
		String name = "A Good Restaurant";
		String websiteUrl = "https://www.mcdonalds.com/us/en-us.html";
		int price = 1;
		String address = "Everywhere";
		String phoneNumber = "(123)456-7890";
		double rating = 2.25;
		int drivingTime = 5;
		
		Restaurant restaurant1 = new Restaurant(name, websiteUrl, price, address, phoneNumber, rating, drivingTime);
		Restaurant restaurant2 = new Restaurant("An Unhealthy and " + name, websiteUrl, price, address, phoneNumber, rating, drivingTime - 5);
		
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		restaurants.add(restaurant1);

		assertTrue(restaurants.contains(restaurant1));
		assertFalse(restaurants.contains(restaurant2));
		
		String pictureUrl = "http://www.todayifoundout.com/wp-content/uploads/2017/11/rick-astley.png";
		double prepTime = 10;
		double cookTime = 25;
		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add("1 teaspoon ground ginger");
		ingredients.add("1 rack of lamb");
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("Throw in a pan.");
		instructions.add("Cook until done.");
		Recipe recipe1 = new Recipe(name, pictureUrl, prepTime, cookTime, ingredients, instructions, rating);

		assertFalse(restaurant1.equals(recipe1));
		assertFalse(restaurants.contains(recipe1));
	}

}
