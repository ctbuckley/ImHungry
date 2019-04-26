package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Database;

/**
 * Servlet implementation class CheckGroceryItemServlet
 */
@WebServlet("/CheckGroceryItem")

public class CheckGroceryItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		System.out.println("Inside CheckGroceryList Servlet");
		String item = request.getParameter("item");
		try {
			Database db = new Database();
			int userID = db.getUserfromUsers(username);
			db.changeCheckonGroceryItem(userID, item);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
