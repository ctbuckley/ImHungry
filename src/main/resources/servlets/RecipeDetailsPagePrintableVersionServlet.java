package servlets;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Recipe;
import data.UserList;

/**
 * Servlet implementation class RecipeDetailsPagePrintableVersionServlet
 */

@WebServlet("/recipeDetailsPagePrintableVersion")
public class RecipeDetailsPagePrintableVersionServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// if Recipe results are not stored in session (meaning session has expired), 
		//  send the user back to the search page
		Recipe[] recipeResults = (Recipe[]) session.getAttribute("recipeResults");
		if (recipeResults == null) {
			// if recipe results are not stored in session (meaning session has expired), 
			//  send the user back to the search page
			RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/search.jsp");
			dispatch.forward(request,  response);
			return;
		}
		int arrNum = Integer.parseInt(request.getParameter("arrNum"));
		Recipe r = recipeResults[arrNum];
		
		request.setAttribute("recipeVal", r);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/recipeDetailsPrintableVersion.jsp");
		dispatch.forward(request,  response);				
	}

}
