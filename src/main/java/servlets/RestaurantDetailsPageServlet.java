package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Database;
import data.Restaurant;
import data.UserList;

@WebServlet("/restaurantDetails")
public class RestaurantDetailsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		try {

			Database db = new Database();

			String username = (String)session.getAttribute("username");
			ResultSet rs = db.getUserfromUsers(username);
			rs.next();
			int userID = rs.getInt("userID");
			
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
	
			Integer itemID = (Integer)session.getAttribute("itemID");
			
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
						db.insertItemintoList(userID, itemID, "Favorites");
					}
					break;
				case 'd':
					// Adding to do not show list
					if (!userLists[0].contains(r) && !userLists[2].contains(r)) {
						userLists[1].add(r);
						db.insertItemintoList(userID, itemID, "Do Not Show");
					}
					break;
				case 't':
					// adding to To Explore list
					if (!userLists[0].contains(r) && !userLists[1].contains(r)) {
						userLists[2].add(r);
						db.insertItemintoList(userID, itemID, "To Be Explored");
						
					}
					break;
				}
				// Update the userLists variable in session
				session.setAttribute("userLists", userLists);
			}
			// Pass restaurant object and arrNum to jsp
			request.setAttribute("restaurantVal", restaurantResults[arrNum]);
			request.setAttribute("arrNum", arrNum);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/restaurantDetails.jsp");
		dispatch.forward(request,  response);			
	}

}
