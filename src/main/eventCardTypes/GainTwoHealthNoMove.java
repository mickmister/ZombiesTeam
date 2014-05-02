package main.eventCardTypes;

import main.GameHandler;
import main.Player;

public class GainTwoHealthNoMove extends OneUseCard
{
	public GainTwoHealthNoMove()
	{
		super(PossibleTarget.Self, "GainTwoHealthNoMove", "Instead of making a movement roll, gain 2 health");
	}

	@Override
	public int behavior(int num)
	{
		Player player = this.getTargetPlayer();
		player.setMovesRemaining(0);
		player.addLifeToken();
		player.addLifeToken();
		GameHandler.instance.nextGameState();
		return 1;
	}

}
