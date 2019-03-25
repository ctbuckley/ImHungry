package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

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

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		rd = mock(RequestDispatcher.class);
		
		
		when(request.getSession()).thenReturn(session);
		
	}

	/*
	 *  Test to make sure a valid login returns correctly.
	 */
	@Test
	public void testValidLogin() throws Exception {

		when(request.getParameter("username")).thenReturn("master");
		when(request.getParameter("pass")).thenReturn("root");
		StringWriter out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
		new ValidateLoginServlet().service(request, response);
		
		String r = out.toString();
		
		JsonElement je = new JsonParser().parse(r);
	    JsonObject  jo = je.getAsJsonObject();
	    String result = jo.get("success").getAsString();
	    
	    assertEquals("true", result);
	}
	
	/*
	 *  Test to make sure an invalid login returns correctly
	 */
	
	@Test
	public void testInvalidLogin() throws Exception {

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
	
	
	
}
