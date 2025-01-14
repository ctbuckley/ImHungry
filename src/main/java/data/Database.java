package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Database {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public Database() throws ClassNotFoundException, SQLException {	
	
		Class.forName(Config.className);
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hungrydatabase?user=root&password="+ Config.databasePW+"&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=utf8");	
	
	}
	
	/* get user */
	
	public int getUserfromUsers(String username) throws SQLException {
		ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
		ps.setString(1, username);
		rs = ps.executeQuery();
		
		if (rs.next()) {
			return rs.getInt("userID");
		}
		
		return -1;
	}
	
	public String getUserPassword(int userID) throws SQLException {
		
		ResultSet rs;
		ps = conn.prepareStatement("SELECT * FROM hungrydatabase.Users WHERE userID=?");
		ps.setString(1, Integer.toString(userID));
		
		rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getString("pass");
		}
		
		return null;
	}
	
	public void deleteUserfromUsers(int userID) throws SQLException {
		ps = conn.prepareStatement("DELETE FROM hungrydatabase.Lists WHERE userID=?");
		ps.setString(1, Integer.toString(userID));
		ps.executeUpdate();	
		
		ps = conn.prepareStatement("DELETE FROM hungrydatabase.SearchHistory WHERE userID=?");
		ps.setString(1, Integer.toString(userID));
		ps.executeUpdate();	
		
		ps = conn.prepareStatement("DELETE FROM hungrydatabase.Users WHERE userID=?");
		ps.setString(1, Integer.toString(userID));
		ps.executeUpdate();	
	}
	
	public void insertUserintoUsers(String username, String pass) throws SQLException {
		ps = conn.prepareStatement("INSERT INTO Users (username, pass) VALUES ('" + username + "', '" + pass + "');");
		ps.executeUpdate();
	}
	
	/* get search history */
	
	public ArrayList<SearchItem> getSearchItemfromSearch(int userID) throws SQLException {
		ps = conn.prepareStatement("SELECT * FROM SearchHistory WHERE userID=?");
		ps.setInt(1, userID);
		ResultSet rs = ps.executeQuery();
		/* returns all searchQuery's related to user */
		ArrayList<SearchItem> result = new ArrayList<SearchItem>();
		while (rs.next()) {
			SearchItem temp = new SearchItem(rs.getInt("searchID"), rs.getInt("userID"), rs.getInt("numResults"), 
					rs.getInt("radius"), rs.getString("searchQuery"), this.getLinksfromImages(rs.getString("searchQuery")));
		    result.add(temp);
		}
		return result;
	}	
	
	public int insertQueryintoSearchHistory(int userID, String query, int num, int radius) throws SQLException {
		ps = conn.prepareStatement("INSERT INTO SearchHistory (userID, searchQuery, numResults, radius) "
				+ "VALUES ('" + userID + "','" + query +"', '" + num +"', '" + radius + "');");
		ps.executeUpdate();
		
		ps = conn.prepareStatement("SELECT MAX(searchID) FROM SearchHistory");
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("MAX(searchID)");
	}
	
	public void deleteQueryfromSearchHistory(int searchID) throws SQLException {
		ps = conn.prepareStatement("DELETE FROM hungrydatabase.SearchHistory WHERE searchID=?");
		ps.setString(1, Integer.toString(searchID));
		ps.executeUpdate();	
	}
	
	/* insert items (used for lists) */
	
	public void insertInstructions(Recipe r, int itemID) throws SQLException{
		for(int i=0; i<r.getInstructions().size(); i++) {
			
			ps = conn.prepareStatement("INSERT INTO Instructions "
					+ "(itemID, insIndex, instruc) VALUES "
					+ "(?, ?, ?);");
			ps.setInt(1, itemID);
			ps.setInt(2, (i+1));
			ps.setString(3, r.getInstructions().get(i));
			
			ps.executeUpdate();
			
		}
	}
	
	public void insertIngredients(Recipe r, int itemID) throws SQLException{
		for(int i=0; i<r.getIngredients().size(); i++) {
			
			ps = conn.prepareStatement("INSERT INTO Ingredients "
					+ "(itemID, ingIndex, ingred) VALUES "
					+ "(?, ?, ?);");
			ps.setInt(1, itemID);
			ps.setInt(2, (i+1));
			ps.setString(3, r.getIngredients().get(i));
			
			ps.executeUpdate();
			
		}
	}
	
	public int insertRecipe(Recipe r) throws SQLException {
		int result = insertItem(0, r.getName(), r.getRating(), r.getPictureUrl(), r.getPrepTime(), 
				r.getCookTime(), "", -1, "", "", -1);	
		insertInstructions(r, result);
		insertIngredients(r, result);
		return result;
	}
	
	public int insertRestaurant(Restaurant r) throws SQLException {
		return insertItem(1, r.getName(), r.getRating(), "", -1, -1, r.getWebsiteUrl(), 
				r.getPrice(), r.getAddress(), r.getPhoneNumber(), r.getDrivingTime());
	}
	
	public int insertItem(int itemType, String itemName, double rating, String picURL, 
			double prep, double cook, String website, double price, String address,
			String phone, int drive) throws SQLException {
		
		ps = conn.prepareStatement("INSERT INTO Item "
				+ "(itemType, itemName, rating, picURL, prepTime, cookTime, "
				+ "websiteURL, price, address, phone, driveTime) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		ps.setInt(1, itemType); //
		ps.setString(2, itemName);
		ps.setDouble(3, rating);
		ps.setString(4, picURL);
		ps.setDouble(5, prep);
		ps.setDouble(6, cook);
		
		ps.setString(7, website);
		ps.setDouble(8, price);
		ps.setString(9, address);
		ps.setString(10, phone);
		ps.setDouble(11, drive);
		
		ps.executeUpdate();
		
		ps = conn.prepareStatement("SELECT MAX(itemID) FROM Item");
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("MAX(itemID)");
		
	}
	

	public void insertItemintoList(int userID, int itemID, String listName) throws SQLException {
		
		int listID = getList(listName);
		int itemIndex = getIndex(userID, listID) + 1;
		
		ps = conn.prepareStatement("INSERT INTO Lists "
				+ "(userID, itemID, listID, itemIndex) VALUES "
				+ "(?, ?, ?, ?);");
		
		ps.setInt(1, userID); 
		ps.setInt(2, itemID);
		ps.setInt(3, listID);
		ps.setInt(4, itemIndex);
		
		ps.executeUpdate();
		
	}
	
	public int getList(String listName) {
		if(listName.equals("Favorites")) {
			return 1;
		} else if(listName.equals("Do Not Show")) {
			return 3;
		} else {
			return 2;
		}
	}
	
	public int getIndex(int userID, int listID) throws SQLException {
		ps = conn.prepareStatement("SELECT MAX(itemIndex) FROM Lists "
				+ "WHERE userID=?"
				+ " AND listID=?");
		ps.setInt(1, userID);
		ps.setInt(2, listID);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("MAX(itemIndex)");
	}
	
	public int getItemType(int itemID) throws SQLException {
		ps = conn.prepareStatement("SELECT * FROM Item "
				+ "WHERE itemID=?");
		ps.setInt(1, itemID);
		rs = ps.executeQuery();
		rs.next();
		return rs.getInt("itemType");
	}
	
	public int getItemId(Recipe r) throws SQLException{
		
		ps = conn.prepareStatement("SELECT * from Item WHERE itemName=? AND rating=? AND prepTime=? AND cookTime=?");
		ps.setString(1, r.getName());
		ps.setDouble(2, r.getRating());
		ps.setDouble(3, r.getPrepTime());
		ps.setDouble(4, r.getCookTime());
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("itemID");
	}
	
	public int getItemId(Restaurant r) throws SQLException {
		ps = conn.prepareStatement("SELECT * from Item WHERE itemName=? AND rating=? AND address=?");
		ps.setString(1, r.getName());
		ps.setDouble(2, r.getRating());
		ps.setString(3, r.getAddress());
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("itemID");
	}
	
	public ArrayList<String> getIngredients(int itemID) throws SQLException{
		ps = conn.prepareStatement("SELECT * FROM Ingredients "
				+ "WHERE itemID=?");
		ps.setInt(1, itemID);
		rs = ps.executeQuery();
		ArrayList<String> result = new ArrayList<String>();
		
		while(rs.next()) {
			result.add(rs.getString("ingred"));
		}
		return result;
	}
	
	
	public ArrayList<String> getInstructions(int itemID) throws SQLException{
		ps = conn.prepareStatement("SELECT * FROM Instructions "
				+ "WHERE itemID=?");
		ps.setInt(1, itemID);
		rs = ps.executeQuery();
		ArrayList<String> result = new ArrayList<String>();
		
		while(rs.next()) {
			result.add(rs.getString("instruc"));
		}
		return result;
	}
	
	public Recipe getRecipeInfo(int itemID) throws SQLException {
		ps = conn.prepareStatement("SELECT * FROM Item WHERE itemID=?");
		ps.setInt(1, itemID);
		rs = ps.executeQuery();
		rs.next();
		
		double r = rs.getDouble("rating");
		
		return new Recipe(rs.getString("itemName"), rs.getString("picURL"), rs.getDouble("prepTime"),
				rs.getDouble("cookTime"), getIngredients(itemID), getInstructions(itemID), r);
	}
	
	public Restaurant getRestaurantInfo(int itemID) throws SQLException {
		ps = conn.prepareStatement("SELECT * FROM Item "
				+ "WHERE itemID=?");
		ps.setInt(1, itemID);
		rs = ps.executeQuery();
		rs.next();
		
		double r = rs.getDouble("rating");

		return new Restaurant(rs.getString("itemName"), rs.getString("websiteURL"), rs.getInt("price"),
				rs.getString("address"), rs.getString("phone"), r, rs.getInt("driveTime"));
	}
	
	public void swapItemIndex(int oldIndex, int newIndex, String username, String listName) throws SQLException {
		
		int userID = this.getUserfromUsers(username);
		
		//listID
		int listID = this.getList(listName);
		
		
		//how do we get itemID?
		ps = conn.prepareStatement("SELECT * from Lists WHERE userID=? AND listID=? AND itemIndex=?");
		ps.setInt(1, userID);
		ps.setInt(2, listID);
		ps.setInt(3, oldIndex);
		
		ResultSet rs1 = ps.executeQuery();
		
		System.out.println("UserID: " + userID);
		System.out.println("listID: " + listID);
		System.out.println("ItemIndex: " + oldIndex);
		
		rs1.next();
		int itemID = rs1.getInt("itemID");
		
		boolean moveDown = true;
		int loopNum = 0;
		
		if (oldIndex < newIndex) {
			moveDown = true;
			loopNum = newIndex - oldIndex;
		} else {
			moveDown = false;
			loopNum = oldIndex - newIndex;
		}
		
		for (int i = 0; i < loopNum; i++) {
			swapHelper(userID, listID, itemID, moveDown);
		}
	
	}
	
	public void swapHelper(int userID, int listID, int itemID, boolean moveDown) throws SQLException{ 
		
		ps = conn.prepareStatement("SELECT * from Lists WHERE userID=? AND listID=? AND itemID=?");
		ps.setInt(1, userID);
		ps.setInt(2, listID);
		ps.setInt(3, itemID);
		
		rs = ps.executeQuery();
		
		rs.next();
		int index = rs.getInt("itemIndex"); //this is the CURRENT itemIndex
		int newIndex = 0;
		
		if (moveDown) {
			
			/* itemID is the item being moved DOWN 
			 * i.e. A -- B -- C
			 * if itemID = A, then it becomes
			 * B -- A -- C
			 * then, if itemID = A again, it becomes
			 * B -- C -- A
			 */
			newIndex = index+1;
			
		}else {
			/* itemID is the item being moved UP 
			 * i.e. A -- B -- C
			 * if itemID = C, then it becomes
			 * A -- C -- B
			 * then, if itemID = C again, it becomes
			 * C -- A -- B
			 */
			newIndex = index-1;
		}
		
		ps = conn.prepareStatement("UPDATE Lists " + 
				"SET itemIndex = ? " + 
				"WHERE userID=? AND listID=? AND itemID=?");
		ps.setInt(1, -1);
		ps.setInt(2, userID);
		ps.setInt(3, listID);
		ps.setInt(4, itemID);
		ps.executeUpdate(); 
		
		ps = conn.prepareStatement("UPDATE Lists " + 
				"SET itemIndex = ? " + 
				"WHERE userID=? AND listID=? AND itemIndex=?");
		ps.setInt(1, index);
		ps.setInt(2, userID);
		ps.setInt(3, listID);
		ps.setInt(4, newIndex);
		ps.executeUpdate();
		
		ps = conn.prepareStatement("UPDATE Lists " + 
				"SET itemIndex = ? " + 
				"WHERE itemIndex=?");
		ps.setInt(1, newIndex);
		ps.setInt(2, -1);
		ps.executeUpdate();
		  
	}	
	public ArrayList<Integer> getItemsfromList(int userID, String listName) throws SQLException {
		
		int listID = getList(listName);
		
		ps = conn.prepareStatement("SELECT * FROM Lists "
				+ "WHERE userID=?"
				+ " AND listID=?");
		ps.setInt(1, userID);
		ps.setInt(2, listID);
		rs = ps.executeQuery();
		
		ArrayList<Integer> items = new ArrayList<Integer>();
		
		ArrayList<Integer> itemIndexes = new ArrayList<Integer>();
		
		HashMap<Integer, Integer> indexToItemID = new HashMap<Integer, Integer>();
		
		while(rs.next()) {		
			itemIndexes.add(rs.getInt("itemIndex"));
			indexToItemID.put(rs.getInt("itemindex"), rs.getInt("itemID"));
		}
		
		Collections.sort(itemIndexes);
		
		for (int i : itemIndexes) {
			items.add(indexToItemID.get(i));
		}
		
		return items;
	}
	
	public UserList getUserList(String username, int listIndex) throws SQLException {
		
		int userID = this.getUserfromUsers(username);
		
		String listName = "";
		
		if (listIndex == 0) {
			listName = "Favorites";
		} else if (listIndex == 1) {
			listName = "Do Not Show";
		} else {
			listName = "To Explore";
		}
		
		ArrayList<Integer> itemIDs = getItemsfromList(userID, listName);
		UserList result = new UserList();
		
		for(int i=0; i<itemIDs.size(); i++) {
			int type = getItemType(itemIDs.get(i));
			
			if(type==0) {
				result.add(getRecipeInfo(itemIDs.get(i)));
			}else{
				result.add(getRestaurantInfo(itemIDs.get(i)));
			}
			
		}
		
		return result;
	}
	
	public void dropTable(String tableName) throws SQLException{
		ps = conn.prepareStatement("DELETE FROM " + tableName + ";");
		ps.executeUpdate();
	}
	
	public void deleteIngredients(int itemID) throws SQLException{
		ps = conn.prepareStatement("SELECT * FROM Ingredients WHERE itemID=?");
		ps.setInt(1, itemID);
		rs = ps.executeQuery();	
		
		int items = 0;
		
		while(rs.next()) {
			items = rs.getInt("ingredID");
			ps = conn.prepareStatement("DELETE FROM Ingredients WHERE ingredID=?");
			ps.setInt(1, items);
			ps.executeUpdate();
		}
		
	}
	
	public void deleteInstructions(int itemID) throws SQLException{
		ps = conn.prepareStatement("SELECT * FROM Instructions WHERE itemID=?");
		ps.setInt(1, itemID);
		rs = ps.executeQuery();	
		
		int items = 0;
		
		while(rs.next()) {
			items = rs.getInt("instrucID");
			ps = conn.prepareStatement("DELETE FROM Instructions WHERE instrucID=?");
			ps.setInt(1, items);
			ps.executeUpdate();
		}
	}
	
	public void deleteItemfromItem(int itemID) throws SQLException {
		int type = getItemType(itemID);
		
		if(type==0) {
			deleteIngredients(itemID);
			deleteInstructions(itemID);
		}
		ps = conn.prepareStatement("DELETE FROM Item WHERE itemID=?");
		ps.setInt(1, itemID);
		ps.executeUpdate();	
	}
	 
	
	public void deleteItem(int LID) throws SQLException {
		ps = conn.prepareStatement("DELETE FROM Lists WHERE LID=?");
		ps.setInt(1, LID);
		ps.executeUpdate();	
	}
	
	public void deleteItemfromList(int userID, int itemID, String listName) throws SQLException {
		
		int listID = getList(listName);
		
		ps = conn.prepareStatement("SELECT * FROM Lists WHERE userID=? AND itemID=? AND listID=?");
		ps.setInt(1, userID);
		ps.setInt(2, itemID);
		ps.setInt(3, listID);

		rs = ps.executeQuery();
		if(rs.next()) {
			deleteItem(rs.getInt("LID"));
		}
		
	}
	
	/* 
	 * *******************************************************************************
	 * GROCERY LIST 
	 * *******************************************************************************
	 */
	
	public ArrayList<String> getGroceryListforUser(int userID) throws SQLException{
		
		ps = conn.prepareStatement("SELECT * FROM Grocery WHERE userID=?");
		ps.setInt(1, userID);
		rs = ps.executeQuery();
		ArrayList<String> result = new ArrayList<String>();
		
		while(rs.next()) {
			result.add(rs.getString("ingredientName"));
		}
		return result;
		
	}
	
	public int getCheckonGroceryItem(int userID, String ingredient) throws SQLException{
		ps = conn.prepareStatement("SELECT * FROM Grocery WHERE userID=? AND ingredientName=?");
		ps.setInt(1, userID);
		ps.setString(2, ingredient);
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		return rs.getInt("checked");
	}
	
	public void changeCheckonGroceryItem(int userID, String ingredient) throws SQLException{	
		
		int check = this.getCheckonGroceryItem(userID, ingredient);	
		
		if(check==0) {
			check = 1;
		}else {
			check = 0;
		}
		
		ps = conn.prepareStatement("UPDATE Grocery " + 
				"SET checked=? " + 
				"WHERE userID=? AND ingredientName=?");
		ps.setInt(1, check);
		ps.setInt(2, userID);
		ps.setString(3, ingredient);
		ps.executeUpdate(); 
		
	}
	
	public int getGroceryItemQuantity(int userID, String ingredient) throws SQLException{
		ps = conn.prepareStatement("SELECT * FROM Grocery WHERE userID=? AND ingredientName=?");
		ps.setInt(1, userID);
		ps.setString(2, ingredient);
		ResultSet rs = ps.executeQuery();
		int quantity = -1;
		if(rs.next()) {
			quantity = rs.getInt("quantity");
		}
		return quantity;
	}
	
	public int getGroceryItemID(int userID, String ingredient) throws SQLException{
		ps = conn.prepareStatement("SELECT * FROM Grocery WHERE userID=? AND ingredientName=?");
		ps.setInt(1, userID);
		ps.setString(2, ingredient);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return rs.getInt("groceryID");
		}
		return -1;
	}
	
	public int insertIngredientintoGrocery(int userID, String ingredient) throws SQLException{
		
		int quantity = this.getGroceryItemQuantity(userID, ingredient);
		
		if(quantity==-1) {
			//insert a new item
			ps = conn.prepareStatement("INSERT INTO Grocery (userID, ingredientName, checked, quantity) "
					+ "VALUES (?, ?, ?, ?);");
			ps.setInt(1, userID);
			ps.setString(2,  ingredient);
			ps.setInt(3,  0);
			ps.setInt(4, 1);
			
			ps.executeUpdate();
			
			ps = conn.prepareStatement("SELECT MAX(groceryID) FROM Grocery");
			ResultSet rs2 = ps.executeQuery();
			rs2.next();
			return rs2.getInt("MAX(groceryID)");
			
		} else {
			//update a current item
			ps = conn.prepareStatement("UPDATE Grocery " + 
					"SET quantity=? " + 
					"WHERE userID=? AND ingredientName=? ");
			ps.setInt(1, ++quantity);
			ps.setInt(2, userID);
			ps.setString(3, ingredient);
			
			ps.executeUpdate();
			
			return this.getGroceryItemID(userID, ingredient);
		}

	}
	
	public void deleteIngredientfromGrocery(int userID, String ingredient) throws SQLException {
		ps = conn.prepareStatement("SELECT * FROM Grocery WHERE userID=? AND ingredientName=?");
		ps.setInt(1, userID);
		ps.setString(2, ingredient);

		rs = ps.executeQuery();
		rs.next();
		
		ps = conn.prepareStatement("DELETE FROM Grocery WHERE groceryID=?");
		ps.setInt(1, rs.getInt("groceryID"));
		ps.executeUpdate();	
	}
	
	/* 
	 * *******************************************************************************
	 * IMAGE LINKS
	 * *******************************************************************************
	 */
	
	public int insertLinkintoImages(String searchQuery, String imgURL) throws SQLException {
		ps = conn.prepareStatement("INSERT INTO Images (searchQuery, imgURL) "
				+ "VALUES (?, ?);");
		ps.setString(1, searchQuery);
		ps.setString(2, imgURL);
		ps.executeUpdate();
		
		ps = conn.prepareStatement("SELECT MAX(imageID) FROM Images");
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("MAX(imageID)");
	
	}
	
	public ArrayList<String> getLinksfromImages(String searchQuery) throws SQLException {
		
		ps = conn.prepareStatement("SELECT * FROM Images WHERE searchQuery=?");
		ps.setString(1, searchQuery);
		rs = ps.executeQuery();
		ArrayList<String> result = new ArrayList<String>();
		
		while(rs.next()) {
			result.add(rs.getString("imgURL"));
		}
		return result;
		
	}
	
	public void deleteLinkfromImages(String imgURL) throws SQLException {
		ps = conn.prepareStatement("DELETE FROM Images WHERE imgURL=?");
		ps.setString(1, imgURL);
		ps.executeUpdate();
	}
	
	public boolean queryImagesExist(String query) throws SQLException {
        ps = conn.prepareStatement("SELECT * from Images WHERE searchQuery=?");
        ps.setString(1, query);
        rs = ps.executeQuery(); 
     
        if(rs.next()) {
            return true;    
        }
        return false;
	}
}
