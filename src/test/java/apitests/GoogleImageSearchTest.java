package apitests;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Vector;

import org.junit.Test;

import api.GoogleImageSearch;

/*
 *  Tests for the GoogleImageSearch class.
 */
public class GoogleImageSearchTest {

	/*
	 *  Tests that a basic search will succeed.
	 */
	@Test
	public void testBasicFunctionality() throws IOException {
		// This is necessary for complete coverage, even though the functions are supposed to be accessed statically.
		GoogleImageSearch gi = new GoogleImageSearch();
		
		Vector<String> arr = GoogleImageSearch.GetImagesFromGoogle("mexican");
		assertEquals(10, arr.size());
	}

	/*
	 *  Tests a search with multiple words.
	 */
	@Test
	public void testMultipleWordInput() throws IOException {
		Vector<String> arr = GoogleImageSearch.GetImagesFromGoogle("mexican food");
		assertEquals(10, arr.size());

	}

	/*
	 *  Tests a search with an invalid search.
	 */
	@Test
	public void testBadInput() throws IOException {
		Vector<String> arr = GoogleImageSearch.GetImagesFromGoogle("qwertyuiopoiuyrtyuiopoiuyghjuytfvb");
		assertEquals(0, arr.size());

	}

}
