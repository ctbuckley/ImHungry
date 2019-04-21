package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import data.Config;
import data.Database;
import servlets.AddUserServlet;
import servlets.ValidateLoginServlet;

/*
 *  Tests for the AddUserServlet class.
 */
public class AddUserServletTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;

	@Mock
	RequestDispatcher rd;
	
	Database db;
	StringWriter out;

	@Before
	public void setUp() throws ClassNotFoundException, SQLException, IOException {
		MockitoAnnotations.initMocks(this);
		
		db = new Database();
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		rd = mock(RequestDispatcher.class);
		out = new StringWriter();
		
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		when(request.getSession()).thenReturn(session);
		
	}

	/*
	 *  Test to make sure a valid new user returns correctly.
	 */
	@Test
	public void testValidNewUser() throws Exception {

		when(request.getParameter("username")).thenReturn("testValidNewUser");
		when(request.getParameter("pass")).thenReturn("password");
		
		new AddUserServlet().service(request, response);
		
		String r = out.toString();
		
		JsonElement je = new JsonParser().parse(r);
	    JsonObject  jo = je.getAsJsonObject();
	    String result = jo.get("success").getAsString();
	    
	    assertEquals("true", result);
	    

		int uID = db.getUserfromUsers("testValidNewUser");
		db.deleteUserfromUsers(uID);
	}	
	
	/*
	 *  Test to make sure an empty username return correctly
	 */
	
	@Test
	public void testEmptyUsername() throws Exception {

		when(request.getParameter("username")).thenReturn("");
		when(request.getParameter("pass")).thenReturn("pass");
		
		new AddUserServlet().service(request, response);
		
		String r = out.toString();
		
		JsonElement je = new JsonParser().parse(r);
	    JsonObject  jo = je.getAsJsonObject();
	    String result = jo.get("success").getAsString();
	    
	    assertEquals("false", result);
	    
	    jo = jo.getAsJsonObject("data");
	    result = jo.get("errorMsg").getAsString();
	    
	    assertEquals("The username is empty! ", result);
	}
	
	/*
	 *  Test to make sure an empty password return correctly
	 */
	
	@Test
	public void testEmptyPassword() throws Exception {

		when(request.getParameter("username")).thenReturn("master");
		when(request.getParameter("pass")).thenReturn("");
		
		new AddUserServlet().service(request, response);
		
		String r = out.toString();
		
		JsonElement je = new JsonParser().parse(r);
	    JsonObject  jo = je.getAsJsonObject();
	    String result = jo.get("success").getAsString();
	    
	    assertEquals("false", result);
	    
	    jo = jo.getAsJsonObject("data");
	    result = jo.get("errorMsg").getAsString();
	    
	    assertEquals("The password is empty! ", result);
		
	}
	
	/*
	 *  Test to make sure a username that already exists works correctly
	 */
	
	@Test
	public void testUsernameAlreadyExist() throws Exception {
		
		when(request.getParameter("username")).thenReturn("testValidNewUser");
		when(request.getParameter("pass")).thenReturn("password");
		
		new AddUserServlet().service(request, response);
		
		String r = out.toString();
		
		JsonElement je = new JsonParser().parse(r);
	    JsonObject  jo = je.getAsJsonObject();
	    String result = jo.get("success").getAsString();
	    
	    assertEquals("true", result);

		when(request.getParameter("username")).thenReturn("testValidNewUser");
		when(request.getParameter("pass")).thenReturn("password");
		out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
		new AddUserServlet().service(request, response);
		
		r = out.toString();
		
		je = new JsonParser().parse(r);
	    jo = je.getAsJsonObject();
	    result = jo.get("success").getAsString();
	    
	    assertEquals("false", result);
	    
	    jo = jo.getAsJsonObject("data");
	    result = jo.get("errorMsg").getAsString();
	    
	    assertEquals("A user with that username already exists!", result);
	    
		int uID = db.getUserfromUsers("testValidNewUser");
		db.deleteUserfromUsers(uID);
	}
	
	@Test
    public void testThrowClassExceptions() throws Exception {
    	
		
	when(request.getParameter("username")).thenReturn("testUser");
	when(request.getParameter("pass")).thenReturn("root");
    	
       String tempClassName = Config.className;
       Config.className = "garbage";
       
        
       new AddUserServlet().service(request, response);
       

      Config.className = tempClassName;
       
    }
    
    @Test
    public void testThrowSqlExceptions() throws Exception {
    	
    	when(request.getParameter("username")).thenReturn("testUser");
    	when(request.getParameter("pass")).thenReturn("root");
    	
 
       String tempDBPW = Config.databasePW;
       Config.databasePW = "notmypass";
       
       
        
       new AddUserServlet().service(request, response);
       

      Config.databasePW = tempDBPW;
      
       
    }
    
    @Test
    public void testThrowAlgorithmExceptions() throws Exception {
    	
    	when(request.getParameter("username")).thenReturn("testUser");
    	when(request.getParameter("pass")).thenReturn("root");
    	
 
       String tempAlgo = Config.hashAlgo;
       
       Config.hashAlgo = "garbage";
       
       
       out = new StringWriter();
	   when(response.getWriter()).thenReturn(new PrintWriter(out));
        
       
       new AddUserServlet().service(request, response);
       

      Config.hashAlgo = tempAlgo;
      
       
    }
}
