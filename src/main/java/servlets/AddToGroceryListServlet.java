package servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import data.Database;

@WebServlet("/AddToGroceryList")
public class AddToGroceryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		
		String itemsParameter = request.getParameter("groceryListItems");
		itemsParameter = java.net.URLDecoder.decode(itemsParameter, StandardCharsets.UTF_8.name());
		Gson gson = new Gson();
		ArrayList<String> groceryListItems = gson.fromJson(itemsParameter, new TypeToken<ArrayList<String>>(){}.getType());
		try {
			Database db = new Database();
			int userID = db.getUserfromUsers(username);
			for(int i = 0; i < groceryListItems.size(); i++) {
				db.insertIngredientintoGrocery(userID, groceryListItems.get(i));
			}
			
	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
