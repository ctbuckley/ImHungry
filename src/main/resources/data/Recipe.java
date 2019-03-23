package data;
import java.io.Serializable;
import java.util.ArrayList;

//Implements Serializable Interface to allow storing in session 
public class Recipe implements Comparable<Recipe>, Serializable {
	private static final long serialVersionUID = 1L;

	// String variables = "", numeric variables = -1 if data is not available from allrecipes.com
	private String name = "";
	private String pictureUrl = "";
	private double prepTime = -1;
	private double cookTime = -1;
	private ArrayList<String> ingredients;
	private ArrayList<String> instructions;
	private double rating = -1;
	
	// Constructor
	public Recipe(String name, String pictureUrl, double prepTime, double cookTime, ArrayList<String> ingredients,
			ArrayList<String> instructions, double rating) {
		this.name = name;
		this.pictureUrl = pictureUrl;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.ingredients = ingredients;
		this.instructions = instructions;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public double getPrepTime() {
		return prepTime;
	}

	public double getCookTime() {
		return cookTime;
	}

	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	public ArrayList<String> getInstructions() {
		return instructions;
	}

	public double getRating() {
		return rating;
	}
	/*
	 * comparing method to sort recipe results in ascending order of prep time
	 */
	public int compareTo(Recipe arg0) {
		return (int) (this.getPrepTime() - arg0.getPrepTime());
	}
	/*
	 * Equals overridden for vector::contains() method
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Recipe) {
			Recipe o = (Recipe) obj;
			return (this.name.equals(o.name));
		}
		else {
			return false;
		}
	}
}
