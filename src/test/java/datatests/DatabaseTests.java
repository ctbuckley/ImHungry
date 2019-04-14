package datatests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Database;
import data.Recipe;
import data.Restaurant;
import data.SearchItem;

import static org.junit.Assert.assertEquals;

public class DatabaseTests {

	Database db;
	int userID;

	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		db = new Database();
		db.insertUserintoUsers("testUser", "password");
		ResultSet rs =  db.getUserfromUsers("testUser");
		rs.next();
		userID = rs.getInt("userID");
	}
	
	@Test
	public void databaseUserTest() throws Exception {
		
		//insert new user
		db.insertUserintoUsers("testValidNewUser", "hashedPass");	
		
		//get the new user by their username
		ResultSet rs = db.getUserfromUsers("testValidNewUser");
		
		assertEquals(true, rs.next());
	    
	    //Remove the user so that this test works next time
  		rs = null;
  		rs = db.getUserfromUsers("testValidNewUser");
  		rs.next();
		int uID = rs.getInt("userID");
		db.deleteUserfromUsers(uID);
		
		//verify that this is empty now
		rs = db.getUserfromUsers("testValidNewUser");
		assertEquals(false, rs.next());
		
	}	
	
	@Test
	public void databaseSearchHistoryTest() throws ClassNotFoundException, SQLException {
		
		//insert two search queries into the database for the user
		int searchID1 = db.insertQueryintoSearchHistory(userID, "chicken", 4, 10000);
		int searchID2 = db.insertQueryintoSearchHistory(userID, "apple", 5, 20000);
		
		
		//insert a different user
		db.insertUserintoUsers("testValidNewUser", "hashedPass");	
		//get the new user by their username
		ResultSet rs = db.getUserfromUsers("testValidNewUser");
		rs.next();
		int uID = rs.getInt("userID");
		//insert a random query for a different user
		int searchID3 = db.insertQueryintoSearchHistory(uID, "other", 1, 100);
		
		//get all search queries for the user
		ArrayList<SearchItem> results = db.getSearchItemfromSearch(userID);
		
		assertEquals(2, results.size());
		
		SearchItem chicken = results.get(0);
		SearchItem apple = results.get(1);
		
		assertEquals(chicken.numResults, 4);
		assertEquals(apple.numResults, 5);
		
		assertEquals(chicken.searchQuery, "chicken");
		assertEquals(apple.searchQuery, "apple");
		
		assertEquals(chicken.radius, 10000);
		assertEquals(apple.radius, 20000);
		
		assertEquals(chicken.userID, userID);
		assertEquals(apple.userID, userID);
	
		//delete them so this test passes again next time
		db.deleteQueryfromSearchHistory(searchID1);
		db.deleteQueryfromSearchHistory(searchID2);
		db.deleteQueryfromSearchHistory(searchID3);
		
		//Remove the other user so that this test works next time
		db.deleteUserfromUsers(uID);
	}
	
	@Test
	public void noSearchesTest() throws ClassNotFoundException, SQLException {
		ArrayList<SearchItem> results = db.getSearchItemfromSearch(userID);
		assertEquals(0, results.size());
		
	}
	
	@Test
	public void databaseListItemTest() throws ClassNotFoundException, SQLException {
		
		//insert 3 recipes and 3 restaurants for testing
		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add("testIngredient1");
		ingredients.add("testIngredient2");
		
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("testInstruction1");
		instructions.add("testInstruction2");
		
		Recipe recipe1 = new Recipe("testRecipe1", "picURL", 1.0, 2.0, ingredients, instructions, 4.0);
		Recipe recipe2 = new Recipe("testRecipe2", "picURL", 2.0, 3.0, ingredients, instructions, 3.0);
		Recipe recipe3 = new Recipe("testRecipe3", "picURL", 3.0, 4.0, ingredients, instructions, 2.0);
		Recipe recipe4 = new Recipe("testRecipe4", "picURL", 3.0, 4.0, ingredients, instructions, 2.0);
		Recipe recipe5 = new Recipe("testRecipe5", "picURL", 3.0, 4.0, ingredients, instructions, 2.0);
		
		Restaurant restaurant1 = new Restaurant("testRestaurant1", "webURL", 20, "sampleAddress", "phoneNumber", 4.0, 30);
		Restaurant restaurant2 = new Restaurant("testRestaurant2", "webURL", 20, "sampleAddress", "phoneNumber", 4.0, 30);
		Restaurant restaurant3 = new Restaurant("testRestaurant3", "webURL", 20, "sampleAddress", "phoneNumber", 4.0, 30);
		
		int itemID1 = db.insertRecipe(recipe1);
		int itemID2 = db.insertRecipe(recipe2);
		int itemID3 = db.insertRecipe(recipe3);
		
		int itemID4 = db.insertRestaurant(restaurant1);
		int itemID5 = db.insertRestaurant(restaurant2);
		int itemID6 = db.insertRestaurant(restaurant3);
		
		int itemID7 = db.insertRecipe(recipe4);
		int itemID8 = db.insertRecipe(recipe5);
		
		//insert with userID, itemID, listName
		//insertItemintoList(userID, itemID, listName);
		
		db.insertItemintoList(userID, itemID1, "Favorites");
		db.insertItemintoList(userID, itemID2, "To Explore");
		db.insertItemintoList(userID, itemID3, "Do Not Show");
		db.insertItemintoList(userID, itemID4, "Favorites");
		db.insertItemintoList(userID, itemID5, "To Explore");
		db.insertItemintoList(userID, itemID6, "Do Not Show");
		
		db.insertItemintoList(userID, itemID7, "Favorites");
		db.insertItemintoList(userID, itemID8, "Favorites");
		
		//load data from database for verification
		
		ArrayList<Integer> favIDs = db.getItemsfromList(userID, "Favorites");
		ArrayList<Integer> exploreIDs = db.getItemsfromList(userID, "To Explore");
		ArrayList<Integer> donotshowIDs = db.getItemsfromList(userID, "Do Not Show");
		
		ArrayList<Recipe> favRecipes = new ArrayList<Recipe>();
		ArrayList<Recipe> exploreRecipes = new ArrayList<Recipe>();
		ArrayList<Recipe> dnsRecipes = new ArrayList<Recipe>();
		ArrayList<Restaurant> favRestaurant = new ArrayList<Restaurant>();
		ArrayList<Restaurant> exploreRestaurant = new ArrayList<Restaurant>();
		ArrayList<Restaurant> dnsRestaurant = new ArrayList<Restaurant>();
		
		for (int id : favIDs) {
			//recipe
			if (db.getItemType(id) == 0) {
				favRecipes.add(db.getRecipeInfo(id));
			} else {
				favRestaurant.add(db.getRestaurantInfo(id));
			}
		}
		
		for (int id : exploreIDs) {
			//recipe
			if (db.getItemType(id) == 0) {
				exploreRecipes.add(db.getRecipeInfo(id));
			} else {
				exploreRestaurant.add(db.getRestaurantInfo(id));
			}
		}
		
		for (int id : donotshowIDs) {
			//recipe
			if (db.getItemType(id) == 0) {
				dnsRecipes.add(db.getRecipeInfo(id));
			} else {
				dnsRestaurant.add(db.getRestaurantInfo(id));
			}
		}
		
		assertEquals(favIDs.size(), 4);
		assertEquals(exploreIDs.size(), 2);
		assertEquals(donotshowIDs.size(), 2);
		
		assertEquals(favRecipes.size(), 3);
		assertEquals(exploreRecipes.size(), 1);
		assertEquals(dnsRecipes.size(), 1);
		assertEquals(favRestaurant.size(), 1);
		assertEquals(exploreRestaurant.size(), 1);
		assertEquals(dnsRestaurant.size(), 1);
		
		//test swapping and ordering
		
		//we want to test moving items in favIDs
		//order is itemID1, itemID4, itemID7, itemID8
		assertEquals((Integer)favIDs.get(0), (Integer)itemID1);
		assertEquals((Integer)favIDs.get(1), (Integer)itemID4);
		assertEquals((Integer)favIDs.get(2), (Integer)itemID7);
		assertEquals((Integer)favIDs.get(3), (Integer)itemID8);
		
		db.swapItemIndex(userID, 1, itemID1, true); 
		favIDs = db.getItemsfromList(userID, "Favorites");
		assertEquals((Integer)favIDs.get(0), (Integer)itemID4);
		assertEquals((Integer)favIDs.get(1), (Integer)itemID1);
		assertEquals((Integer)favIDs.get(2), (Integer)itemID7);
		assertEquals((Integer)favIDs.get(3), (Integer)itemID8);
		
		db.swapItemIndex(userID, 1, itemID1, false); 
		favIDs = db.getItemsfromList(userID, "Favorites");
		assertEquals((Integer)favIDs.get(0), (Integer)itemID1); 
		assertEquals((Integer)favIDs.get(1), (Integer)itemID4);
		assertEquals((Integer)favIDs.get(2), (Integer)itemID7);
		assertEquals((Integer)favIDs.get(3), (Integer)itemID8);
		
		//cleanup database
		
		db.deleteItemfromList(userID, itemID1, "Favorites");
		db.deleteItemfromList(userID, itemID2, "To Explore");
		db.deleteItemfromList(userID, itemID3, "Do Not Show");
		db.deleteItemfromList(userID, itemID4, "Favorites");
		db.deleteItemfromList(userID, itemID5, "To Explore");
		db.deleteItemfromList(userID, itemID6, "Do Not Show");
		db.deleteItemfromList(userID, itemID7, "Favorites");
		db.deleteItemfromList(userID, itemID8, "Favorites");
		
		db.deleteItemfromItem(itemID1);
		db.deleteItemfromItem(itemID2);
		db.deleteItemfromItem(itemID3);
		db.deleteItemfromItem(itemID4);
		db.deleteItemfromItem(itemID5);
		db.deleteItemfromItem(itemID6);
		db.deleteItemfromItem(itemID7);
		db.deleteItemfromItem(itemID8);
	}
	
	@Test
	public void databaseGroceryTest() throws Exception {
		
		db.insertIngredientintoGrocery(userID, "2.5 lbs of chicken");
		db.insertIngredientintoGrocery(userID, "1/2 cup of soymilk"); 
		db.insertIngredientintoGrocery(userID, "2 eggs");
		db.insertIngredientintoGrocery(userID, "3 cups of flour");
		
		ArrayList<String> results = db.getGroceryListforUser(userID);
		
		assertEquals(4, results.size());
		assertEquals("2 eggs", results.get(2));
		
		db.deleteIngredientfromGrocery(userID, "1/2 cup of soymilk");
		
		results = db.getGroceryListforUser(userID);
		assertEquals("2 eggs", results.get(1));

		db.deleteIngredientfromGrocery(userID, "2.5 lbs of chicken");
		db.deleteIngredientfromGrocery(userID, "2 eggs");
		db.deleteIngredientfromGrocery(userID, "3 cups of flour");
		
		results = db.getGroceryListforUser(userID);
		assertEquals(0, results.size());
		
	}	
	
	
	
	@After
	public void teardown() throws SQLException {
		db.deleteUserfromUsers(userID);
	}

}