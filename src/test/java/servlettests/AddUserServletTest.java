package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

import data.Database;
import servlets.AddUserServlet;

/*
 *  Tests for the ResultsPageServlet class.
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

	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		MockitoAnnotations.initMocks(this);
		
		db = new Database();
		
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

		when(request.getParameter("username")).thenReturn("testValidNewUser");
		when(request.getParameter("pass")).thenReturn("password");
		StringWriter out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
		new AddUserServlet().service(request, response);
		
		String r = out.toString();
		
		JsonElement je = new JsonParser().parse(r);
	    JsonObject  jo = je.getAsJsonObject();
	    String result = jo.get("success").getAsString();
	    
	    assertEquals("true", result);
	    
	    //Remove the user so that this test works next time
  		ResultSet rs = null;
  		rs = db.getUserfromUsers("testValidNewUser");
  		rs.next();
		int uID = rs.getInt("userID");
		db.deleteUserfromUsers(uID);
		if(rs!=null) {
			rs.close();
		}
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
		StringWriter out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
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
		StringWriter out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
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
	    
	    //Remove the user so that this test works next time
	    ResultSet rs = null;
  		rs = db.getUserfromUsers("testValidNewUser");
  		rs.next();
		int uID = rs.getInt("userID");
		db.deleteUserfromUsers(uID);
		if(rs!=null) {
			rs.close();
		}
	}
}
