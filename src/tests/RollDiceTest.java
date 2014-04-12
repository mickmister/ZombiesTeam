package tests;

import static org.junit.Assert.*;
import main.*;
import main.GameHandler.GameState;

import org.junit.*;

public class RollDiceTest
{
	@Test
	public void testRollDice()
	{
		new GameHandler(2);
		for (int i = 0; i < 10000000; i++)
		{
			int roll = RollDice.rollDice();
			if (roll > 6)
			{
				fail("Roll was greater than 6.");
			}
			if (roll < 1)
			{
				fail("Roll was less than 1.");
			}
		}
	}
	
	@Test
	public void testRollDicePlayerMovementDieRollState()
	{
		GameHandler game = new GameHandler(2);
		game.nextGameState();
		game.nextGameState();
		int roll = RollDice.rollDice();
		assertEquals(roll, game.getPlayer(0).getMovesRemaining());
		assertEquals(GameState.playerMovement, game.getCurrentState());
	}
	
	@Test
	public void testRollDiceZombieMovementDieRollState()
	{
		GameHandler game = new GameHandler(2);
		game.nextGameState();
		game.nextGameState();
		game.nextGameState();
		int roll = RollDice.rollDice();
		assertEquals(roll, game.getPlayer(0).getMovesRemaining());
		// assertEquals(GameState.zombieMovement, game.getCurrentState());
	}
	
	@Test
	public void testRollDiceZombieCombatState()
	{
		GameHandler game = new GameHandler(2);
		TileCell start = game.getMap().getMapTile(5, 5).getCell(1, 1);
		start.setZombie(true);
		game.nextGameState();
		game.nextGameState();
		game.nextGameState();
		start.setZombie(false);
		int roll = RollDice.rollDice();
		assertEquals(game.getPlayer(0).getZombieCombatRoll(), roll);
		assertEquals(GameState.zombieMovementDieRoll, game.getCurrentState());
	}
}