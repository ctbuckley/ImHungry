package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GoogleDirections {
	
	private final static String apiKey = "AIzaSyCVTgss5eiM_qBXwsnz_UgVK4d-FWiXZIM";
	
	// Returns the driving time in seconds from one point to another.
	public static int getDrivingTime(double startLatitude, double startLongitude, double endLatitude, double endLongitude) throws IOException {
		
		String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json?";
		urlString += "origins=" + Double.toString(startLatitude) + "," + Double.toString(startLongitude);
		urlString += "&destinations=" + Double.toString(endLatitude) + "," + Double.toString(endLongitude);
		urlString += "&key=" + apiKey;
		
		//System.out.println("Distance Request URL: " + urlString);
			
		URL url = new URL(urlString);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();			
		httpCon.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
			  
		int durationSeconds = -1;
		
		try {
		    JsonParser parser = new JsonParser();
		    JsonObject jsonObj = (JsonObject)parser.parse(br); 
		    JsonArray rowArr = (JsonArray) jsonObj.get("rows");
		    JsonObject firstObject = (JsonObject) rowArr.get(0);
		    JsonObject element = (JsonObject) ((JsonArray) firstObject.get("elements")).get(0);
		    JsonObject duration = (JsonObject) element.get("duration");
		    String durationValue = duration.get("value").getAsString();
		    
		    durationSeconds = Integer.parseInt(durationValue);	   
		} catch (NullPointerException e) {
	    	System.out.println("Failed for request " + urlString);
	    	e.printStackTrace();
			    
		}
	    
		return durationSeconds;
	}

}