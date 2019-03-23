package apitests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import api.GoogleDirections;

/*
 *  Tests for the GoogleDirections class.
 */
public class GoogleDirectionsTest {

	/*
	 * Test to make sure the GoogleDirections class returns reasonable values.
	 */
	@Test
	public void testBasicFunctionality() throws IOException {
		
		// This is necessary for complete coverage, even though the functions are supposed to be accessed statically.
		GoogleDirections gd = new GoogleDirections();
		
		// Waypoints are Tommy Trojan and Spudnuts.
		int timeDuration = GoogleDirections.getDrivingTime(34.0206, -118.2854, 34.0252, -118.2788);
		assertTrue(timeDuration > 120);  // This will be different depending on the time of day so it has to be greater than or equal to.
	}
	
	
	/*
	 * Test to make sure the API can handle bad latitude and longitude data.
	 */
	@Test
	public void badUrltest() throws IOException {
		
		int timeDuration = GoogleDirections.getDrivingTime(34000.0206, -118.2854, 34.0252, -118.2788);
		System.out.println(timeDuration);
		assertEquals(-1, timeDuration);
	}

}
