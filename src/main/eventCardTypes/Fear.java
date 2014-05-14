package main.eventCardTypes;

import gui.*;

import javax.swing.*;

import main.*;

public class Fear extends OneUseCard
{
	
	public Fear()
	{
		super(PossibleTarget.Pick, "Fear", Messages.getString("EventCard.Fear.desc"));
		
	}
	
	@Override
	public int behavior(int num)
	{
		Player player = getTargetPlayer();
		player.setMovesRemaining(0);
		GameHandler.instance.nextGameState();
		DialogHandler.showMessage(null, Messages.getString("EventCard.Fear.skip_move"), getName(), JOptionPane.WARNING_MESSAGE);
		
		return 1;
	}
	
}
