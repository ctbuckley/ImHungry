package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Database;

@WebServlet("/ListReorder")
public class ListReorderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String oldIndexString = request.getParameter("oldIndex");
		String newIndexString = request.getParameter("newIndex");
		String listName = request.getParameter("listName");
	
		try {
			Database db = new Database();
			int userID = db.getUserfromUsers(username);
			db.swapItemIndex(Integer.parseInt(oldIndexString) + 1, Integer.parseInt(newIndexString) + 1, username, listName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
