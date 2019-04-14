package data;
import java.io.Serializable;
import java.util.ArrayList;
/*
 * An UserList object will correspond to a predefined list (one of Favorite, Do Not Show, To Explore)
 * Total 3 UserList objects will be stored in session
 */
// Implements Serializable Interface to allow storing in session 
public class UserList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// ArrayList of restaurants and recipes in the predefined list
	private ArrayList<Restaurant> restaurants;
	private ArrayList<Recipe> recipes;
	
	// Empty when the list is created (created when a new session is started)
	public UserList() {
		restaurants = new ArrayList<Restaurant>();
		recipes = new ArrayList<Recipe>();
	}
	
	public ArrayList<Restaurant> getRestaurants() {
		return restaurants;
	}

	public ArrayList<Recipe> getRecipes() {
		return recipes;
	}

	/*
	 * add methods:
	 * Returns false if the passed object is already in the list 
	 * Returns true if successfully added to the list
	 */
	
	public boolean add(Object r) {
		if(r instanceof Recipe) {
			
			if (recipes.contains((Recipe)r)) {
				return false;
			} else {
				recipes.add((Recipe)r);
				return true;
			}
			
		} else if(r instanceof Restaurant) {
			
			if (restaurants.contains((Restaurant)r)) {
				return false;
			} else {
				restaurants.add((Restaurant)r);
				return true;
			}
			
		} else {
			System.out.println("The class in remove is not a Recipe or Restaurant.");
			return false;
		}
	}

	/*
	 * remove method:
	 * Returns true if successfully removed from the list
	 * Returns false if the passed object is not in the list
	 */
	
	public boolean remove(Object r) {
		if(r instanceof Recipe) {
			return recipes.remove(r);
		} else if(r instanceof Restaurant) {
			return restaurants.remove(r);
		} else {
			System.out.println("The class in remove is not a Recipe or Restaurant.");
			return false;
		}
	}
	

	/*
	 * contains methods:
	 * Returns true if the passed object is in the list
	 * Returns false if the passed object is not in the list
	 */
	
	public boolean contains(Object r) {
		if(r instanceof Recipe) {
			return recipes.contains(r);
		} else if(r instanceof Restaurant) {
			return restaurants.contains(r);
		} else {
			System.out.println("The class in remove is not a Recipe or Restaurant.");
			return false;
		}
	}

}
