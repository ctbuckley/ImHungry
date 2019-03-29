package datatests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Database;
import data.SearchItem;

import static org.junit.Assert.assertEquals;

public class DatabaseTests {

	Database db;
	int userID;

	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		db = new Database();
		db.insertUserintoUsers("testUser", "password");
		ResultSet rs =  db.getUserfromUsers("testUser");
		rs.next();
		userID = rs.getInt("userID");
	}
	
	@Test
	public void databaseUserTest() throws Exception {
		
		//insert new user
		db.insertUserintoUsers("testValidNewUser", "hashedPass");	
		
		//get the new user by their username
		ResultSet rs = db.getUserfromUsers("testValidNewUser");
		
		assertEquals(true, rs.next());
	    
	    //Remove the user so that this test works next time
  		rs = null;
  		rs = db.getUserfromUsers("testValidNewUser");
  		rs.next();
		int uID = rs.getInt("userID");
		db.deleteUserfromUsers(uID);
		
		//verify that this is empty now
		rs = db.getUserfromUsers("testValidNewUser");
		assertEquals(false, rs.next());
		
	}	
	
	@Test
	public void databaseSearchHistoryTest() throws ClassNotFoundException, SQLException {
		
		//insert two search queries into the database for the user
		int searchID1 = db.insertQueryintoSearchHistory(userID, "chicken", 4, 10000);
		int searchID2 = db.insertQueryintoSearchHistory(userID, "apple", 5, 20000);
		
		
		//insert a different user
		db.insertUserintoUsers("testValidNewUser", "hashedPass");	
		//get the new user by their username
		ResultSet rs = db.getUserfromUsers("testValidNewUser");
		rs.next();
		int uID = rs.getInt("userID");
		//insert a random query for a different user
		int searchID3 = db.insertQueryintoSearchHistory(uID, "other", 1, 100);
		
		//get all search queries for the user
		ArrayList<SearchItem> results = db.getSearchItemfromSearch(userID);
		
		assertEquals(2, results.size());
		
		SearchItem chicken = results.get(0);
		SearchItem apple = results.get(1);
		
		assertEquals(chicken.numResults, 4);
		assertEquals(apple.numResults, 5);
		
		assertEquals(chicken.searchQuery, "chicken");
		assertEquals(apple.searchQuery, "apple");
		
		assertEquals(chicken.radius, 10000);
		assertEquals(apple.radius, 20000);
		
		assertEquals(chicken.userID, userID);
		assertEquals(apple.userID, userID);
	
		//delete them so this test passes again next time
		db.deleteQueryfromSearchHistory(searchID1);
		db.deleteQueryfromSearchHistory(searchID2);
		db.deleteQueryfromSearchHistory(searchID3);
		
		//Remove the other user so that this test works next time
		db.deleteUserfromUsers(uID);
	}
	
	@Test
	public void noSearchesTest() throws ClassNotFoundException, SQLException {
		ArrayList<SearchItem> results = db.getSearchItemfromSearch(userID);
		assertEquals(0, results.size());
		
	}
	
	@After
	public void teardown() throws SQLException {
		db.deleteUserfromUsers(userID);
	}

}
