package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import main.GameHandler;
import main.GameHandler.GameState;

import org.junit.Test;

public class GameHandlerTest
{
	@Test
	public void testGameHandler()
	{
		for (int i = 2; i <= 4; i += 1)
		{
			GameHandler test = new GameHandler(i);
			// Test valid constructor (no exceptions, etc.).
			assertNotNull(test);
			// Test that the singleton global reference is correct.
			assertEquals(test, GameHandler.instance);
		}
	}
	
	@Test
	public void testNextTurn2Players()
	{
		GameHandler test = new GameHandler(2);
		assertEquals(0, test.getTurn());
		test.nextTurn();
		assertEquals(1, test.getTurn());
		test.nextTurn();
		assertEquals(0, test.getTurn());
	}
	
	@Test
	public void testNextTurn4Players()
	{
		GameHandler test = new GameHandler(4);
		assertEquals(0, test.getTurn());
		test.nextTurn();
		assertEquals(1, test.getTurn());
		test.nextTurn();
		assertEquals(2, test.getTurn());
		test.nextTurn();
		assertEquals(3, test.getTurn());
		test.nextTurn();
		assertEquals(0, test.getTurn());
	}
	
	@Test
	public void testNextStateNoMoves()
	{
		GameHandler test = new GameHandler(2);
		
		assertEquals(GameState.tilePlacement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.zombiePlacement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.playerMovementDieRoll, test.getCurrentState());
		test.nextGameState();
		// The player will have no moves, so it should skip from
		// playerMovementDieRoll -> zombieMovementDieRoll
		assertEquals(GameState.zombieMovementDieRoll, test.getCurrentState());
		// TODO: Uncomment when last states are done.
		// test.nextGameState();
		// assertEquals(GameState.zombieMovement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.tilePlacement, test.getCurrentState());
	}
	
	@Test
	public void testNextStateWithMoves()
	{
		GameHandler test = new GameHandler(3);
		test.getPlayer(0).setMovesRemaining(5);
		
		assertEquals(GameState.tilePlacement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.zombiePlacement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.playerMovementDieRoll, test.getCurrentState());
		test.nextGameState();
		// The player will have moves, so it should go from
		// playerMovementDieRoll -> playerMovement
		assertEquals(GameState.playerMovement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.zombieMovementDieRoll, test.getCurrentState());
		// TODO: Uncomment when last states are done.
		// test.nextGameState();
		// assertEquals(GameState.zombieMovement, test.getCurrentState());
		test.nextGameState();
		assertEquals(GameState.tilePlacement, test.getCurrentState());
	}
}