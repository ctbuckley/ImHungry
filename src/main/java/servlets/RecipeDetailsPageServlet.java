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
import data.Recipe;
import data.Restaurant;
import data.UserList;

@WebServlet("/recipeDetails")
public class RecipeDetailsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {

			Database db = new Database();

			String username = (String)session.getAttribute("username");
			ResultSet rs = db.getUserfromUsers(username);
			rs.next();
			int userID = rs.getInt("userID");
			
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
			// Arrnum allows backend to know which recipe the user is viewing
			int arrNum = Integer.parseInt(request.getParameter("arrNum"));
			Recipe r = recipeResults[arrNum];
	
			Integer itemID = (Integer)session.getAttribute("itemID");
			
			String addToListParam;
			if ((addToListParam = request.getParameter("listType")) != null) {
				// When "Add to List" Button is clicked
				// Add to list only if the recipe is not in another list
				UserList[] userLists = (UserList[]) session.getAttribute("userLists");
				switch (addToListParam.charAt(0)) {
				case 'f':
					// Adding to favorite list
					if (!userLists[1].contains(r) && !userLists[2].contains(r)) {
						userLists[0].add(r);
						db.insertItemintoList(userID, itemID, "To Be Explored");					
					}
					break;
				case 'd':
					// Adding to do not show list
					if (!userLists[0].contains(r) && !userLists[2].contains(r)) {
						userLists[1].add(r);
						db.insertItemintoList(userID, itemID, "To Be Explored");
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
			// Pass recipe object and arrNum to jsp
		session.setAttribute("recipeVal", recipeResults[arrNum]);
		session.setAttribute("arrNum", arrNum);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/recipeDetails.jsp");
		dispatch.forward(request,  response);				
	}
}
