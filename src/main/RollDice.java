package main;

import main.GameHandler.GameState;

public class RollDice
{
	public static int rollDice()
	{
		int roll;
		roll = (int) (Math.random() * 6 + 1);
		roll = processRoll(roll);
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
			boolean win = player.fightZombie(game.getMap().getMapTile(player.getTileLocation().y, player.getTileLocation().x)
					.getCell(player.getCellLocation().y, player.getCellLocation().x));
			if (win)
			{
				game.nextGameState();
			}
		}
	}
	
	private static int processRoll(int roll) {
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(game.getTurn());
		if (player.checkIfCardIsActive("Double Trouble"))
		{
			System.out.println("Double card executed!");
			roll = roll * 2;
			player.removeActiveEventCard("Double Trouble");
		}	
		return 0;
	}
}