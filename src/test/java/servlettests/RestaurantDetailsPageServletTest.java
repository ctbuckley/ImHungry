package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import data.Restaurant;
import data.UserList;
import servlets.RestaurantDetailsPageServlet;

/*
 * Tests for the RestaurantDetailsPageServlet servlet.
 */
public class RestaurantDetailsPageServletTest {


	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;

	@Mock
	RequestDispatcher rd;
	
	Restaurant[] results;
	UserList[] userLists;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		rd = mock(RequestDispatcher.class);
		
		when(request.getSession()).thenReturn(session);
		
		userLists = new UserList[3];
		for (int i = 0; i < 3; ++i) {
			userLists[i] = new UserList();
		}
		
        results = new Restaurant[2];
		results[0] = new Restaurant("A Good Restaurant", "https://www.mcdonalds.com/us/en-us.html", 1, "Everywhere", "(123)456-7890", 2.25, 5);
		results[1] = new Restaurant("A Bad Restaurant", "https://www.bk.com/", 2, "Almost everywhere", "(123)456-7896", 1.25, 50);
        
        when(session.getAttribute("restaurantResults")).thenReturn(results);
        when(request.getParameter("arrNum")).thenReturn("1");
        when(session.getAttribute("userLists")).thenReturn(userLists);
	}

	/*
	 * Test for adding a Restaurant to the Favorites list.
	 */
	@Test
	public void testAddToFavorites() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("f");
		
		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(1, userLists[0].getRestaurants().size());
		assertEquals(0, userLists[1].getRestaurants().size());
		assertEquals(0, userLists[2].getRestaurants().size());
		assertEquals(tmp.getRestaurants().get(0), userLists[0].getRestaurants().get(0));

	}
	
	/*
	 *  Test adding a Restaurant to the To Explore list.
	 */
	@Test
	public void testAddToToExplore() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("t");
		
		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(0, userLists[1].getRestaurants().size());
		assertEquals(1, userLists[2].getRestaurants().size());
		assertEquals(tmp.getRestaurants().get(0), userLists[2].getRestaurants().get(0));

	}
	
	/*
	 *  Test adding a Restaurant to Do Not Show.
	 */
	@Test
	public void testAddToDoNotShow() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("d");

		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(1, userLists[1].getRestaurants().size());
		assertEquals(0, userLists[2].getRestaurants().size());
		assertEquals(tmp.getRestaurants().get(0), userLists[1].getRestaurants().get(0));

	}
	
	/*
	 *  Test adding to the Favorites list if the Restaurant is already in the Do Not Show list.
	 */
	@Test
	public void testAddToFavoritesDuplicate1() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("f");

        userLists[1].add(results[1]);
        
		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(1, userLists[1].getRestaurants().size());
		assertEquals(0, userLists[2].getRestaurants().size());
		assertEquals(tmp.getRestaurants().get(0), userLists[1].getRestaurants().get(0));

	}
	
	/*
	 *  Test adding to the To Explore list if the Restaurant is already in the Favorite list.
	 */
	@Test
	public void testAddToToExploreDuplicate1() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("t");

        userLists[0].add(results[1]);
		
		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(1, userLists[0].getRestaurants().size());
		assertEquals(0, userLists[1].getRestaurants().size());
		assertEquals(0, userLists[2].getRestaurants().size());
		assertEquals(tmp.getRestaurants().get(0), userLists[0].getRestaurants().get(0));		

	}
	
	/*
	 *  Test adding to the Do Not Show list if the Restaurant is already in the Favorite list.
	 */
	@Test
	public void testAddToDoNotShowDuplicate1() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("d");

        userLists[0].add(results[1]);

		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(1, userLists[0].getRestaurants().size());
		assertEquals(0, userLists[1].getRestaurants().size());
		assertEquals(0, userLists[2].getRestaurants().size());
		assertEquals(tmp.getRestaurants().get(0), userLists[0].getRestaurants().get(0));
	}
	
	/*
	 *  Test adding to the Favorites list if the Restaurant is already in the To Explore list.
	 */
	@Test
	public void testAddToFavoritesDuplicate2() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("f");

        userLists[2].add(results[1]);
        
		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(0, userLists[1].getRestaurants().size());
		assertEquals(1, userLists[2].getRestaurants().size());
		assertEquals(tmp.getRestaurants().get(0), userLists[2].getRestaurants().get(0));
	}
	
	/*
	 *  Test adding to the To Explore list if the Restaurant is already in the Do Not Show list.
	 */
	@Test
	public void testAddToToExploreDuplicate2() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("t");

        userLists[1].add(results[1]);
		
		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(1, userLists[1].getRestaurants().size());
		assertEquals(0, userLists[2].getRestaurants().size());
		assertEquals(tmp.getRestaurants().get(0), userLists[1].getRestaurants().get(0));
	}
	
	/*
	 *  Test adding to the Do Not Show list if the Restaurant is already in the To Explore.
	 */
	@Test
	public void testAddToDoNotShowDuplicate2() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("d");

        userLists[2].add(results[1]);

		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		UserList tmp = new UserList();
		tmp.add(results[1]);
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(0, userLists[1].getRestaurants().size());
		assertEquals(1, userLists[2].getRestaurants().size());
		assertEquals(tmp.getRestaurants().get(0), userLists[2].getRestaurants().get(0));
	}	
	
	/*
	 *  Test to make sure the servlet will not modify the lists if there is not list specified.
	 */
	@Test
	public void testNoList() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn(null);
		
		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(0, userLists[1].getRestaurants().size());
		assertEquals(0, userLists[2].getRestaurants().size());
	}
	
	/*
	 *  Test to make sure the servlet will not modify the lists if the one specified is not correct.
	 */
	@Test
	public void testIncorrectList() throws Exception {

        when(request.getRequestDispatcher("/jsp/restaurantDetails.jsp")).thenReturn(rd);
        when(request.getParameter("listType")).thenReturn("a");

		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);
		
		assertEquals(0, userLists[0].getRestaurants().size());
		assertEquals(0, userLists[1].getRestaurants().size());
		assertEquals(0, userLists[2].getRestaurants().size());

	}
	
	/*
	 *  Test to make sure the servlet will redirect back to the search page if the session has expired.
	 */
	@Test
	public void testExpiredSession() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/search.jsp")).thenReturn(rd);
        when(session.getAttribute("restaurantResults")).thenReturn(null);

		new RestaurantDetailsPageServlet().service(request, response);

		verify(rd).forward(request, response);

	}

}
