package apitests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Vector;

import org.junit.Test;

import api.Scrapper;
import data.Recipe;

/*
 * Test for the Scrapper class, which scrapes recipe data from allrecipes.com. 
 */
public class ScrapperTest {

	/*
	 * Test to make sure that the parseTime function correctly parses a datetime String into seconds.
	 */
	@Test
	public void parseTimeTest() {
		int value = Scrapper.parseTime("PT1H");
		assertEquals(value, 60);
		
		value = Scrapper.parseTime("PT1H10M");
		assertEquals(value, 70);
		
		value = Scrapper.parseTime("PT1H5M");
		assertEquals(value, 65);
		
		value = Scrapper.parseTime("PT15M");
		assertEquals(value, 15);
		
		value = Scrapper.parseTime("PT5M");
		assertEquals(value, 5);
		
		value = Scrapper.parseTime("");
		assertEquals(value, -1);
		
		value = Scrapper.parseTime("PT");
		assertEquals(value, -1);
		
	}
	
	/*
	 * Test the get function will return the correct Recipe information for a url.
	 */
	@Test
	public void getTest() throws IOException {
		
		// This is necessary for complete coverage, even though the functions are supposed to be accessed statically.
		Scrapper sr = new Scrapper();
		
		String url = "https://www.allrecipes.com/recipe/228293/curry-stand-chicken-tikka-masala-sauce/";
		
		Recipe recipe = Scrapper.get(url);
		
		System.out.println("Results for recipe at " + url);
		System.out.println("Name: " + recipe.getName());
		System.out.println("Picture url: " + recipe.getPictureUrl());
		System.out.println("Prep Time: " + recipe.getPrepTime());
		System.out.println("Cook Time: " + recipe.getCookTime());
		System.out.println("Rating: " + recipe.getRating());
		System.out.println("Ingredients: " + recipe.getIngredients().size());
		for(String ingredient : recipe.getIngredients()) {
			System.out.println("    " + ingredient);
		}
		System.out.println("Instructions: " + recipe.getInstructions().size());
		for(String instruction : recipe.getInstructions()) {
			System.out.println("    " + instruction);
		}
		
	}
	
	/*
	 * Test that a search larger than the amount of recipes that can fit on one page will succeed.
	 */
	@Test
	public void searchTest() throws IOException {
		Vector<Recipe> recipes = Scrapper.search("chicken", 5);
		assertEquals(5, recipes.size());
		
		recipes = Scrapper.search("chicken", 21);
		assertEquals(21, recipes.size());
		
	}
	
	/*
	 * Tests requests which return less than the specified amount of results.
	 */
	@Test
	public void searchBadRequestTest() throws IOException {
		Vector<Recipe> recipes = Scrapper.search("qwertyuiop", 25);
		assertEquals(0, recipes.size());
				
	}
	
	/*
	 *  Tests a multi word search.
	 */
	@Test
	public void searchMultipleTermsTest() throws IOException {
		Vector<Recipe> recipes = Scrapper.search("shrimp curry", 2);
		assertEquals(2, recipes.size());
		
	}
	
	
}
