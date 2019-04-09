package api;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.Recipe;

public class Scrapper {

	// Returns a vector of recipes of size n that satisfy the search.
	public static Vector<Recipe> search(String searchTerm, int n) throws IOException{
	
		searchTerm = URLEncoder.encode(searchTerm, "UTF-8");
		
		Vector<Recipe> recipes = new Vector<Recipe>();
		ArrayList<String> urls = new ArrayList<String>();
		
		String baseUrl =  "https://allrecipes.com/search/results/?wt=" + searchTerm + "&sort=re";
		int currentPage = 1;
		String pagUrl = baseUrl + "&page=" + currentPage;
		Document doc = Jsoup.connect(pagUrl).get();
		Element elem = doc.getElementById("fixedGridSection");
		if(elem == null) {
			return recipes;
		}
		
		Elements recipeBoxes = elem.getElementsByClass("fixed-recipe-card__info");
					
		
		for(int i = 0; i < n; i++) {
			
			try {
				Element recipeBox = recipeBoxes.get(i);
				
				Elements links = recipeBox.getElementsByTag("a");
				String link = links.get(0).attr("href");
				urls.add(link);
				
			} catch (IndexOutOfBoundsException e) {
				
				wait(1000);
									
				currentPage++;
				try {
					doc = Jsoup.connect(baseUrl + "&page=" + currentPage).get();
					elem = doc.getElementById("fixedGridSection");
					recipeBoxes = elem.getElementsByClass("fixed-recipe-card__info");
				} catch(NullPointerException el) {
					System.out.println("Failed for page " + baseUrl + "&page=" + currentPage);
			    	el.printStackTrace();
			    	break;
				}
				
				n -= i - 1;
				i = 0;
				
			}
		}
		
		System.out.println("Urls: " + urls.size());
		
		for(String url : urls) {
			recipes.add(Scrapper.get(url));
		}
		
		return recipes;
	}
	
	// Returns a Recipe object representation of the recipe at the url on allrecipes.com.
	public static Recipe get(String url) throws IOException {
		
		System.out.println("Starting to scrape " + url);
		wait(1000);
		
		Document doc = Jsoup.connect(url).get();
		
		// Read recipe name
		Element recipeName = doc.getElementById("recipe-main-content");
		String name = recipeName.text();
		
		// Read recipe photo
		Elements recipePhoto = doc.getElementsByClass("rec-photo");
		String pictureUrl = recipePhoto.get(0).attr("src");
		
		// Read rating count
		Elements ratingSummary = doc.getElementsByClass("recipe-summary__stars");
		Elements rating = ratingSummary.get(0).getElementsByClass("rating-stars");
		String sRecipeRating = rating.attr("data-ratingstars");
		double recipeRating = Double.parseDouble(sRecipeRating);

		Element directions = doc.getElementsByClass("directions--section__steps").get(0);
		
		// Read prep and cook time
		Elements times = directions.getElementsByTag("time");
		String sPrepTime = "";
		String sCookTime = "";
		for(Element time: times) {
			String timeType = time.attr("itemprop");
			if(timeType.equals("prepTime")) {
				sPrepTime = time.attr("datetime");
			}
			if(timeType.equals("cookTime")) {
				sCookTime = time.attr("datetime");
			}
		}
		double cookTime = parseTime(sCookTime);
		double prepTime = parseTime(sPrepTime);
		
		// Read instruction list
		ArrayList<String> instructions = new ArrayList<String>();
		Element instructionList = directions.getElementsByClass("list-numbers recipe-directions__list").get(0);
		Elements eInstructions = instructionList.getElementsByClass("recipe-directions__list--item");
		for(Element instruction : eInstructions) {
			instructions.add(instruction.text());
		}
		
		// Read ingredient list
		ArrayList<String> ingredients = new ArrayList<String>();
		Elements eIngredients = doc.getElementsByAttributeValue("itemprop", "recipeIngredient");
		for(Element ingredient : eIngredients) {
			ingredients.add(ingredient.text());
		}
		
		
		return new Recipe(name, pictureUrl, prepTime, cookTime, ingredients, instructions, recipeRating);
	}
	
	// Returns the time in minutes of a datetime string from allrecipes.com
	public static int parseTime(String datetime) {
		
		if(datetime.equals("")) {
			return -1;
		}
		
		int retval = 0;
		
		int hIndex = datetime.indexOf('H');
		int mIndex = datetime.indexOf('M');
		
		int hours = 0;
		int minutes = 0;
		
		if(hIndex > 2) {
			hours = Integer.parseInt(datetime.substring(2, hIndex));
			if(mIndex > 2) {
				minutes = Integer.parseInt(datetime.substring(hIndex+1, mIndex));
			}
		} else if(mIndex > 2) {
			minutes = Integer.parseInt(datetime.substring(2, mIndex));
		} else {
			return -1;
		}
		
		retval = minutes + 60 * hours;
		
		return retval;
	}
	
	private static void wait(int ms) {
		long start = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - start < ms) {
		}
	}
	
}
