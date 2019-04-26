package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Vector;

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
import servlets.AddSearchHistoryServlet;
import servlets.ValidateLoginServlet;

/*
 *  Tests for the ResultsPageServlet class.
 */
public class ValidateLoginServletTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;

	@Mock
	RequestDispatcher rd;
	
	static int counter = 0;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		rd = mock(RequestDispatcher.class);
		
		counter += 1;
		
		if (counter == 1) {
			session = mock(HttpSession.class);
			when(request.getSession()).thenReturn(session);
		} else {
			session = mock(HttpSession.class);
	    	when(session.getAttribute("test")).thenReturn(0);
	    	Vector<String> vec = new Vector<String>(1);
	    	vec.add("item");
	    	Enumeration<String> value = vec.elements();
	    	when(session.getAttributeNames()).thenReturn(value);
			when(request.getSession()).thenReturn(session);
		}

		
		
		
	}

	/*
	 *  Test to make sure a valid login returns correctly.
	 */
	@Test
	public void testValidLogin() throws Exception {
		
		Database db = new Database();
		db.insertUserintoUsers("testUser", "4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2");
		int uID = db.getUserfromUsers("testUser");

		when(request.getParameter("username")).thenReturn("testUser");
		when(request.getParameter("pass")).thenReturn("root");
		StringWriter out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
		new ValidateLoginServlet().service(request, response);
		
		String r = out.toString();
		
		System.out.println(r);
		
		JsonElement je = new JsonParser().parse(r);
	    JsonObject  jo = je.getAsJsonObject();
	    String result = jo.get("success").getAsString();
	    
	    assertEquals("true", result);
	    
	    db.deleteUserfromUsers(uID);
	}
	
	/*
	 *  Test to make sure an invalid login returns correctly
	 */
	
	@Test
	public void testInvalidLogin() throws Exception {
		
		Database db = new Database();
		db.insertUserintoUsers("master", "pass");
		int uID = db.getUserfromUsers("master");


		when(request.getParameter("username")).thenReturn("master");
		when(request.getParameter("pass")).thenReturn("incorrectpass");
		StringWriter out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
		new ValidateLoginServlet().service(request, response);
		
		String r = out.toString();
		
		JsonElement je = new JsonParser().parse(r);
	    JsonObject  jo = je.getAsJsonObject();
	    String result = jo.get("success").getAsString();
	    
	    assertEquals("false", result);
	
	    jo = jo.getAsJsonObject("data");
	    result = jo.get("errorMsg").getAsString();
	    
	    assertEquals("The password is incorrect!", result);
		
	    db.deleteUserfromUsers(uID);
	}
	
	/*
	 *  Test to make sure an empty username return correctly
	 */
	
	@Test
	public void testEmptyUsername() throws Exception {

		when(request.getParameter("username")).thenReturn("");
		when(request.getParameter("pass")).thenReturn("pass");
		StringWriter out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
		new ValidateLoginServlet().service(request, response);
		
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
		StringWriter out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
		new ValidateLoginServlet().service(request, response);
		
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
	 *  Test to make sure a username that doesn't exist works correctly
	 */
	
	@Test
	public void testUsernameDoesntExist() throws Exception {

		when(request.getParameter("username")).thenReturn("usernamethatdoesntexist");
		when(request.getParameter("pass")).thenReturn("pass");
		StringWriter out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
		new ValidateLoginServlet().service(request, response);
		
		String r = out.toString();
		
		JsonElement je = new JsonParser().parse(r);
	    JsonObject  jo = je.getAsJsonObject();
	    String result = jo.get("success").getAsString();
	    
	    assertEquals("false", result);
	    
	    jo = jo.getAsJsonObject("data");
	    result = jo.get("errorMsg").getAsString();
	    
	    assertEquals("A user with that username does not exist!", result);
		
	}
	
	@Test
    public void testThrowClassExceptions() throws Exception {
    	
		Database db = new Database();
		db.insertUserintoUsers("testUser", "4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2");
		int uID = db.getUserfromUsers("testUser");
		
		when(request.getParameter("username")).thenReturn("testUser");
		when(request.getParameter("pass")).thenReturn("root");
    	
 
       String tempClassName = Config.className;
       

       Config.className = "garbage";
       
       when(request.getParameter("username")).thenReturn("testUser");
       when(request.getParameter("pass")).thenReturn("10000");
       
        
       new ValidateLoginServlet().service(request, response);
       

      Config.className = tempClassName;
      
      db.deleteUserfromUsers(uID);
       
    }
    
    @Test
    public void testThrowSqlExceptions() throws Exception {
    	
    	Database db = new Database();
		db.insertUserintoUsers("testUser", "4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2");
		int uID = db.getUserfromUsers("testUser");
		
		when(request.getParameter("username")).thenReturn("testUser");
		when(request.getParameter("pass")).thenReturn("root");
    	
 
       String tempDBPW = Config.databasePW;
       

       Config.databasePW = "notmypass";
       
       when(request.getParameter("username")).thenReturn("testUser");
       when(request.getParameter("pass")).thenReturn("10000");
       
        
       new ValidateLoginServlet().service(request, response);
       

      Config.databasePW = tempDBPW;
      
      db.deleteUserfromUsers(uID);
       
    }
    
    @Test
    public void testThrowAlgorithmExceptions() throws Exception {
    	
    	Database db = new Database();
		db.insertUserintoUsers("testUser", "4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2");
		int uID = db.getUserfromUsers("testUser");
		
		when(request.getParameter("username")).thenReturn("testUser");
		when(request.getParameter("pass")).thenReturn("root");
    	
 
       String tempAlgo = Config.hashAlgo;
       

       Config.hashAlgo = "garbage";
       
       when(request.getParameter("username")).thenReturn("testUser");
       when(request.getParameter("pass")).thenReturn("10000");
       StringWriter out = new StringWriter();
	   when(response.getWriter()).thenReturn(new PrintWriter(out));
        
       
       new ValidateLoginServlet().service(request, response);
       

      Config.hashAlgo = tempAlgo;
      
      db.deleteUserfromUsers(uID);
       
    }
	
	
	
}
