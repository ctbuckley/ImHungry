package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
import data.UserList;
import servlets.ListManagementPageServlet;
import servlets.RecipeDetailsPageServlet;

/*
 * Tests for the RecipeDetailsPageServlet servlet.
 */
public class RecipeDetailsPageServletTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;

	@Mock
	RequestDispatcher rd;
	
	Recipe[] results;
	UserList[] userLists;
	Database db;
	int userID;
	int itemID;
	String databasePW;
	String className;
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException{
		MockitoAnnotations.initMocks(this);
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		rd = mock(RequestDispatcher.class);
		
		databasePW = Config.databasePW;
	    className = Config.className;
		
	    db = new Database();
		db.insertUserintoUsers("testUser", "password");
		userID = db.getUserfromUsers("testUser");
		
		when(request.getSession()).thenReturn(session);
		
		userLists = new UserList[3];
		for (int i = 0; i < 3; ++i) {
			userLists[i] = new UserList();
		}
		
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
		Recipe recipe2 = new Recipe("Not" + name, pictureUrl, prepTime, cookTime, ingredients, instructions, rating);
        

		itemID = db.insertRecipe(recipe2);		
		
		results = new Recipe[2];
		results[0] = recipe1;
		results[1] = recipe2;
        
		when(session.getAttribute("itemID")).thenReturn(itemID);
        when(session.getAttribute("recipeResults")).thenReturn(results);
        when(request.getParameter("arrNum")).thenReturn("1");
        when(session.getAttribute("userLists")).thenReturn(userLists);
        when(session.getAttribute("username")).thenReturn("testUser");
	}

	/*
	 * Test for adding a Recipe to the Favorites list.
	 */
	@Test
	public void testAddToFavorites() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("f");
		
		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(1, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(0, userLists[2].getRecipes().size());
		assertEquals(tmp.getRecipes().get(0), userLists[0].getRecipes().get(0));

	}
	
	/*
	 *  Test adding a Recipe to the To Explore list.
	 */
	@Test
	public void testAddToToExplore() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("t");
		
		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(1, userLists[2].getRecipes().size());
		assertEquals(tmp.getRecipes().get(0), userLists[2].getRecipes().get(0));

	}
	
	/*
	 *  Test adding a Recipe to Do Not Show.
	 */
	@Test
	public void testAddToDoNotShow() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("d");

		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRecipes().size());
		assertEquals(1, userLists[1].getRecipes().size());
		assertEquals(0, userLists[2].getRecipes().size());
		assertEquals(tmp.getRecipes().get(0), userLists[1].getRecipes().get(0));

	}
	
	/*
	 *  Test adding to the Favorites list if the Recipe is already in the Do Not Show list.
	 */
	@Test
	public void testAddToFavoritesDuplicate1() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("f");

        userLists[1].add(results[1]);
        
		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRecipes().size());
		assertEquals(1, userLists[1].getRecipes().size());
		assertEquals(0, userLists[2].getRecipes().size());
		assertEquals(tmp.getRecipes().get(0), userLists[1].getRecipes().get(0));

	}
	
	/*
	 *  Test adding to the To Explore list if the Recipe is already in the Favorite list.
	 */
	@Test
	public void testAddToToExploreDuplicate1() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("t");

        userLists[0].add(results[1]);
		
		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(1, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(0, userLists[2].getRecipes().size());
		assertEquals(tmp.getRecipes().get(0), userLists[0].getRecipes().get(0));		

	}
	
	/*
	 *  Test adding to the Do Not Show list if the Recipe is already in the Favorite list.
	 */
	@Test
	public void testAddToDoNotShowDuplicate1() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("d");

        userLists[0].add(results[1]);

		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(1, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(0, userLists[2].getRecipes().size());
		assertEquals(tmp.getRecipes().get(0), userLists[0].getRecipes().get(0));
	}
	
	/*
	 *  Test adding to the Favorites list if the Recipe is already in the To Explore list.
	 */
	@Test
	public void testAddToFavoritesDuplicate2() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("f");

        userLists[2].add(results[1]);
        
		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(1, userLists[2].getRecipes().size());
		assertEquals(tmp.getRecipes().get(0), userLists[2].getRecipes().get(0));
	}
	
	/*
	 *  Test adding to the To Explore list if the Recipe is already in the Do Not Show list.
	 */
	@Test
	public void testAddToToExploreDuplicate2() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("t");

        userLists[1].add(results[1]);
		
		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRecipes().size());
		assertEquals(1, userLists[1].getRecipes().size());
		assertEquals(0, userLists[2].getRecipes().size());
		assertEquals(tmp.getRecipes().get(0), userLists[1].getRecipes().get(0));
	}
	
	/*
	 *  Test adding to the Do Not Show list if the Recipe is already in the To Explore.
	 */
	@Test
	public void testAddToDoNotShowDuplicate2() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("d");

        userLists[2].add(results[1]);

		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(1, userLists[2].getRecipes().size());
		assertEquals(tmp.getRecipes().get(0), userLists[2].getRecipes().get(0));
	}	
	
	/*
	 *  Test to make sure the servlet will not modify the lists if there is not list specified.
	 */
	@Test
	public void testNoList() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn(null);
		
		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		assertEquals(0, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(0, userLists[2].getRecipes().size());
	}
	
	/*
	 *  Test to make sure the servlet will not modify the lists if the one specified is not correct.
	 */
	@Test
	public void testIncorrectList() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("a");

		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		assertEquals(0, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(0, userLists[2].getRecipes().size());

	}
	
	/*
	 *  Test to make sure the servlet will redirect back to the search page if the session has expired.
	 */
	@Test
	public void testExpiredSession() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/search.jsp")).thenReturn(rd);
        when(session.getAttribute("recipeResults")).thenReturn(null);

		new RecipeDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);

	}
	
	@Test
    public void testThrowClassExceptions() throws Exception {
       String tempClassName = Config.className;
       Config.className = "garbage";
       
       when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
       
       new RecipeDetailsPageServlet().service(request, response);
       Config.className =  tempClassName;
    }
    
    @Test
    public void testThrowSqlExceptions() throws Exception {
       String tempDBPW = Config.databasePW;
       Config.databasePW = "notmypass";
       
       when(request.getRequestDispatcher("/jsp/recipeDetails.jsp")).thenReturn(rd);
       
       new RecipeDetailsPageServlet().service(request, response);
       Config.databasePW = tempDBPW;
    }
	
	@After
	public void teardown() throws SQLException {
		
		Config.databasePW = databasePW;
		Config.className = className;
		
		try {
			db.dropTable("Lists");
		} catch (SQLException sqle) {
			
		}
		
		db.deleteUserfromUsers(userID);
	}
	
}
