package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import data.Config;
import data.Database;
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
		ResultSet rs =  db.getUserfromUsers("testUser");
		rs.next();
		userID = rs.getInt("userID");
		
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        rd = mock(RequestDispatcher.class);
        
        when(request.getSession()).thenReturn(session);
        
    }

    @Test
    public void testValidNewUser() throws Exception {

       when(request.getParameter("username")).thenReturn("testUser");
       when(request.getParameter("oldIndex")).thenReturn("0");
       when(request.getParameter("newIndex")).thenReturn("2");
       when(request.getParameter("listName")).thenReturn("Favorites");
       
       new ListReorderServlet().service(request, response);
 
       //revert
       when(request.getParameter("username")).thenReturn("testUser");
       when(request.getParameter("oldIndex")).thenReturn("2");
       when(request.getParameter("newIndex")).thenReturn("0");
       when(request.getParameter("listName")).thenReturn("Favorites");
       
       new ListReorderServlet().service(request, response);
       
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