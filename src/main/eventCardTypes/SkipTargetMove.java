package main.eventCardTypes;

import main.GameHandler;
import main.Player;

public class SkipTargetMove extends OneUseCard
{

	public SkipTargetMove(PossibleTarget posTar, String name, String description)
	{
		super(PossibleTarget.Pick, "Fear", "Target player may not move by any means during their next turn");
	}

	@Override
	public int behavior(int num)
	{
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(game.getTurn());
		player.setMovesRemaining(0);
		player.addLifeToken();
		player.addLifeToken();
		game.nextGameState();
		return num;
	}
	
}
