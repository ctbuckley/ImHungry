package servlets;

import java.io.IOException;
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
		
		
		/*
		 * Sort restaurants in ascending order of drive time from Tommy Trojan,
		 * using compareTo method overridden in Restaurant class
		 */
		Collections.sort(restaurants);
		Restaurant currRestaurant;
		int insertIndex = 0;
		// Check for restaurants in Favorite list, and put them on top
		for (int i = 0; i < restaurants.size(); ++i) {
			currRestaurant = restaurants.get(i);
			System.out.println(currRestaurant.getName());
			if (favoriteList.contains(currRestaurant)) {
				System.out.println("is in the favorite list");
				restaurants.remove(i);
				restaurants.add(insertIndex, currRestaurant);
				for (int j = 0; j < restaurants.size(); ++j) {
					System.out.print(restaurants.get(j).getName() + "  ");
				}
				System.out.println("");
				++insertIndex;
			}
		}
		// Check for restaurants in Do Not Show list, and remove them, 
		// 	assuming a restaurant cannot be in more than one predefined list
		for (int i = insertIndex; i < restaurants.size(); ++i) {
			currRestaurant = restaurants.get(i);
			if (doNotShowList.contains(currRestaurant)) {
				restaurants.remove(i);
				--i;
			}
		}
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
		
		
		/*
		 * Sort recipes in ascending order of prep time,
		 * using compareTo method overridden in Recipe class
		 */
		Collections.sort(recipes);
		Recipe currRecipe;
		insertIndex = 0;
		// Check for recipes in Favorite list, and put them on top
		for (int i = 0; i < recipes.size(); ++i) {
			currRecipe = recipes.get(i);
			System.out.println(currRecipe.getName());
			if (favoriteList.contains(currRecipe)) {
				System.out.println("is contained in favorite list");
				recipes.add(insertIndex, currRecipe);
				recipes.remove(i+1);
				++insertIndex;
			}
		}
		// Check for recipes in Do Not Show list, and remove them, 
		// 	assuming a recipe cannot be in more than one predefined list
		for (int i = insertIndex; i < recipes.size(); ++i) {
			currRecipe = recipes.get(i);
			if (doNotShowList.contains(currRecipe)) {
				recipes.remove(i);
				--i;
			}
		}
		// vector size should be resultCount (discard extra data)
		restaurants.setSize(resultCount);
		recipes.setSize(resultCount);
		
		// The size of a page for pagination;
		int pageSize = 5;
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
		Vector<String> imageUrlVec = GoogleImageSearch.GetImagesFromGoogle(searchTerm);
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
}