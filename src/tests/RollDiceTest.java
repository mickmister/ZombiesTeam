package tests;

import static org.junit.Assert.*;
import main.DiceRoll;
import main.GameHandler;
import main.GameHandler.GameState;
import main.TileCell;

import org.junit.Test;

public class RollDiceTest
{
	
	@Test
	public void testRollDice()
	{
		GameHandler game = new GameHandler(2);
		for (int i = 0; i < 1000000; i++)
		{
			int roll = DiceRoll.rollDice();
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
		int roll = DiceRoll.rollDice();
		assertEquals(game.getPlayer(game.getTurn()).getMovesRemaining(), roll);
		
		assertEquals(GameState.playerMovement, game.getCurrentState());
	}
	
	@Test
	public void testRollDiceZombieCombatState()
	{
		GameHandler game = new GameHandler(2);
		TileCell start = game.getMap().getMapTile(5,5).getCell(1,1);
		start.setZombie(true);
		game.nextGameState();
		game.nextGameState();
		game.nextGameState();
		start.setZombie(false);
		int roll = DiceRoll.rollDice();
		assertEquals(game.getPlayer(game.getTurn()).getZombieCombatRoll(), roll);
		
		assertEquals(GameState.zombieMovementDieRoll, game.getCurrentState());
	}
	
}
