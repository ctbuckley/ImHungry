
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
			int userID = db.getUserfromUsers(username);
			ArrayList<String> currentGroceryItems = db.getGroceryListforUser(userID); // "1 cup of x"
			ArrayList<Integer> quantity = new ArrayList<Integer>(); //"2"
			ArrayList<Integer> check = new ArrayList<Integer>(); //"0" or "1"
			
			for(int i=0; i<currentGroceryItems.size(); i++) {
				String item = currentGroceryItems.get(i);
				int quant = db.getGroceryItemQuantity(userID, item);
				quantity.add(quant);
				check.add(db.getCheckonGroceryItem(userID, item));
				 
				if (quant > 1) {
					item = mergeItems(item, quant);
				}
				
				currentGroceryItems.set(i, item);
			}
			
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
	
	private String mergeItems(String item, int quant) {
		String[] splitted = item.split(" "); //"1 3/4 cup of water"
		double num = 0;
		double frac = 0;
		String theRest = "";
		boolean updated = false;
		
		for(int i=0; i<splitted.length; i++) {
			String str = splitted[i];
			if (str.contains("/")) {
		        String[] fracString = str.split("/");
		        frac = Double.parseDouble(fracString[0]) / Double.parseDouble(fracString[1]);
		    }else {
		    	try 
		        { 
		            // checking valid integer using parseInt() method 
		            num = Integer.parseInt(str); 
		        }  
		        catch (NumberFormatException e)  
		        { 
		        	String units = "cup teaspoon tsp tablespoon tbsp spoonful dash quart "
		        			+ "gallon clove can cube jar egg head stalk pound lb ounce oz "
		        			+ "package pack bowl plate breast leg thigh wing rib carton jug"
		        			+ "sprig ball slice cut drop wedge filet milliliter gram pint";
		        	if(units.contains(str) && (num+frac)*quant > 1 && !updated) {
		        		updated = true;
		        		theRest += str + "s ";
		        	}else {
		        		theRest += str + " ";
		        	}
		        	
		            continue; 
		        } 
		    }
			
		}
		
		num += frac;
		if(num==0) {
			//nothing in front
			return item;
		}
		num *= quant;

		return (num + " " + theRest);
	}
	
}