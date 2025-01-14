package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import data.Config;
import data.Database;
import data.Recipe;
import data.Restaurant;
import data.UserList;
import servlets.ListReorderServlet;

/*
 *  Tests for the ResultsPageServlet class.
 */
public class ListReorderServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher rd;
    
    Database db;
    int userID;
    
    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
    	
        MockitoAnnotations.initMocks(this);
        
        db = new Database();
		db.insertUserintoUsers("testUser", "password");
		userID = db.getUserfromUsers("testUser");
		
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        rd = mock(RequestDispatcher.class);
        
        when(request.getSession()).thenReturn(session);
        
    }

    @Test
    public void testListReordering() throws Exception {
    	
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
		
		//test swapping and ordering
		
		//we want to test moving items in favIDs
		//order is itemID1, itemID4, itemID7, itemID8
		assertEquals((Integer)favIDs.get(0), (Integer)itemID1);
		assertEquals((Integer)favIDs.get(1), (Integer)itemID4);
		assertEquals((Integer)favIDs.get(2), (Integer)itemID7);
		assertEquals((Integer)favIDs.get(3), (Integer)itemID8);
		 
		when(request.getParameter("username")).thenReturn("testUser");
        when(request.getParameter("oldIndex")).thenReturn("0");
        when(request.getParameter("newIndex")).thenReturn("2");
        when(request.getParameter("listName")).thenReturn("Favorites");
       
        new ListReorderServlet().service(request, response);
		
		favIDs = db.getItemsfromList(userID, "Favorites");
		assertEquals((Integer)favIDs.get(0), (Integer)itemID4);
		assertEquals((Integer)favIDs.get(1), (Integer)itemID7);
		assertEquals((Integer)favIDs.get(2), (Integer)itemID1);
		assertEquals((Integer)favIDs.get(3), (Integer)itemID8);
		
		UserList favList = db.getUserList("testUser", 0);
		assertEquals(3, favList.getRecipes().size());
		assertEquals(1, favList.getRestaurants().size());
		
		favList = db.getUserList("testUser", 1);
		assertEquals(1, favList.getRecipes().size());
		assertEquals(1, favList.getRestaurants().size());
		
		favList = db.getUserList("testUser", 2);
		assertEquals(1, favList.getRecipes().size());
		assertEquals(1, favList.getRestaurants().size());
		
		when(request.getParameter("username")).thenReturn("testUser");
        when(request.getParameter("oldIndex")).thenReturn("2");
        when(request.getParameter("newIndex")).thenReturn("0");
        when(request.getParameter("listName")).thenReturn("Favorites");
       
        new ListReorderServlet().service(request, response);
		
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
    public void testThrowClassExceptions() throws Exception {
       String tempClassName = Config.className;
       Config.className = "garbage";
       new ListReorderServlet().service(request, response);
       Config.className =  tempClassName;
    }
    
    @Test
    public void testThrowSqlExceptions() throws Exception {
       String tempDBPW = Config.databasePW;
       Config.databasePW = "notmypass";
       new ListReorderServlet().service(request, response);
       Config.databasePW = tempDBPW;
    }
    
    @After
	public void teardown() throws SQLException {
		db.deleteUserfromUsers(userID);
	}
}