package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.GameHandler.GameState;
import main.RollDice;
import main.TileCell;

import org.junit.Test;

public class RollDiceTest
{
	@Test
	public void testRollDice()
	{
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
	public void testRollActionPlayerMovementDieRoll()
	{
		GameHandler game = new GameHandler(2);
		game.nextGameState();
		game.nextGameState();
		int roll = RollDice.rollDice();
		RollDice.rollAction(roll);
		assertEquals(roll, game.getPlayer(0).getMovesRemaining());
		assertEquals(GameState.playerMovement, game.getCurrentState());
	}
	
	@Test
	public void testRollActionZombieMovementDieRoll()
	{
		GameHandler game = new GameHandler(2);
		game.nextGameState();
		game.nextGameState();
		game.nextGameState();
		int roll = RollDice.rollDice();
		RollDice.rollAction(roll);
		assertEquals(roll, game.getPlayer(0).getMovesRemaining());
		assertEquals(GameState.zombieMovement, game.getCurrentState());
	}
	
	@Test
	public void testRollActionZombieCombat()
	{
		GameHandler game = new GameHandler(2);
		TileCell tile = game.getMap().getMapTile(5, 5).getCell(1, 1);
		tile.setZombie(true);
		game.nextGameState();
		game.nextGameState();
		game.nextGameState();
		
		RollDice.rollDice();
		
		DialogHandler.defaultReturn = JOptionPane.NO_OPTION;
		RollDice.rollAction(3);
		assertEquals(3, game.getPlayer(0).getZombieCombatRoll());
		assertEquals(GameState.zombieCombat, game.getCurrentState());
		
		RollDice.rollAction(4);
		assertEquals(4, game.getPlayer(0).getZombieCombatRoll());
		assertEquals(GameState.zombieMovementDieRoll, game.getCurrentState());
		
		game = new GameHandler(2);
		tile = game.getMap().getMapTile(5, 5).getCell(1, 1);
		tile.setZombie(true);
		tile.setBulletToken(true);
		tile.setLifeToken(true);
		game.nextGameState();
		game.nextGameState();
		game.nextGameState();
		
		RollDice.rollAction(5);
		assertEquals(5, game.getPlayer(0).getZombieCombatRoll());
		assertEquals(GameState.zombieMovementDieRoll, game.getCurrentState());
	}
	
	@Test
	public void testRollActionOtherState()
	{
		GameHandler game = new GameHandler(2);
		int roll = RollDice.rollDice();
		RollDice.rollAction(roll);
		assertEquals(0, game.getPlayer(0).getMovesRemaining());
		assertEquals(0, game.getPlayer(0).getZombieCombatRoll());
		assertEquals(GameState.tilePlacement, game.getCurrentState());
	}
}