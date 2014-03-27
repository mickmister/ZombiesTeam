package main.tests;

import static org.junit.Assert.*;
import main.Player;

import org.junit.Test;

public class PlayerTest
{
	@Test
	public void testConstructor()
	{
		Player test = new Player();
		assertNotNull(test);
	}
	
	@Test
	public void testStartingValues()
	{
		Player test = new Player();
		assertEquals(0, test.getZombiesCaptured());
		assertEquals(3, test.getLifeTokens());
		assertEquals(3, test.getBulletTokens());
	}
}