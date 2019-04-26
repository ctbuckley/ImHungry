package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import data.Config;
import data.Database;
import servlets.DisplayGroceryListServlet;

public class DisplayGroceryListServletTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;
	@Mock
	RequestDispatcher rd;
	
	@Captor
	ArgumentCaptor argCaptor;
	
	Database db;
	int userID;
	String databasePW;
	String className;
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
	    MockitoAnnotations.initMocks(this);
	    
	    databasePW = Config.databasePW;
	    className = Config.className;
	    
	    db = new Database();
		db.insertUserintoUsers("testUser", "password");
		userID = db.getUserfromUsers("testUser");
		
		db.insertIngredientintoGrocery(userID, "2 eggs");
		
	    request = mock(HttpServletRequest.class);
	    response = mock(HttpServletResponse.class);
	    session = mock(HttpSession.class);
	    rd = mock(RequestDispatcher.class);
	    
	    when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/jsp/groceryList.jsp")).thenReturn(rd);

	}
	/*
	 *  Test to make sure a valid new user returns correctly.
	 */
	@Test
	public void testInsertIngredient() throws Exception {
		
		argCaptor = ArgumentCaptor.forClass(String[].class);
		when(session.getAttribute("username")).thenReturn("testUser");
		    
		new DisplayGroceryListServlet().service(request, response);
		   
		verify(session).setAttribute(ArgumentMatchers.eq("groceryList"), argCaptor.capture());
		String[] ingredientList = (String[]) argCaptor.getValue();
		assertEquals(1, ingredientList.length);
		assertEquals(ingredientList[0], "2 eggs");
			
		verify(rd).forward(request, response);

	} 
	
	@Test
	public void testMergeIngredients() throws Exception {
		
		argCaptor = ArgumentCaptor.forClass(String[].class);
		when(session.getAttribute("username")).thenReturn("testUser");
		    
		new DisplayGroceryListServlet().service(request, response);
		   
		verify(session).setAttribute(ArgumentMatchers.eq("groceryList"), argCaptor.capture());
		String[] ingredientList = (String[]) argCaptor.getValue();
		assertEquals(1, ingredientList.length);
		assertEquals(ingredientList[0], "2 eggs");
			
		verify(rd).forward(request, response);
		
		db.insertIngredientintoGrocery(userID, "2 1/2 lbs of chicken");
		db.insertIngredientintoGrocery(userID, "1/2 cup of soymilk"); 
		db.insertIngredientintoGrocery(userID, "2 eggs");
		db.insertIngredientintoGrocery(userID, "oil for cooking");
		db.insertIngredientintoGrocery(userID, "1 cup of flour");
		
		db.insertIngredientintoGrocery(userID, "2 1/2 lbs of chicken");
		db.insertIngredientintoGrocery(userID, "1/2 cup of soymilk"); 
		db.insertIngredientintoGrocery(userID, "2 eggs");
		db.insertIngredientintoGrocery(userID, "oil for cooking");
		db.insertIngredientintoGrocery(userID, "1 cup of flour");
		db.insertIngredientintoGrocery(userID, "1 spoonful of beef filet");
		
		db.insertIngredientintoGrocery(userID, "2 1/2 lbs of chicken");
		db.insertIngredientintoGrocery(userID, "oil for cooking");
		db.insertIngredientintoGrocery(userID, "1 cup of flour");
		db.insertIngredientintoGrocery(userID, "1 spoonful of beef filet");
		
		new DisplayGroceryListServlet().service(request, response);
		
		db.deleteIngredientfromGrocery(userID, "2 1/2 lbs of chicken");
		db.deleteIngredientfromGrocery(userID, "2 eggs");
		db.deleteIngredientfromGrocery(userID, "1 cup of flour");
		db.deleteIngredientfromGrocery(userID, "1/2 cup of soymilk");
		db.deleteIngredientfromGrocery(userID, "1 spoonful of beef filet");
		db.deleteIngredientfromGrocery(userID, "oil for cooking");
		

	} 
	
	@Test
	public void testThrowClassExceptions() throws Exception {
		    
	   Config.className = "garbage";
	    
	   new DisplayGroceryListServlet().service(request, response);
	   
	   verify(rd).forward(request, response);
	          
	}
	
	@Test
	public void testThrowSqlExceptions() throws Exception {
		
	
	   Config.databasePW = "notmypass";
	   
	   new DisplayGroceryListServlet().service(request, response);
	   
	   verify(rd).forward(request, response);
	          
	}
	
	@After
	public void teardown() throws SQLException {
		
		Config.databasePW = databasePW;
		Config.className = className;
		
		try {
			db.deleteIngredientfromGrocery(userID, "2 eggs");
		} catch (SQLException sqle) {
			
		}
		
		db.deleteUserfromUsers(userID);
	}
	

}
