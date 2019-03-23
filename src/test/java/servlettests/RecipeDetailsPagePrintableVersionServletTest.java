package servlettests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import data.Recipe;
import servlets.RecipeDetailsPagePrintableVersionServlet;

/*
 * Tests for the RecipeDetailsPagePrintableVersionServlet servlet.
 */
public class RecipeDetailsPagePrintableVersionServletTest {


	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;

	@Mock
	RequestDispatcher rd;
	
	private Recipe recipe1;
	private Recipe recipe2;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		rd = mock(RequestDispatcher.class);
		
		when(request.getSession()).thenReturn(session);
		
		String name = "Good Food";
		String pictureUrl = "http://www.todayifoundout.com/wp-content/uploads/2017/11/rick-astley.png";
		double prepTime = 10;
		double cookTime = 25;
		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add("1 teaspoon ground ginger");
		ingredients.add("1 rack of lamb");
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("Throw in a pan.");
		instructions.add("Cook until done.");
		double rating = 4.5;
		
		recipe1 = new Recipe(name, pictureUrl, prepTime, cookTime, ingredients, instructions, rating);
		recipe2 = new Recipe("Not" + name, pictureUrl, prepTime, cookTime, ingredients, instructions, rating);
	}

	/*
	 *  Test to make sure that the correct Recipe is being selected by the servlet and that it is redirecting to the correct location.
	 */
	@Test
	public void testStandard() throws Exception {

        when(request.getRequestDispatcher("/jsp/recipeDetailsPrintableVersion.jsp")).thenReturn(rd);
        
        Recipe[] results = new Recipe[2];
		results[0] = recipe1;
		results[1] = recipe2;
        
        when(session.getAttribute("recipeResults")).thenReturn(results);
        when(request.getParameter("arrNum")).thenReturn("1");
		
		new RecipeDetailsPagePrintableVersionServlet().service(request, response);

		verify(rd).forward(request, response);
		verify(request).setAttribute(ArgumentMatchers.eq("recipeVal"), ArgumentMatchers.eq(results[1]));


	}
	
	/*
	 * Test to make sure that the servlet will redirect back to the search page if the session expired.	
	 */
	@Test
	public void testExpiredSession() throws Exception {
		
        when(request.getRequestDispatcher("/jsp/search.jsp")).thenReturn(rd);
        when(session.getAttribute("recipeResults")).thenReturn(null);

		new RecipeDetailsPagePrintableVersionServlet().service(request, response);

		verify(rd).forward(request, response);

	}
}
