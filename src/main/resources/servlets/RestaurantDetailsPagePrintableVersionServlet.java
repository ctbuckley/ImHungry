package servlets;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Restaurant;
import data.UserList;

/**
 * Servlet implementation class RestaurantDetailsPagePrintableVersionServlet
 */

@WebServlet("/restaurantDetailsPagePrintableVersion")
public class RestaurantDetailsPagePrintableVersionServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// if restaurant results are not stored in session (meaning session has expired), 
		//  send the user back to the search page
		Restaurant[] restaurantResults = (Restaurant[]) session.getAttribute("restaurantResults");
		if (restaurantResults == null) {
			// if restaurant results are not stored in session (meaning session has expired), 
			//  send the user back to the search page
			RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/search.jsp");
			dispatch.forward(request,  response);
			return;
		}
		int arrNum = Integer.parseInt(request.getParameter("arrNum"));
		Restaurant r = restaurantResults[arrNum];

		request.setAttribute("restaurantVal", r);

		RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/restaurantDetailsPrintableVersion.jsp");
		dispatch.forward(request,  response);			
	}
}
