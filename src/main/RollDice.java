package main;

import main.GameHandler.GameState;
import main.eventCardTypes.*;

public class RollDice
{
	public static int rollDice()
	{
		int roll;
		roll = (int) (Math.random() * 6 + 1);
		// Player player = GameHandler.instance.getPlayer(GameHandler.instance.getTurn());
		// if (GameHandler.instance.getCurrentState() == GameState.playerMovementDieRoll)
		// {
		// roll = GameHandler.instance.getEventDeck().doCardAction(player, AdrenalineRush.class,
		// roll);
		// }
		// if (GameHandler.instance.getCurrentState() == GameState.zombieCombat)
		// {
		// roll = GameHandler.instance.getEventDeck().doCardAction(player, AdrenalineRush.class,
		// roll);
		// roll = GameHandler.instance.getEventDeck().doCardAction(player, Shotgun.class, roll);
		// }
		roll = handleDiceCards(roll);
		
		return roll;
	}
	
	private static int handleDiceCards(int roll)
	{
		int result = roll;
		GameState state = GameHandler.instance.getCurrentState();
		EventCardDeck deck = GameHandler.instance.getEventDeck();
		Player player = GameHandler.instance.getPlayer(GameHandler.instance.getTurn());
		
		switch (state)
		{
			case playerMovementDieRoll:
				result = deck.doCardAction(player, AdrenalineRush.class, result);
				result = deck.doCardAction(player, UntiedShoe.class, result);
				break;
			case zombieCombat:
				result = deck.doCardAction(player, AdrenalineRush.class, result);
				result = deck.doCardAction(player, Shotgun.class, result);
				break;
			default:
				break;
		}
		
		return result;
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