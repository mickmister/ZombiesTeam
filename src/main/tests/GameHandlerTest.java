package main.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import main.GameHandler;

import org.junit.Test;

public class GameHandlerTest
{
	@Test
	public void testGameHandler()
	{
		for (int i = 0; i <= 4; i += 1)
		{
			GameHandler test = new GameHandler(i);
			assertNotNull(test);
		}
	}
	
	@Test
	public void testNextTurn()
	{
		GameHandler test = new GameHandler(2);
		assertEquals(0, test.getTurn());
		test.nextTurn();
		assertEquals(1, test.getTurn());
		test.nextTurn();
		assertEquals(0, test.getTurn());
	}
}