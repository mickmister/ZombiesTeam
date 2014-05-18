package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.Player;
import main.eventCardParents.SingleUseCard;

public class Fear extends SingleUseCard
{
	
	public Fear()
	{
		super(PossibleTarget.Pick, "Fear", "Target player may not move by any means during their next turn.");
		
	}
	
	@Override
	public int behavior(int num)
	{
		Player player = getTargetPlayer();
		player.setMovesRemaining(0);
		GameHandler.instance.nextGameState();
		DialogHandler.showMessage(null, "Your movement got skipped", getName(), JOptionPane.WARNING_MESSAGE);
		
		return 1;
	}
	
}
