package servlettests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
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
import data.SearchItem;
import servlets.AddSearchHistoryServlet;
import servlets.AddToGroceryListServlet;

/*
 *  Tests for the AddToGroceryListServlet class.
 */
public class AddToGroceryListServletTest {
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
    String databasePW;
    String className;
    
    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        MockitoAnnotations.initMocks(this);
        
        databasePW = Config.databasePW;
        className = Config.className;
        
        db = new Database();
		db.insertUserintoUsers("testUser", "password");
		userID =  db.getUserfromUsers("testUser");
		
		db.insertIngredientintoGrocery(userID, "2 eggs");
		
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        rd = mock(RequestDispatcher.class);
        
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("groceryListItems")).thenReturn("%5B%222%20eggs%22,%221%20tablespoon%20olive%20oil%22%5D"); // URI Encoded JSON array ["2 eggs", "1 tablespoon olive oil"]
    }
    /*
     *  Test to make sure a valid new user returns correctly.
     */
    @Test
    public void testInsertIngredient() throws Exception {
       when(session.getAttribute("username")).thenReturn("testUser");
        
       new AddToGroceryListServlet().service(request, response);
       
       ArrayList<String> ingredients = db.getGroceryListforUser(userID);
       assertEquals(ingredients.get(0), "2 eggs");
       for(int i = 0; i < ingredients.size(); i++) System.out.println(ingredients.get(i));
       assertEquals(ingredients.get(1), "1 tablespoon olive oil");
       assertEquals(ingredients.size(), 2);

    } 
    
    @Test
    public void testThrowClassExceptions() throws Exception {
    	    
       Config.className = "garbage";
        
       new AddToGroceryListServlet().service(request, response);
              
    }
    
    @Test
    public void testThrowSqlExceptions() throws Exception {
    	

       Config.databasePW = "notmypass";
       
       new AddToGroceryListServlet().service(request, response);
              
    }
    
    @After
	public void teardown() throws SQLException {
    	
    	Config.databasePW = databasePW;
    	Config.className = className;
    	
    	try {
    		db.deleteIngredientfromGrocery(userID, "2 eggs");
    		db.deleteIngredientfromGrocery(userID, "1 tablespoon olive oil");
    	} catch (SQLException sqle) {
    		
    	}
    	
		db.deleteUserfromUsers(userID);
	}


    
}