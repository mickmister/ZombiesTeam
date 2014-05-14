package main.eventCardTypes;

import main.*;

public class KeysAreStillIn extends OneUseCard
{
	
	public KeysAreStillIn()
	{
		super(PossibleTarget.Self, "The Keys Are Still In It", Messages.getString("EventCard.KeysAreStillIn.desc"));
	}
	
	@Override
	public int behavior(int num)
	{
		Player player = GameHandler.instance.getPlayer(GameHandler.instance.getTurn());
		player.setMovesRemaining(10);
		GameHandler.instance.nextGameState();
		return 1;
	}
	
}
