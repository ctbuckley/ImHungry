
package servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Database;

@WebServlet("/DisplayGroceryList")
public class DisplayGroceryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		
		String[] groceryList = new String[0];

		try {
			Database db = new Database();
			ResultSet rs = db.getUserfromUsers(username);
			rs.next();
			int userID = rs.getInt("userID");
			rs.close();
			ArrayList<String> currentGroceryItems = db.getGroceryListforUser(userID);
			
			groceryList = new String[currentGroceryItems.size()];
			currentGroceryItems.toArray(groceryList);
	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		session.setAttribute("groceryList", groceryList);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/groceryList.jsp");
		dispatch.forward(request,  response);	
	}
}