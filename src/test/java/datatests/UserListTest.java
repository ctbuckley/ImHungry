package datatests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import data.Recipe;
import data.Restaurant;
import data.UserList;

/*
 *  Tests for the UserList data structure.
 */
public class UserListTest {
	
	static Recipe recipe1;
	static Recipe recipe2;
	static Restaurant restaurant1;
	static Restaurant restaurant2;
	
	@Before
	public void initTest() {
		String name = "Good Food";
		String pictureUrl = "http://www.todayifoundout.com/wp-content/uploads/2017/11/rick-astley.png";
		double prepTime = 10;
		double cookTime = 25;
		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add("1 teaspoon ground ginger");
		ingredients.add("1 rack of lamb");
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("Throw in a pan.");
		instructions.add("Cook until done.");
		double rating = 4.5;
		
		recipe1 = new Recipe(name, pictureUrl, prepTime, cookTime, ingredients, instructions, rating);
		recipe2 = new Recipe("Not" + name, pictureUrl, prepTime, cookTime, ingredients, instructions, rating);
		
		restaurant1 = new Restaurant("A Good Restaurant", "https://www.mcdonalds.com/us/en-us.html", 1, "Everywhere", "(123)456-7890", 2.25, 5);
		restaurant2 = new Restaurant("A Bad Restaurant", "https://www.bk.com/", 2, "Almost everywhere", "(123)456-7896", 1.25, 50);
		
	}

	/*
	 * Test to make sure that add, contains, and remove work for both recipes and restaurants in expected situations.
	 */
	@Test
	public void testBasicFunctionality() {
	
		UserList list = new UserList();
		
		assertFalse(list.contains(restaurant1));
		assertFalse(list.contains(restaurant2));
		
		assertFalse(list.remove(restaurant1));
		assertFalse(list.remove(restaurant2));
		
		assertTrue(list.add(restaurant1));
		assertTrue(list.contains(restaurant1));
		assertTrue(list.remove(restaurant1));
		
		assertFalse(list.contains(recipe1));
		assertFalse(list.contains(recipe2));

		assertFalse(list.remove(recipe1));
		assertFalse(list.remove(recipe2));

		assertTrue(list.add(recipe1));
		assertTrue(list.contains(recipe1));
		assertTrue(list.remove(recipe1));

	}
	
	/*
	 * Test that the getRecipes and getRestaurants functions work as expected.
	 */
	@Test
	public void testGetArray() {
		
		UserList list = new UserList();
		
		ArrayList<Recipe> recipes = list.getRecipes();
		ArrayList<Restaurant> restaurants = list.getRestaurants();
		
		assertEquals(0, recipes.size());
		assertEquals(0, restaurants.size());
		
		assertTrue(list.add(recipe1));
		assertEquals(recipe1, recipes.get(0));
		assertEquals(1, recipes.size());
		assertEquals(0, restaurants.size());
		
		assertTrue(list.add(restaurant1));
		assertEquals(restaurant1, restaurants.get(0));
		assertEquals(1, recipes.size());
		assertEquals(1, restaurants.size());
		
		assertTrue(list.add(restaurant2));
		assertEquals(restaurant2, restaurants.get(1));
		assertEquals(1, recipes.size());
		assertEquals(2, restaurants.size());
		
		assertTrue(list.remove(restaurant1));
		assertEquals(restaurant2, restaurants.get(0));
		assertEquals(1, recipes.size());
		assertEquals(1, restaurants.size());
			
	}
	
	/*
	 * Test that the add will fail if the object is already in the UserList.
	 */
	@Test
	public void testAdd() {
		UserList list = new UserList();
		
		assertTrue(list.add(restaurant1));
		assertTrue(list.contains(restaurant1));
		
		assertFalse(list.add(restaurant1));
		assertTrue(list.contains(restaurant1));
		
		assertTrue(list.remove(restaurant1));
		assertFalse(list.contains(restaurant1));
		
		
		assertTrue(list.add(recipe1));
		assertTrue(list.contains(recipe1));
		
		assertFalse(list.add(recipe1));
		assertTrue(list.contains(recipe1));
		
		assertTrue(list.remove(recipe1));
		assertFalse(list.contains(recipe1));

	}
	
	/*
	 * Test incorrect classes being put into UserList functions.
	 */
	@Test
	public void testIncorrectClassType() {
		
		UserList list = new UserList();
		
		String bad = "Not a restaurant or recipe";
		
		assertFalse(list.add(bad));
		assertFalse(list.contains(bad));		
		assertFalse(list.remove(bad));
		
	}
	

}
