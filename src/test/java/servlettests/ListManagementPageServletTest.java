package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import data.Recipe;
import data.Restaurant;
import data.UserList;
import servlets.ListManagementPageServlet;

/*
 *  Tests for the ListManagementPageServlet servlet.
 */
public class ListManagementPageServletTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;

	@Mock
	RequestDispatcher rd;
	private Recipe recipe1;
	private Recipe recipe2;
	private Restaurant restaurant1;
	private Restaurant restaurant2;
	private UserList[] userLists;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		rd = mock(RequestDispatcher.class);
		
		when(request.getSession()).thenReturn(session);
		
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
		
		userLists = new UserList[3];
		for (int i = 0; i < 3; ++i) {
			userLists[i] = new UserList();
		}	
		
	}
	
	/*
	 * Test to make sure that the servlet will redirect back to the search page if the session expired.	
	 */
	@Test
	public void testExpiredSession() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/search.jsp")).thenReturn(rd);
        when(session.getAttribute("userLists")).thenReturn(null);

		new ListManagementPageServlet().service(request, response);

		verify(rd).forward(request, response);

	}
	
	/*
	 * Test to make sure that nothing moves if there are only null inputs.
	 */
	@Test
	public void testNullValues() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/listManagement.jsp")).thenReturn(rd);
        when(session.getAttribute("userLists")).thenReturn(userLists);

		new ListManagementPageServlet().service(request, response);

		verify(session).setAttribute(ArgumentMatchers.eq("userLists"), ArgumentMatchers.eq(userLists));
		verify(rd).forward(request, response);

	}
	
	/*
	 * Test to make sure that nothing moves if an invalid list is given.
	 */
	@Test
	public void testIncorrectListType() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/listManagement.jsp")).thenReturn(rd);
        when(session.getAttribute("userLists")).thenReturn(userLists);
        when(request.getParameter("listName")).thenReturn("a");

		new ListManagementPageServlet().service(request, response);

		verify(session).setAttribute(ArgumentMatchers.eq("userLists"), ArgumentMatchers.eq(userLists));
		verify(rd).forward(request, response);

	}
	
	/*
	 * Test to make sure a Restaurant can be removed from the list.
	 */
	@Test
	public void testRestaurantRemove() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/listManagement.jsp")).thenReturn(rd);
        when(request.getParameter("opType")).thenReturn("r");
        when(request.getParameter("fromList")).thenReturn("d");
        when(request.getParameter("listName")).thenReturn("d");
        when(request.getParameter("recOrRest")).thenReturn("rest");
        when(request.getParameter("arrNum")).thenReturn("0");

        
        userLists[1].add(restaurant1);
        when(session.getAttribute("userLists")).thenReturn(userLists);
        
        assertEquals(1, userLists[1].getRestaurants().size());
        
		new ListManagementPageServlet().service(request, response);
		
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(0, userLists[1].getRestaurants().size());
		assertEquals(0, userLists[2].getRestaurants().size());
		verify(rd).forward(request, response);
		verify(request).setAttribute(ArgumentMatchers.eq("listVal"), ArgumentMatchers.eq(userLists[1]));
		verify(request).setAttribute(ArgumentMatchers.eq("listName"), ArgumentMatchers.eq("Don't Show"));
		verify(session).setAttribute(ArgumentMatchers.eq("restaurants"), ArgumentMatchers.eq(userLists[1].getRestaurants()));
		verify(session).setAttribute(ArgumentMatchers.eq("recipes"), ArgumentMatchers.eq(userLists[1].getRecipes()));
		
	}
	
	/*
	 * Test to make sure a Recipe can be removed from the list.
	 */
	@Test
	public void testRecipeRemove() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/listManagement.jsp")).thenReturn(rd);
        when(request.getParameter("opType")).thenReturn("r");
        when(request.getParameter("fromList")).thenReturn("t");
        when(request.getParameter("listName")).thenReturn("t");
        when(request.getParameter("recOrRest")).thenReturn("rec");
        when(request.getParameter("arrNum")).thenReturn("0");
        
        userLists[2].add(recipe1);
        when(session.getAttribute("userLists")).thenReturn(userLists);
        
        assertEquals(1, userLists[2].getRecipes().size());
        
		new ListManagementPageServlet().service(request, response);
		
		assertEquals(0, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(0, userLists[2].getRecipes().size());
		verify(rd).forward(request, response);
		verify(request).setAttribute(ArgumentMatchers.eq("listVal"), ArgumentMatchers.eq(userLists[2]));
		verify(request).setAttribute(ArgumentMatchers.eq("listName"), ArgumentMatchers.eq("To Explore"));
		verify(session).setAttribute(ArgumentMatchers.eq("restaurants"), ArgumentMatchers.eq(userLists[2].getRestaurants()));
		verify(session).setAttribute(ArgumentMatchers.eq("recipes"), ArgumentMatchers.eq(userLists[2].getRecipes()));
	}
	
	/*
	 * Test to make sure a Restaurant can be moved from one list to another successfully.
	 */
	@Test
	public void testMoveRestaurant() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/listManagement.jsp")).thenReturn(rd);
        when(request.getParameter("opType")).thenReturn("d");
        when(request.getParameter("fromList")).thenReturn("f");
        when(request.getParameter("listName")).thenReturn("f");
        when(request.getParameter("recOrRest")).thenReturn("rest");
        when(request.getParameter("arrNum")).thenReturn("0");
        
        userLists[0].add(restaurant1);
        when(session.getAttribute("userLists")).thenReturn(userLists);
        
		new ListManagementPageServlet().service(request, response);
        
        UserList[] tmpUserLists = new UserList[3];
		for (int i = 0; i < 3; ++i) {
			tmpUserLists[i] = new UserList();
		}
		
		tmpUserLists[1].add(restaurant1);
		
		ArrayList<Restaurant> tmp = tmpUserLists[1].getRestaurants();
		
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(1, userLists[1].getRestaurants().size());
		assertEquals(0, userLists[2].getRestaurants().size());
		assertEquals(tmp.get(0), userLists[1].getRestaurants().get(0));
		verify(rd).forward(request, response);
		verify(request).setAttribute(ArgumentMatchers.eq("listVal"), ArgumentMatchers.eq(userLists[0]));
		verify(request).setAttribute(ArgumentMatchers.eq("listName"), ArgumentMatchers.eq("Favorites"));
		verify(session).setAttribute(ArgumentMatchers.eq("restaurants"), ArgumentMatchers.eq(userLists[0].getRestaurants()));
		verify(session).setAttribute(ArgumentMatchers.eq("recipes"), ArgumentMatchers.eq(userLists[0].getRecipes()));
	}
	
	/*
	 * Test to make sure a Recipe can be moved from one list to another successfully.
	 */
	@Test
	public void testMoveRecipe() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/listManagement.jsp")).thenReturn(rd);
        when(request.getParameter("opType")).thenReturn("f");
        when(request.getParameter("fromList")).thenReturn("t");
        when(request.getParameter("listName")).thenReturn("t");
        when(request.getParameter("recOrRest")).thenReturn("rec");
        when(request.getParameter("arrNum")).thenReturn("0");
        
        userLists[2].add(recipe1);
        when(session.getAttribute("userLists")).thenReturn(userLists);
        
		new ListManagementPageServlet().service(request, response);
        
        UserList[] tmpUserLists = new UserList[3];
		for (int i = 0; i < 3; ++i) {
			tmpUserLists[i] = new UserList();
		}
		
		tmpUserLists[0].add(recipe1);
		
		ArrayList<Recipe> tmp = tmpUserLists[0].getRecipes();
		
		assertEquals(1, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(0, userLists[2].getRecipes().size());
		assertEquals(tmp.get(0), userLists[0].getRecipes().get(0));
		verify(rd).forward(request, response);
		verify(request).setAttribute(ArgumentMatchers.eq("listVal"), ArgumentMatchers.eq(userLists[2]));
		verify(request).setAttribute(ArgumentMatchers.eq("listName"), ArgumentMatchers.eq("To Explore"));
		verify(session).setAttribute(ArgumentMatchers.eq("restaurants"), ArgumentMatchers.eq(userLists[2].getRestaurants()));
		verify(session).setAttribute(ArgumentMatchers.eq("recipes"), ArgumentMatchers.eq(userLists[2].getRecipes()));
	}
	
	/*
	 * Test to make sure a Recipe will not move to a list if it already exists in the other list.
	 */
	@Test
	public void testMoveRecipeFail() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/listManagement.jsp")).thenReturn(rd);
        when(request.getParameter("opType")).thenReturn("t");
        when(request.getParameter("fromList")).thenReturn("t");
        when(request.getParameter("listName")).thenReturn("t");
        when(request.getParameter("recOrRest")).thenReturn("rec");
        when(request.getParameter("arrNum")).thenReturn("0");
        
        userLists[2].add(recipe1);
        when(session.getAttribute("userLists")).thenReturn(userLists);
        
		new ListManagementPageServlet().service(request, response);
        
        UserList[] tmpUserLists = new UserList[3];
		for (int i = 0; i < 3; ++i) {
			tmpUserLists[i] = new UserList();
		}
		
		tmpUserLists[2].add(recipe1);
		ArrayList<Recipe> tmp = tmpUserLists[2].getRecipes();
		
		assertEquals(0, userLists[0].getRecipes().size());
		assertEquals(0, userLists[1].getRecipes().size());
		assertEquals(1, userLists[2].getRecipes().size());
		assertEquals(tmp.get(0), userLists[2].getRecipes().get(0));
		verify(rd).forward(request, response);
		verify(request).setAttribute(ArgumentMatchers.eq("listVal"), ArgumentMatchers.eq(userLists[2]));
		verify(request).setAttribute(ArgumentMatchers.eq("listName"), ArgumentMatchers.eq("To Explore"));
		verify(session).setAttribute(ArgumentMatchers.eq("restaurants"), ArgumentMatchers.eq(userLists[2].getRestaurants()));
		verify(session).setAttribute(ArgumentMatchers.eq("recipes"), ArgumentMatchers.eq(userLists[2].getRecipes()));
	}
	
	/*
	 * Test to make sure a Recipe will not move to a list if it already exists in the other list.
	 */	
	@Test
	public void testMoveRestaurantFail() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/listManagement.jsp")).thenReturn(rd);
        when(request.getParameter("opType")).thenReturn("t");
        when(request.getParameter("fromList")).thenReturn("t");
        when(request.getParameter("listName")).thenReturn("t");
        when(request.getParameter("recOrRest")).thenReturn("rest");
        when(request.getParameter("arrNum")).thenReturn("0");
        
        userLists[2].add(restaurant1);
        when(session.getAttribute("userLists")).thenReturn(userLists);
        
		new ListManagementPageServlet().service(request, response);
        
        UserList[] tmpUserLists = new UserList[3];
		for (int i = 0; i < 3; ++i) {
			tmpUserLists[i] = new UserList();
		}
		
		tmpUserLists[2].add(restaurant1);
		ArrayList<Restaurant> tmp = tmpUserLists[2].getRestaurants();
		
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(0, userLists[1].getRestaurants().size());
		assertEquals(1, userLists[2].getRestaurants().size());
		assertEquals(tmp.get(0), userLists[2].getRestaurants().get(0));
		verify(rd).forward(request, response);
		verify(request).setAttribute(ArgumentMatchers.eq("listVal"), ArgumentMatchers.eq(userLists[2]));
		verify(request).setAttribute(ArgumentMatchers.eq("listName"), ArgumentMatchers.eq("To Explore"));
		verify(session).setAttribute(ArgumentMatchers.eq("restaurants"), ArgumentMatchers.eq(userLists[2].getRestaurants()));
		verify(session).setAttribute(ArgumentMatchers.eq("recipes"), ArgumentMatchers.eq(userLists[2].getRecipes()));
	}

}
