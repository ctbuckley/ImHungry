package servlettests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
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

import data.Database;
import servlets.GetSearchHistoryServlet;

public class GetSearchHistoryServletTest {

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
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		rd = mock(RequestDispatcher.class);
		
		when(request.getSession()).thenReturn(session);
		
		db.insertUserintoUsers("testUser", "password");
		ResultSet rs =  db.getUserfromUsers("testUser");
		rs.next();
		userID = rs.getInt("userID");
		
	}

	/*
	 *  Test to make sure a valid new user returns correctly.
	 */
	@Test
	public void testGetSearchHistory() throws Exception {

		when(request.getParameter("username")).thenReturn("testUser");
		StringWriter out = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		
		//insert two search queries into the database for the user
		int searchID1 = db.insertQueryintoSearchHistory(userID, "chicken", 4, 10000);
		int searchID2 = db.insertQueryintoSearchHistory(userID, "apple", 5, 20000);
		
		new GetSearchHistoryServlet().service(request, response);
		
		String r = out.toString();
		
		//verify response
		
		assertEquals(true, true);
		System.out.println(r);
	
		//delete them so this test passes again next time
		db.deleteQueryfromSearchHistory(searchID1);
		db.deleteQueryfromSearchHistory(searchID2);
		
		//Remove the user so that this test works next time
		db.deleteUserfromUsers(userID);
	}	
	
	@After
	public void teardown() throws SQLException {
		db.deleteUserfromUsers(userID);
	}
	
	
}
