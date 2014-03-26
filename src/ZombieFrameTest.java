import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Simple JUnit tests
 *
 * @author watersdr.
 *         Created Mar 20, 2014.
 */
public class ZombieFrameTest {

	/**
	 * Shows that we know how to construct JUnit Tests
	 *
	 */
	@Test
	public void test() {
		assertEquals(6, ZombieFrame.multiply(2, 3));
		assertEquals(0, ZombieFrame.multiply(3, 0));
	}
	
	/**
	 * Tests whether Buffalo smells or not.
	 *
	 */
	@Test
	public void smellTest() {
		assertTrue("Buffalo Smells".length() > 0);
	}

}
