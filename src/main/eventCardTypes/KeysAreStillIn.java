package main.eventCardTypes;

import internationalization.ECRB;
import main.GameHandler;
import main.Player;
import main.eventCardParents.SingleUseCard;

public class KeysAreStillIn extends SingleUseCard
{
	
	public KeysAreStillIn()
	{
		super(PossibleTarget.Self, ECRB.get("KeysAreStillIn.name"), //$NON-NLS-1$
				ECRB.get("KeysAreStillIn.description")); //$NON-NLS-1$
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
