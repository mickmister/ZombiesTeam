package main;

import main.GameHandler.GameState;
import main.eventCardTypes.*;

public class RollDice
{
	public static int rollDice()
	{
		int roll = (int) (Math.random() * 6 + 1);
		Player player = GameHandler.instance.getPlayer(GameHandler.instance.getTurn());
		if (GameHandler.instance.getCurrentState() == GameState.playerMovementDieRoll)
		{			
			roll = GameHandler.instance.getEventDeck().doCardAction(player, AdrenalineRush.class, roll);
			roll = GameHandler.instance.getEventDeck().doCardAction(player, UntiedShoe.class, roll);
			roll = GameHandler.instance.getEventDeck().doCardAction(player, Skateboard.class, roll);
		}
		if (GameHandler.instance.getCurrentState() == GameState.zombieCombat)
		{
			roll = GameHandler.instance.getEventDeck().doCardAction(player, AdrenalineRush.class, roll);
			roll = GameHandler.instance.getEventDeck().doCardAction(player, Shotgun.class, roll);
			roll = GameHandler.instance.getEventDeck().doCardAction(player, FireAxe.class, roll);
			roll = GameHandler.instance.getEventDeck().doDiscardedCardAction(player, Chainsaw.class, roll);
		}		
		return roll;
	}
	
	public static void rollAction(int roll)
	{
		GameHandler game = GameHandler.instance;
		
		if (game.getCurrentState() == GameState.playerMovementDieRoll || game.getCurrentState() == GameState.zombieMovementDieRoll)
		{
			Player player = game.getPlayer(game.getTurn());
			player.setMovesRemaining(roll);
			game.nextGameState();
		}
		else if (game.getCurrentState() == GameState.zombieCombat)
		{
			Player player = game.getPlayer(game.getTurn());
			player.setZombieCombatRoll(roll);
			TileCell cell = game.getMap().getMapTile(player.getTileLocation().y, player.getTileLocation().x)
					.getCell(player.getCellLocation().y, player.getCellLocation().x);
			boolean win = player.fightZombie(cell);
			if (win)
			{
				if (cell.hasBulletToken())
				{
					player.addBulletToken();
					cell.setBulletToken(false);
				}
				if (cell.hasLifeToken())
				{
					player.addLifeToken();
					cell.setLifeToken(false);
				}
				game.nextGameState();
			}
		}
		
		game.fireDataChangedEvent(null);
	}
}