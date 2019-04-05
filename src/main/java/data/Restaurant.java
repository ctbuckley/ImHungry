package data;

import java.io.Serializable;

//Implements Serializable Interface to allow storing in session 
public class Restaurant implements Comparable<Restaurant>, Serializable {
	private static final long serialVersionUID = 1L;

	// String variables = "NULL", numeric variables = -1 if data is not available from Yelp
	private String name;
	private String websiteUrl;
	private int price;
	private String address;
	private String phoneNumber;
	private double rating;
	private int drivingTime;
	
	// Constructor
	public Restaurant(String name, String websiteUrl, int price, String address, String phoneNumber, double rating,
			int drivingTime) {
		this.name = name;
		this.websiteUrl = websiteUrl;
		this.price = price;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.rating = rating;
		this.drivingTime = drivingTime;
	}
	
	public String getName() {
		return name;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public int getPrice() {
		return price;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public double getRating() {
		return rating;
	}

	public int getDrivingTime() {
		return drivingTime;
	}
	/*
	 * comparing method to sort restaurant results in 
	 * 	ascending order of drive time from Tommy Trojan
	 */
	public int compareTo(Restaurant o) {
		return this.getDrivingTime() - o.getDrivingTime();			
	}
	/*
	 * Equals overridden for vector::contains() method
	 */	
	public boolean equals(Object obj) {
		if (obj instanceof Restaurant) {
			Restaurant o = (Restaurant) obj;
			return (this.name.equals(o.name));
		}
		else {
			return false;
		}
	}
}
