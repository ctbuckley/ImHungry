package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import api.AccessYelpAPI;
import api.GoogleImageSearch;
import api.Scrapper;
import data.Database;
import data.Recipe;
import data.Restaurant;
import data.UserList;

/*
 * Back-end code for generating the Results Page
 */
@WebServlet("/results")
public class ResultsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/*
	 * service method is invoked whenever user attempts to 
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("resultsOrList", "results");
		UserList[] userLists = (UserList[]) session.getAttribute("userLists");
		
		// input validation should be done on front end (empty string, non-integer for resultCount, etc.)
		String searchTerm = request.getParameter("q");
		String resultCountRaw = request.getParameter("n");
		String radiusRaw = request.getParameter("radiusInput");
		String pageNumberRaw = request.getParameter("pageNumber");
		String fromSearch = request.getParameter("fromSearch");
		Integer resultCount = null;
		Integer radius = null;
		Integer pageNumber = null;
		
		UserList pastSearchList = (UserList)session.getAttribute("pastSearchList");
		
		//Pull results from session 
		boolean dataIsCached = false;
		if (pastSearchList != null && fromSearch == null) {
			dataIsCached = true;
		}
		
		/*
		 * If no UserList array is stored in session, the server considers the user to be a new user,
		 *  and makes new userList array for this session. 
		 * Else, retrieve UserList array from session.
		 */
		if (userLists == null) {
			userLists = new UserList[3];
			for (int i = 0; i < 3; ++i) {
				userLists[i] = new UserList();
			}
			session.setAttribute("userLists", userLists);
		}
		UserList favoriteList = userLists[0];
		UserList doNotShowList = userLists[1];
		ArrayList<Restaurant> doNotShowRestaurants = doNotShowList.getRestaurants();
		ArrayList<Recipe> doNotShowRecipes = doNotShowList.getRecipes();
		
		/*
		 *  If user clicked "return to search", get parameters from session.
		 * 	Else, get parameters from url
		 */
		if (searchTerm == null) {
			searchTerm = (String) session.getAttribute("searchTerm");
		}
		if (resultCountRaw == null) {
			resultCount = (Integer) session.getAttribute("n");
		} else {
			resultCount = Integer.parseInt(resultCountRaw);
			session.setAttribute("n", resultCount);
		}
		
		if (radiusRaw == null) {
			radius = (Integer) session.getAttribute("radiusInput");
		} else {
			radius = Integer.parseInt(radiusRaw);
		}
		
		if (pageNumberRaw == null) {
			pageNumber = 1;
			System.out.println("Couldn't find page number");
		} else {
			System.out.println("Found page number: " + pageNumberRaw);
			pageNumber = Integer.parseInt(pageNumberRaw);
		}
	
		
		if(dataIsCached && (Math.max(doNotShowRecipes.size(), doNotShowRestaurants.size()) + resultCount > Math.min(pastSearchList.getRecipes().size(), pastSearchList.getRestaurants().size())) ){
			dataIsCached = false;
		}
		
		UserList currentResults = new UserList();
		
		/* 
		 * Fetch a list of restaurant objects made from query results given by Yelp API
		 * Get enough results to make up for restaurants/recipes in Do Not Show list, which will not be displayed
		 */
		
		Vector<Restaurant> restaurants = null;
		
		if (dataIsCached) {
			restaurants = new Vector<Restaurant>(pastSearchList.getRestaurants());
		} else {
			restaurants = AccessYelpAPI.YelpRestaurantSearch(searchTerm, resultCount + doNotShowRestaurants.size(), radius);
			for (Restaurant i : restaurants) {
				currentResults.add(i);
			}
		}
		
		restaurants = sortList(restaurants, doNotShowList, favoriteList);
		
		/* 
		 * Fetch a list of recipe objects made by web scraping from allrecipes.com
		 */
		
		Vector<Recipe> recipes = null;
		
		if (dataIsCached) {
			recipes = new Vector<Recipe>(pastSearchList.getRecipes());
		} else {
			recipes = Scrapper.search(searchTerm, resultCount + doNotShowRecipes.size());
			for (Recipe i : recipes) {
				currentResults.add(i);
			}
		}
		
		recipes = sortList(recipes, doNotShowList, favoriteList);
		
		// vector size should be resultCount (discard extra data)
		restaurants.setSize(resultCount);
		recipes.setSize(resultCount);
		
		
		// The size of a page for pagination;
		int pageSize = 10;
		int pageCount = (resultCount + pageSize - 1)/pageSize;

		int startingIndex = (pageNumber - 1) * pageSize;
		int endingIndex = Math.min(pageNumber * pageSize, resultCount);
		
		resultCount = endingIndex - startingIndex;
		
		// Make vectors into arrays and pass to jsp as attributes
		Restaurant[] restaurantArr = new Restaurant[resultCount];
		restaurants.subList(startingIndex, endingIndex).toArray(restaurantArr);
		Recipe[] recipeArr = new Recipe[resultCount];
		recipes.subList(startingIndex, endingIndex).toArray(recipeArr);

		// Google Image Search to make collage of images
		// array of image URLs passed to jsp as "imageUrlVec"
		Database db;
		Vector<String> imageUrlVec = new Vector<String>();
		
		try {
			db = new Database();
			boolean queryImagesExist = db.queryImagesExist(searchTerm);
			
	        if (queryImagesExist) {
	        	ArrayList<String> imgURLs = new ArrayList<String>();
	            imgURLs = db.getLinksfromImages(searchTerm);
	            for (String i : imgURLs) {
	            	imageUrlVec.add(i);
	            }
	        } else {
	        	imageUrlVec = GoogleImageSearch.GetImagesFromGoogle(searchTerm);
	            for(String i : imageUrlVec) {
	                db.insertLinkintoImages(searchTerm, i);
	            }
	        }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] imageUrlArr = new String[imageUrlVec.size()];
		imageUrlVec.toArray(imageUrlArr);
		
		// Pass variables needed for generating front-end
		request.setAttribute("imageUrlVec", imageUrlArr);
		request.setAttribute("restaurantArr", restaurantArr);
		request.setAttribute("recipeArr", recipeArr);
		request.setAttribute("searchTerm", searchTerm);
		request.setAttribute("resultCount", resultCount);
		// store result arrays in session -> used for details page
		session.setAttribute("restaurantResults", restaurantArr);
		session.setAttribute("recipeResults", recipeArr);
		// store searchTerm and resultCount -> used when user clicks "Return to Search"
		session.setAttribute("searchTerm", searchTerm);
		session.setAttribute("resultCount", resultCount);
		session.setAttribute("radiusInput", radius);
		session.setAttribute("pageCount", pageCount);
		
		if (!dataIsCached) {
			session.setAttribute("pastSearchList", currentResults);
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/results.jsp");
		dispatch.forward(request,  response);			
	}
	
	private <T extends Comparable<T>> Vector<T> sortList(Vector<T> original, UserList doNotShowList, UserList favoritesList){
		
		Vector<T> returnValue = new Vector<T>();
		Collections.sort(original);
		
		Object currObject;
		// Check for objects in Favorite list, and put them on top
		for (int i = 0; i < original.size(); ++i) {
			
			currObject = original.get(i);
			
			if(doNotShowList.contains(currObject)) {
				original.remove(currObject);
				i--;
			}
						
			if (favoritesList.contains(currObject)) {
				returnValue.add((T) currObject);
				original.remove(currObject);
				i--;
			}
		}
		
		for (T item : original) {
			returnValue.add(item);
		}
		
		return returnValue;
	}
	
}