package api;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/* 
 * Google Search Class
 * API Key / Search Engine ID from Jiho's Google Account
 * Limited to 100 queries a day
 */
public class GoogleImageSearch {

	static final String API_KEY = "AIzaSyDUB_EE6FdB4xzWrjVnDbWMPgoe0-TVDnw";
	static final String SEARCH_ENGINE_ID = "012879953607576427254:2cidu_it4hw"; //searches for images on the web
	
	// Returns a vector of image urls using the user-provided search term
	public static Vector<String> GetImagesFromGoogle(String searchTerm) throws IOException {
		
		searchTerm = URLEncoder.encode(searchTerm, "UTF-8"); // Encode before constructing url
		// Construct query url using user-provided search term - this can return non-food pictures
		String query = "https://www.googleapis.com/customsearch/v1?searchType=image&imgType=photo&key=" + API_KEY
				+ "&cx=" + SEARCH_ENGINE_ID + "&q=" + searchTerm + "&imgSize=large";
		
		// Get JSON string
		String json = jsonGetRequest(query);
		
		// Parse JSON string 
		JsonElement root = new JsonParser().parse(json);
		JsonObject rootObj = root.getAsJsonObject();
		int count = rootObj.getAsJsonObject("queries").getAsJsonArray("request").get(0)
					.getAsJsonObject().getAsJsonPrimitive("count").getAsInt();
		JsonArray items = rootObj.getAsJsonArray("items");
		
		// Maximum value of count is 10 (API default)
		Vector<String> imageVec = new Vector<String>();
		for (int i = 0; i < count; ++i) {
			try {
				// store only the url of the image
				imageVec.add(items.get(i).getAsJsonObject().getAsJsonPrimitive("link").getAsString());
			} catch (NullPointerException e) {
				System.out.println("Could not find enough images for in query\n" + query);
				break;
			}
		}
		return imageVec;
	}
	/*
	 * Returns json string given query url
	 */
	private static String jsonGetRequest(String urlQueryString) throws IOException {
		String json = null;
		URL url = new URL(urlQueryString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("charset", "utf-8");
		connection.connect();
		InputStream inStream = connection.getInputStream();
		json = streamToString(inStream);
		return json;
	}
	/*
	 * Converts inputStream from HttpURLConnection to String and returns it
	 */
	private static String streamToString(InputStream inputStream) {
		Scanner scan = new Scanner(inputStream, "UTF-8");
		String text = scan.useDelimiter("\\Z").next();
		scan.close();
		return text;
	}
}