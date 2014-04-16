package main;

import main.GameHandler.GameState;

public class RollDice
{
	public static int rollDice()
	{
		int roll;
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(game.getTurn());
		roll = (int) (Math.random() * 6 + 1);
		if (player.checkIfCardIsActive("Double Trouble"))
		{
			System.out.println("Double card executed!");
			roll = roll * 2;
			player.removeActiveEventCard("Double Trouble");
		}	
		return roll;
	}
	
	public static void rollAction(int roll)
	{
		GameHandler game = GameHandler.instance;
		
		if (game.getCurrentState() == GameState.playerMovementDieRoll || game.getCurrentState() == GameState.zombieMovementDieRoll)
		{
			Player player = game.getPlayer(game.getTurn());
			if (player.checkIfCardIsActive("Double Trouble"))
			{
				roll = roll * 2;
				player.removeActiveEventCard("Double Trouble");
			}			
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
	}
}