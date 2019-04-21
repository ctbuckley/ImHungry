package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import data.Database;
import data.SearchItem;

@WebServlet("/GetSearchHistory")
public class GetSearchHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		response.setContentType("application/json");
		
		try {
			Database db = new Database();
			PrintWriter out = response.getWriter();
			int userID = db.getUserfromUsers(username);
			ArrayList<SearchItem> searchItems = db.getSearchItemfromSearch(userID);
			
			Collections.reverse(searchItems);
			
			if(searchItems.size() > 0) {
				searchItems.remove(0);
			}
			
			String json = new Gson().toJson(searchItems);
			out.print(json);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
