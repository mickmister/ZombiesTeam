package tests;

import static org.junit.Assert.*;
import main.*;
import main.DataListener.DataChangedEvent;
import main.GameHandler.GameState;

import org.junit.*;

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
	public void testNextStateInitial()
	{
		new GameHandler(2);
		assertEquals(GameState.tilePlacement, GameHandler.instance.getCurrentState());
		GameHandler.instance.nextGameState();
		assertEquals(GameState.zombiePlacement, GameHandler.instance.getCurrentState());
		GameHandler.instance.nextGameState();
		assertEquals(GameState.playerMovementDieRoll, GameHandler.instance.getCurrentState());
	}
	
	@Test
	public void testNextStateNoMoves()
	{
		new GameHandler(2);
		GameHandler.instance.nextGameState();
		GameHandler.instance.nextGameState();
		// The player will have no moves, so it should skip from
		// playerMovementDieRoll -> zombieMovementDieRoll
		GameHandler.instance.nextGameState();
		assertEquals(GameState.zombieMovementDieRoll, GameHandler.instance.getCurrentState());
		// TODO: Uncomment when last states are done.
		// test.nextGameState();
		// assertEquals(GameState.zombieMovement, test.getCurrentState());
		GameHandler.instance.nextGameState();
		assertEquals(GameState.tilePlacement, GameHandler.instance.getCurrentState());
	}
	
	@Test
	public void testNextStateWithMoves()
	{
		new GameHandler(2);
		GameHandler.instance.getPlayer(0).setMovesRemaining(5);
		
		GameHandler.instance.nextGameState();
		GameHandler.instance.nextGameState();
		// The player will have moves, so it should go from
		// playerMovementDieRoll -> playerMovement
		GameHandler.instance.nextGameState();
		assertEquals(GameState.playerMovement, GameHandler.instance.getCurrentState());
		GameHandler.instance.nextGameState();
		assertEquals(GameState.zombieMovementDieRoll, GameHandler.instance.getCurrentState());
		// TODO: Uncomment when last states are done.
		// test.nextGameState();
		// assertEquals(GameState.zombieMovement, test.getCurrentState());
		GameHandler.instance.nextGameState();
		assertEquals(GameState.tilePlacement, GameHandler.instance.getCurrentState());
	}
	
	@Test
	public void testNextStateWithCombat()
	{
		new GameHandler(2);
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(1, 1).setZombie(true);
		GameHandler.instance.getPlayer(0).setMovesRemaining(1);
		
		GameHandler.instance.nextGameState();
		GameHandler.instance.nextGameState();
		// There is a zombie to fight, so it should go from
		// playerMovementDieRoll -> zombieCombat
		GameHandler.instance.nextGameState();
		assertEquals(GameState.zombieCombat, GameHandler.instance.getCurrentState());
		GameHandler.instance.nextGameState();
		assertEquals(GameState.playerMovement, GameHandler.instance.getCurrentState());
		
		GameHandler.instance.getPlayer(0).setMovesRemaining(0);
		GameHandler.instance.gotoCombatOrMoveState();
		GameHandler.instance.nextGameState();
		assertEquals(GameState.zombieMovementDieRoll, GameHandler.instance.getCurrentState());
		
		// TODO: Uncomment when last states are done.
		// test.nextGameState();
		// assertEquals(GameState.zombieMovement, test.getCurrentState());
		GameHandler.instance.nextGameState();
		assertEquals(GameState.tilePlacement, GameHandler.instance.getCurrentState());
	}
	
	@Test
	public void testGotoCombatOrMoveState()
	{
		new GameHandler(2);
		
		GameHandler.instance.gotoCombatOrMoveState();
		assertEquals(GameState.zombieMovementDieRoll, GameHandler.instance.getCurrentState());
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(1, 1).setZombie(true);
		GameHandler.instance.gotoCombatOrMoveState();
		assertEquals(GameState.zombieCombat, GameHandler.instance.getCurrentState());
		
		new GameHandler(2);
		
		GameHandler.instance.getPlayer(0).setMovesRemaining(1);
		GameHandler.instance.gotoCombatOrMoveState();
		assertEquals(GameState.playerMovement, GameHandler.instance.getCurrentState());
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(1, 1).setZombie(true);
		GameHandler.instance.gotoCombatOrMoveState();
		assertEquals(GameState.zombieCombat, GameHandler.instance.getCurrentState());
	}
	
	@Test
	public void testCardDecks()
	{
		new GameHandler(1);
		assertNotNull(GameHandler.instance.getTileDeck());
		assertNotNull(GameHandler.instance.getEventDeck());
	}
	
	@Test
	public void testGuiStateData()
	{
		new GameHandler(1);
		assertNotNull(GameHandler.instance.getGuiStateData());
		assertEquals(true, GameHandler.instance.getGuiStateData().mapTileDeckButtonEnabled);
		assertEquals(false, GameHandler.instance.getGuiStateData().rollDiceButtonEnabled);
	}
	
	@Test
	public void testDataListeners()
	{
		new GameHandler(1);
		final int[] test = new int[1];
		DataListener listener = new DataListener()
		{
			@Override
			public void dataChanged(DataChangedEvent e)
			{
				test[0] += 1;
			}
		};
		GameHandler.instance.addDataListener(listener);
		GameHandler.instance.addDataListener(listener);
		for (int i = 0; i < 10; i += 1)
		{
			GameHandler.instance.fireDataChangedEvent(DataChangedEvent.test);
		}
		assertEquals(2 * 10, test[0]);
	}
}