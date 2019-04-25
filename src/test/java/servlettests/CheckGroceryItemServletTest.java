package servlettests;

import static org.junit.Assert.*;
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
import servlets.CheckGroceryItemServlet;

public class CheckGroceryItemServletTest {

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
		userID = db.getUserfromUsers("testUser");
		
		db.insertIngredientintoGrocery(userID, "2 eggs");
		db.insertIngredientintoGrocery(userID, "1 tablespoon olive oil");
		
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        rd = mock(RequestDispatcher.class);
        
        when(request.getSession()).thenReturn(session);
    }
 
    
    @Test
    public void testChangeCheckonIngredient() throws Exception {
       when(session.getAttribute("username")).thenReturn("testUser");
       when(request.getParameter("item")).thenReturn("2 eggs");
        
       new CheckGroceryItemServlet().service(request, response);
       int check = db.getCheckonGroceryItem(userID, "2 eggs");
       assertEquals(1, check);
       
       new CheckGroceryItemServlet().service(request, response);
       check = db.getCheckonGroceryItem(userID, "2 eggs");
       assertEquals(0, check);

    } 
    
    @Test
    public void testThrowClassExceptions() throws Exception {
    	    
       Config.className = "garbage";
        
       new CheckGroceryItemServlet().service(request, response);
              
    }
    
    @Test
    public void testThrowSqlExceptions() throws Exception {
    	

       Config.databasePW = "notmypass";
       
       new CheckGroceryItemServlet().service(request, response);
              
    }
    
    @After
	public void teardown() throws SQLException {
    	
    	Config.databasePW = databasePW;
    	Config.className = className;
    	
    	try {
    		db.deleteIngredientfromGrocery(userID, "1 tablespoon olive oil");
    		db.deleteIngredientfromGrocery(userID, "2 eggs");
    	} catch (SQLException sqle) {
    		
    	}
    	
		db.deleteUserfromUsers(userID);
	}

}
