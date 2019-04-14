package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import data.*;

@WebServlet("/listManagement")
public class ListManagementPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserList[] userLists = (UserList[]) session.getAttribute("userLists");
		
		//load data from database
		
		if (userLists == null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/search.jsp");
			dispatch.forward(request,  response);
			return;
		}
		String listType = request.getParameter("listName");
//		System.out.println("ListType: " + listType.charAt(0));
		
		String op = request.getParameter("opType");
//		Check if user wants to move or remove an item from a list
		if (op != null) {
//			Get Variables to help move the item
			String recOrRest = request.getParameter("recOrRest");
			String sFromList = request.getParameter("fromList");
			int listNum = -1;
//			Map the letter name of list to an int
			if(sFromList.equals("f")) {
				listNum = 0;
			}
			else if(sFromList.equals("d")) {
				listNum = 1;
			}
			else {
				listNum = 2;
			}
//			Get position of item in question from current list
			int arrNum = Integer.parseInt(request.getParameter("arrNum"));
//			Get either recipe lists or restaurant lists from the Userlists in question
			UserList fromList = userLists[listNum];

			
			if(op.equals("r")) {
				if(recOrRest.equals("rec")) {
					fromList.remove(fromList.getRecipes().get(arrNum));
				}
				else {
					fromList.remove(fromList.getRestaurants().get(arrNum));
				}
			}else {
				
				// Calculate the new list value
				int toListNum = -1;
				if(op.equals("f")) {
					toListNum = 0;
				}
				else if(op.equals("d")) {
					toListNum = 1;
				}
				else {
					toListNum = 2;
				}
				UserList toList = userLists[toListNum];
				
				// Decide whether the data type is a Recipe or Restaurant.
				if(recOrRest.equals("rec")) {
					if(!toList.contains(fromList.getRecipes().get(arrNum))) {
						toList.add(fromList.getRecipes().get(arrNum));
						fromList.remove(fromList.getRecipes().get(arrNum));
					}
				}
				else {
					if(!toList.contains(fromList.getRestaurants().get(arrNum))) {  // Check if the list already has the item
						toList.add(fromList.getRestaurants().get(arrNum));  // Add the item to the new list
						fromList.remove(fromList.getRestaurants().get(arrNum));  // Remove the item from the old list
					}
				}
				
			}
		}

		// Pass list to display to jsp
		if (listType != null) { // Check to see if the user wanted to go to another list
			switch (listType.charAt(0)) {
			case 'f': // User wants to go to favorites list
				request.setAttribute("listVal", userLists[0]); // Send the userList object that contains both restaurant and recipe files
				request.setAttribute("listName", "Favorites"); // Send the list name
				session.setAttribute("restaurants", userLists[0].getRestaurants()); // So that when user clicks on item, it shows in the details page
				session.setAttribute("recipes", userLists[0].getRecipes()); // Same as previous comment
				
				break;
			case 'd': // User wants to go to Do not Show list
				request.setAttribute("listVal", userLists[1]); // Send the userList object that contains both restaurant and recipe files
				request.setAttribute("listName", "Don't Show"); // Send the list name
				session.setAttribute("restaurants", userLists[1].getRestaurants()); // So that when user clicks on item, it shows in the details page
				session.setAttribute("recipes", userLists[1].getRecipes());
				
				break;
			case 't': // User wants to go to To Explore list
				request.setAttribute("listVal", userLists[2]); // Send the userList object that contains both restaurant and recipe files
				request.setAttribute("listName", "To Explore"); // Send the list name
				session.setAttribute("restaurants", userLists[2].getRestaurants()); // So that when user clicks on item, it shows in the details page
				session.setAttribute("recipes", userLists[2].getRecipes()); // Same as previous comment
				
				break;
			}			
		}

		session.setAttribute("userLists", userLists); // Send the entire array of lists to session, so that we can access any item on front end 
		RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/listManagement.jsp");
		dispatch.forward(request,  response);
	}

}
