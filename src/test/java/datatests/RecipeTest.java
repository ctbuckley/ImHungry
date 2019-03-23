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
 *  Test for the Recipe data structure.
 */
public class RecipeTest {

	/*
	 *  Test that the Recipe can be constructed and correctly stores necessary information.
	 */
	@Test
	public void testConstructor() {
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
		
		Recipe recipe = new Recipe(name, pictureUrl, prepTime, cookTime, ingredients, instructions, rating);
		
		assertEquals(name, recipe.getName());
		assertEquals(pictureUrl, recipe.getPictureUrl());
		assertEquals(prepTime, recipe.getPrepTime(), 0);
		assertEquals(cookTime, recipe.getCookTime(), 0);
		assertEquals(ingredients, recipe.getIngredients());
		assertEquals(instructions, recipe.getInstructions());
		assertEquals(rating, recipe.getRating(), 0);
		
	}
	
	/*
	 * Test that the compares functions will correctly compare Recipe objects when calling Collections.sort().
	 */
	@Test
	public void testCompare() {
		
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
		
		Recipe recipe1 = new Recipe(name, pictureUrl, prepTime, cookTime, ingredients, instructions, rating);
		Recipe recipe2 = new Recipe(name, pictureUrl, prepTime + 5, cookTime, ingredients, instructions, rating);
		Recipe recipe3 = new Recipe(name, pictureUrl, prepTime, cookTime, ingredients, instructions, rating);
		Recipe recipe4 = new Recipe(name, pictureUrl, prepTime - 5, cookTime, ingredients, instructions, rating);
		
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		recipes.add(recipe1);
		recipes.add(recipe2);
		recipes.add(recipe3);
		recipes.add(recipe4);
		
		assertEquals(recipe1, recipes.get(0));
		assertEquals(recipe2, recipes.get(1));
		assertEquals(recipe3, recipes.get(2));
		assertEquals(recipe4, recipes.get(3));
		
		Collections.sort(recipes);
		
		assertEquals(recipe4, recipes.get(0));
		assertEquals(recipe1, recipes.get(1));
		assertEquals(recipe3, recipes.get(2));
		assertEquals(recipe2, recipes.get(3));
		
	}
	
	/*
	 * Test that the equals functions will correctly compare Recipe objects when calling contains. Also makes sure that Restaurants and Recipes cannot be compared.
	 */
	@Test
	public void testEquals() {
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
		
		Recipe recipe1 = new Recipe(name, pictureUrl, prepTime, cookTime, ingredients, instructions, rating);
		Recipe recipe2 = new Recipe("The Best " + name, pictureUrl, prepTime + 5, cookTime, ingredients, instructions, rating);

		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		recipes.add(recipe1);

		assertTrue(recipes.contains(recipe1));
		assertFalse(recipes.contains(recipe2));
		
		String websiteUrl = "https://www.mcdonalds.com/us/en-us.html";
		int price = 1;
		String address = "Everywhere";
		String phoneNumber = "(123)456-7890";
		int drivingTime = 5;
		Restaurant restaurant1 = new Restaurant(name, websiteUrl, price, address, phoneNumber, rating, drivingTime);
		
		assertFalse(recipe1.equals(restaurant1));
		assertFalse(recipes.contains(restaurant1));
		
	}

}
