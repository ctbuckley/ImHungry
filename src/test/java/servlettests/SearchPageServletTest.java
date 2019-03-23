package servlettests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import servlets.SearchPageServlet;

/*
 * Tests for the SearchPageServlet class.
 */
public class SearchPageServletTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;

	@Mock
	RequestDispatcher rd;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		rd = mock(RequestDispatcher.class);
		
	}

	/*
	 * Test to make sure that the servlet redirects to the correct location.
	 */
	@Test
	public void test() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/search.jsp")).thenReturn(rd);

		new SearchPageServlet().service(request, response);

		verify(rd).forward(request, response);

	}
}
