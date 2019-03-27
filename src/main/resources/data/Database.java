package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public Database() throws ClassNotFoundException, SQLException {	
	
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hungrydatabase?user=root&password=password&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=utf8");	
	
	}
	
	public ResultSet getUserfromUsers(String username) throws SQLException {
		ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
		ps.setString(1, username);
		rs = ps.executeQuery();
		return rs;
	}
	
	public void deleteUserfromUsers(int id) throws SQLException {
		ps = conn.prepareStatement("DELETE FROM HungryDatabase.Users WHERE userID=?");
		ps.setString(1, Integer.toString(id));
		ps.executeUpdate();	
	}
	
	public void insertUserintoUsers(String username, String pass) throws SQLException {
		ps = conn.prepareStatement("INSERT INTO Users (username, pass) VALUES ('" + username + "', '" + pass + "');");
		ps.executeUpdate();
	}
	
}
