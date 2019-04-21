package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Config;
import data.Database;

@WebServlet("/ValidateLogin")
public class ValidateLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//From previous page, extract parameters
		Database db;
		
		String username = request.getParameter("username");
		session.setAttribute("username", username);
		String pass = request.getParameter("pass");
		String hashPass = "";
		
		//Set up variables to store return value
		boolean success = true;
		String errorMsg = "";
		String dbUsername = "";
		
		if (pass.length() == 0) {
			success = false;
			errorMsg += "The password is empty! ";
			pass = "";
		}
		
		if (username.length() == 0) {
			success = false;
			errorMsg += "The username is empty! ";
			username = "";
		}
		
		//Hash using sha256
		try {
	        MessageDigest md = MessageDigest.getInstance(Config.hashAlgo);
	        byte[] hashInBytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));
	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashInBytes) {
	        	sb.append(String.format("%02x", b));
	        }
	        hashPass = sb.toString();
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
		}
		
		System.out.println(hashPass);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		if (success) {
			try {
				success = false;
				db = new Database();
				int userID = db.getUserfromUsers(username);
				
				if (userID != -1) {
					//If a user with that email exists, check the password
					if (Objects.equals(hashPass, db.getUserPassword(userID))) {
						success = true;
						dbUsername = username;
					}
					else {
						success = false;
						errorMsg = "The password is incorrect!";
					}
				}
				else {
					//Check details of invalid login
					success = false;
					errorMsg = "A user with that username does not exist!";
				}
				//Set up a JSON return
				String objectToReturn =
						  "{\n"
							+ "\"success\": \"" + success + "\",\n"
							+ "\"data\": {\n"
								+ "\"errorMsg\": \"" + errorMsg + "\",\n"
								+ "\"username\": \"" + dbUsername + "\"\n"
							+ "}\n" 
						+ "}";
				out.print(objectToReturn);
				
			} catch(SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			} catch(ClassNotFoundException cnfe) {
				System.out.println("cnfe: " + cnfe.getMessage());
			} 
		}
		else {
			String objectToReturn =
					  "{\n"
						+ "\"success\": \"" + success + "\",\n"
						+ "\"data\": {\n"
							+ "\"errorMsg\": \"" + errorMsg + "\",\n"
							+ "\"name\": \"\",\n"
							+ "\"email\": \"\"\n"
						+ "}\n" 
					+ "}";
			out.print(objectToReturn);
		}
	}
}




