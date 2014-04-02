package main.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gui.ImageManager;
import main.GameHandler;
import main.GameHandler.GameState;

import org.junit.Test;

public class GameHandlerTest
{
	@Test
	public void testGameHandler()
	{
		for (int i = 0; i <= 4; i += 1)
		{
			new ImageManager();
			GameHandler test = new GameHandler(i);
			assertNotNull(test);
		}
	}
	
	@Test
	public void testNextTurn()
	{
		new ImageManager();
		GameHandler test = new GameHandler(2);
		assertEquals(0, test.getTurn());
		test.nextTurn();
		assertEquals(1, test.getTurn());
		test.nextTurn();
		assertEquals(0, test.getTurn());
	}
	
	@Test
	public void testNextState()
	{
		new ImageManager();
		GameHandler test = new GameHandler(2);
		assertEquals(GameState.tilePlacement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.zombiePlacement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.playerMovement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.zombieMovement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.tilePlacement, test.getCurrentState());
	}
}