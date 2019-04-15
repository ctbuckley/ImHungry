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

@WebServlet("/restaurantDetails")
public class RestaurantDetailsPageServlet extends HttpServlet {
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
		// Arrnum allows backend to know which restaurant the user is viewing
		int arrNum = Integer.parseInt(request.getParameter("arrNum"));
		Restaurant r = restaurantResults[arrNum];

		
		String addToListParam;
		if ((addToListParam = request.getParameter("listType")) != null) {
			// When "Add to List" Button is clicked
			// Add to list only if the restaurant is not in other lists
			UserList[] userLists = (UserList[]) session.getAttribute("userLists");
			switch (addToListParam.charAt(0)) {
			case 'f':
				// Adding to favorite list
				if (!userLists[1].contains(r) && !userLists[2].contains(r)) {
					userLists[0].add(r);					
				}
				break;
			case 'd':
				// Adding to do not show list
				if (!userLists[0].contains(r) && !userLists[2].contains(r)) {
					userLists[1].add(r);
				}
				break;
			case 't':
				// adding to To Explore list
				if (!userLists[0].contains(r) && !userLists[1].contains(r)) {
					userLists[2].add(r);
				}
				break;
			}
			// Update the userLists variable in session
			session.setAttribute("userLists", userLists);
		}
		// Pass restaurant object and arrNum to jsp
		session.setAttribute("restaurantVal", restaurantResults[arrNum]);
		session.setAttribute("arrNum", arrNum);

		RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/restaurantDetails.jsp");
		dispatch.forward(request,  response);			
	}

}
