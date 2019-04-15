package data;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchItem implements Serializable{
   public int searchID;
   public int userID;
   public int numResults;
   public int radius;
   public String searchQuery;
   public ArrayList<String> imgURLs;

   public SearchItem(int searchID, int userID, int numResults, int radius, String searchQuery, ArrayList<String> imgIn) {
       super();
       this.searchID = searchID;
       this.userID = userID;
       this.numResults = numResults;
       this.radius = radius;
       this.searchQuery = searchQuery;
       this.imgURLs = imgIn;
   }
}