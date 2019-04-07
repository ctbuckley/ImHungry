package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import data.Restaurant;
/*
 * Has static method YelpRestaurantSearch
 */
public class AccessYelpAPI {
	
	static String API_KEY = "w3PGnJQ06Zd1DcF_c-hrn_ZBL4mt-qQ6t3R4ytCJF5bbYJB2ORyniUL4XKZIRPDw2N9d5poklzraRrvC75Sw4LOPuxMPumVmqKFKxnqHmUxIunkHy3l-M-3wVz57XHYx";
	static String CLIENT_ID = "YourA2mR9_8h-uctIT2HFg";
	
	static double ttLat = 34.020807;	//Latitude & Longitude of tommy trojan
	static double ttLong = -118.284668;

	public static Vector<Restaurant> YelpRestaurantSearch(String searchTerm, int resultCount) throws UnsupportedEncodingException, IOException {
		return YelpRestaurantSearch(searchTerm, resultCount, 23);
	}
	/*
	 * Queries Yelp API using user-provided search term and number of results.
	 * Returns a vector of Restaurant objects, each of which contains all the information needed to display in subsequent pages
	 */
	public static Vector<Restaurant> YelpRestaurantSearch(String searchTerm, int resultCount, int radius) throws UnsupportedEncodingException, IOException {
		
		searchTerm = URLEncoder.encode(searchTerm, "UTF-8");
		radius = radius * 1760;
		if(radius > 40000) {
			radius = 40000;
		}
	
		String GET_URL = "https://api.yelp.com/v3/businesses/search?"
				+ "term=" + searchTerm // Search Term
				+ "&latitude=34.020807&longitude=-118.284668" // Coordinates of Tommy Trojan
				+ "&sort_by=distance" // Sort by distance
				+ "&categories=restaurants"
				+ "&radius=" + radius;
		
									
		Vector<Restaurant> resultsVec = new Vector<Restaurant>();
		/*
		 * Declare and initialize variables that store parsed restaurant information
		 * If data is not available, numeric values will be -1 and String values will be "NULL"
		 */
		String name = "NULL";
		String websiteUrl = "NULL";
		int price = -1;
		String price_string = "NULL";
		String phoneNumber = "NULL";
		double rating = -1;
		int drivingTime = -1;
		double distance = 0.0;
		
		String address1 = "NULL";
		String address2 = "NULL";
		String address3 = "NULL";
		String address4 = "NULL";
		
		double restLat = -1;
		double restLong = -1;
		
		int resultsLeft = resultCount;
		int limit, offset = 0;
		// Maximum results Yelp API returns is 50, so we have to query multiple times to get more than 50 restaurant information
		while (resultsLeft > 0) {
			if (resultsLeft >= 50) {
				limit = 50;
				resultsLeft -= 50;
			}
			else {
				limit = resultsLeft;
				resultsLeft = 0;
			}
			// limit is the number of results the Yelp API will return. Max is 50.
			// offset is the number of results to skip
			URL url = new URL(GET_URL + "&limit=" + limit + "&offset=" + offset);
			offset += limit;
			// Query Yelp API with constructed url
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", "Bearer" + " " +  API_KEY);	//request object, set authorization key to access yelp API
			httpCon.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
		    
		    // Parse JSON string
		    JsonParser parser = new JsonParser(); 
		    JsonObject jsonObj = (JsonObject)parser.parse(br); 
		    JsonArray jsonArr = (JsonArray) jsonObj.get("businesses");
	
		    for(int i = 0; i < limit; i++) {		//change i to get desired # of search terms
		    	JsonObject jsonobj_1 = null;
		    	try {
		    		jsonobj_1 = (JsonObject)jsonArr.get(i);
		    	} catch (IndexOutOfBoundsException e) {
		    		System.out.println("Could not get " + resultCount + " results from the Yelp API for query\n" + GET_URL);	
		    		break;
		    	}
		    	// Parse restaurant name
		    	if(jsonobj_1.get("name") != null) {
		    		name = jsonobj_1.get("name").toString();
		    		name = name.replace("\"", "");
		    	}
		    	// Parse restaurant website url (yelp page)
		    	if(jsonobj_1.get("url") != null) {		//returns URL to yelp restaurant page
		    		websiteUrl = jsonobj_1.get("url").toString();	//no way to grab URL of restaurant website
		    		websiteUrl = websiteUrl.replace("\"", "");
		    	}
		    	// Parse the price level of the restaurant
		    	if(jsonobj_1.get("price") != null) {
		    		price_string = jsonobj_1.get("price").getAsString();
		    		price_string = price_string.replace("\"", "");
		    		price = price_string.length();		//price represents the number of dollar signs ($) returned from Yelp
		    	}
		    	// Parse restaurant's phone number
		    	if(jsonobj_1.get("phone") != null && jsonobj_1.get("phone").getAsString().length() > 0) {
		    		phoneNumber = jsonobj_1.get("phone").toString();
		    		phoneNumber = phoneNumber.replace("\"", "");
		    		phoneNumber = phoneNumber.substring(1, 1) + "(" + phoneNumber.substring(2,5) + ")-" + phoneNumber.substring(5,8) + "-" + phoneNumber.substring(8,phoneNumber.length());
		    	}
		    	// Parse Yelp rating of the restaurant
		    	if(jsonobj_1.get("rating") != null) {
		    		rating = jsonobj_1.get("rating").getAsDouble();
		    		// rating = rating.replace("\"", "");
		    	}
		    	// Parse restaurant address, which is divided into 4 fields
		    	if(jsonobj_1.get("location") != null) {
		    		JsonObject jsonobj_2 = (JsonObject) jsonobj_1.get("location");
		    		// Main address
		    		if(jsonobj_2.get("address1") != null) {
		    			address1 = jsonobj_2.get("address1").toString();
		    		}
		    		// City
		    		if(jsonobj_2.get("city") != null) {
		    			address2 = jsonobj_2.get("city").toString();
		    		}
		    		// State
		    		if(jsonobj_2.get("state") != null) {
		    			address3 = jsonobj_2.get("state").toString();
		    		}
			    	// Zip Code
		    		if(jsonobj_2.get("zip_code") != null) {
		    			address4 = jsonobj_2.get("zip_code").toString();
		    		}	
		    	}
		    	
		    	if(jsonobj_1.get("distance") != null) {
		    		distance = jsonobj_1.get("distance").getAsDouble();
		    	}
		    	// Construct full address string
		    	String fullAddress = address1 + ", " + address2 + ", " + address3 + ", " + address4;
		    	fullAddress = fullAddress.replace("\"", "");
		    	// Get coordinates of the restaurant
		    	if(jsonobj_1.get("coordinates") != null) {
		    		JsonObject jsonobj_3 = (JsonObject) jsonobj_1.get("coordinates");
		    		restLat = jsonobj_3.get("latitude").getAsDouble();
		    		restLong = jsonobj_3.get("longitude").getAsDouble();
		    	}
		    	// Get the time it takes to drive from Tommy Trojan to the restaurant using Google Directions
		    	drivingTime = GoogleDirections.getDrivingTime(ttLat, ttLong, restLat, restLong);
		    	drivingTime = drivingTime/60;
		    	// Set drivingTime to be greater than zero
		    	if (drivingTime == 0) {
		    		++drivingTime;
		    	}
		    	// Create new Restaurant object based on parsed information and add it to the results vector
		    	Restaurant restaurantObj = new Restaurant(name, websiteUrl, price, fullAddress, phoneNumber, rating, drivingTime);
		    	if(distance <= radius) {
		    		resultsVec.add(restaurantObj);
		    	}
		    }
		}
		return resultsVec;		//returns Vector of restaurant objects
	}

}