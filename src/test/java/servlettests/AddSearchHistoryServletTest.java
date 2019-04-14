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
import data.SearchItem;
import servlets.AddSearchHistoryServlet;

/*
 *  Tests for the ResultsPageServlet class.
 */
public class AddSearchHistoryServletTest {
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
		ResultSet rs =  db.getUserfromUsers("testUser");
		rs.next();
		userID = rs.getInt("userID");
		
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        rd = mock(RequestDispatcher.class);
        
        when(request.getSession()).thenReturn(session);
        
    }
    /*
     *  Test to make sure a valid new user returns correctly.
     */
    @Test
    public void testValidNewUser() throws Exception {
       when(request.getParameter("username")).thenReturn("testUser");
       when(request.getParameter("radius")).thenReturn("10000");
       when(request.getParameter("searchQuery")).thenReturn("milk tea");
       when(request.getParameter("numResults")).thenReturn("7");
        
       new AddSearchHistoryServlet().service(request, response);
 
       ArrayList<SearchItem> si = db.getSearchItemfromSearch(userID);
       int searchID = si.get(0).searchID;
       
       assertEquals(si.get(0).searchQuery, "milk tea");
       assertEquals(si.get(0).numResults, 7); 
       assertEquals(si.get(0).radius, 10000);
       
       db.deleteQueryfromSearchHistory(searchID);
       
    } 
    
    @Test
    public void testThrowClassExceptions() throws Exception {
    	
       String tempClassName = Config.className;
    
       Config.className = "garbage";
        
       new AddSearchHistoryServlet().service(request, response);
       
      Config.className =  tempClassName;
      
       
    }
    
    @Test
    public void testThrowSqlExceptions() throws Exception {
    	
 
       String tempDBPW = Config.databasePW;
       

       Config.databasePW = "notmypass";
       
        
       new AddSearchHistoryServlet().service(request, response);
       

      Config.databasePW = tempDBPW;
       
    }
    
    
    @After
	public void teardown() throws SQLException {
		db.deleteUserfromUsers(userID);
	}


    
}