package servlets;

import java.io.IOException;

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
		UserList[] userLists = (UserList[])session.getAttribute("userLists");
		int listIndex = -1;
		
		try {
			listIndex = Integer.parseInt(request.getParameter("listIndex"));
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
		
		if(listIndex > 2) listIndex = -1;
		
		if (userLists == null || listIndex < 0) {
			RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/search.jsp");
			dispatch.forward(request,  response);
			return;
		}
//		System.out.println("ListType: " + listType.charAt(0));
		
		String op = request.getParameter("opType");
//		Check if user wants to move or remove an item from a list
		if (op != null) {
//			Get Variables to help move the item
			String recOrRest = request.getParameter("recOrRest");

//			Get position of item in question from current list
			int arrNum = Integer.parseInt(request.getParameter("arrNum"));
//			Get either recipe lists or restaurant lists from the Userlists in question
			UserList fromList = userLists[listIndex];

			
			if(op.equals("r")) {
				if(recOrRest.equals("rec")) {
					fromList.remove(fromList.getRecipes().get(arrNum));
				}
				else {
					fromList.remove(fromList.getRestaurants().get(arrNum));
				}
			} else {
				
				// Calculate the new list value
				int toListNum = -1;
				if(op.equals("f")) {
					toListNum = 0;
				} else if(op.equals("d")) {
					toListNum = 1;
				} else if(op.equals("t")) {
					toListNum = 2;
				}
				
				if(toListNum > -1) {
					UserList toList = userLists[toListNum];
					
					// Decide whether the data type is a Recipe or Restaurant.
					if(recOrRest.equals("rec")) {
						moveItem(fromList.getRecipes().get(arrNum), fromList, toList);
					} else {
						moveItem(fromList.getRestaurants().get(arrNum), fromList, toList);
					}
				}
				
			}
		}
		
		request.setAttribute("listVal", userLists[listIndex]); // Send the userList object that contains both restaurant and recipe files
		request.setAttribute("listIndex", listIndex); // Send the list name
		session.setAttribute("restaurants", userLists[listIndex].getRestaurants()); // So that when user clicks on item, it shows in the details page
		session.setAttribute("recipes", userLists[listIndex].getRecipes()); // Same as previous comment

		session.setAttribute("userLists", userLists); // Send the entire array of lists to session, so that we can access any item on front end 
		RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/listManagement.jsp");
		dispatch.forward(request,  response);
	}
	
	private <T> void moveItem(T item, UserList fromList, UserList toList) {
		if(!toList.contains(item)) {  // Check if the list already has the item
			toList.add(item);  // Add the item to the new list
			fromList.remove(item);  // Remove the item from the old list
		}
	}
	
}
