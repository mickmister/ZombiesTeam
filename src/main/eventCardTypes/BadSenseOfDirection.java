package main.eventCardTypes;

import gui.*;

import javax.swing.*;

import main.*;

public class BadSenseOfDirection extends OneUseCard
{
	
	public BadSenseOfDirection()
	{
		super(PossibleTarget.Pick, "Bad Sense of Direction", Messages.getString("EventCard.BadSenseOfDirection.desc"));
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, Messages.getString("EventCard.BadSenseOfDirection.discard_life"), getName(), JOptionPane.INFORMATION_MESSAGE);
		GameHandler.instance.getPlayer(num).loseLifeToken();
		Player target = getTargetPlayer();
		target.resetPlayerLocation();
		return 1;
	}
	
}
