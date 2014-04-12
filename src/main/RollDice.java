package main;

import main.GameHandler.GameState;

public class DiceRoll
{
	public static int rollDice()
	{
		GameHandler game = GameHandler.instance;
		int roll = (int) (Math.random() * 6 + 1);
		
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
			boolean win = player.fightZombie(game.getMap().getMapTile(player.getTileLocation().y, player.getTileLocation().x)
					.getCell(player.getCellLocation().y, player.getCellLocation().x));
			if (win)
			{
				game.nextGameState();
			}
		}
		
		return roll;
	}
}