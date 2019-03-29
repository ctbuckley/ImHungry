package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public Database() throws ClassNotFoundException, SQLException {	
	
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hungrydatabase?user=root&password=root&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=utf8");	
	
	}
	
	/* get user */
	
	public ResultSet getUserfromUsers(String username) throws SQLException {
		ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
		ps.setString(1, username);
		rs = ps.executeQuery();
		/* returns (int)userID, (String)username, and (String)hashed password */
		return rs;
	}
	
	public void deleteUserfromUsers(int userID) throws SQLException {
		ps = conn.prepareStatement("DELETE FROM HungryDatabase.Users WHERE userID=?");
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
		rs = ps.executeQuery();
		/* returns all searchQuery's related to user */
		ArrayList<SearchItem> result = new ArrayList<SearchItem>();
		while (rs.next()) {
			SearchItem temp = new SearchItem(rs.getInt("searchID"), rs.getInt("userID"), rs.getInt("numResults"), 
					rs.getInt("radius"), rs.getString("searchQuery"));
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
		ps = conn.prepareStatement("DELETE FROM HungryDatabase.SearchHistory WHERE searchID=?");
		ps.setString(1, Integer.toString(searchID));
		ps.executeUpdate();	
	}
	
	
	
	
	
}
