package main.eventCardTypes;

import gui.*;

import javax.swing.*;

import main.*;

public class HystericalParalysis extends OneUseCard
{
	
	public HystericalParalysis()
	{
		super(PossibleTarget.Pick, "Hysterical Paralysis", Messages.getString("EventCard.HystericalParalysis.desc"));
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler.instance.nextTurn();
		DialogHandler.showMessage(null, Messages.getString("EventCard.HystericalParalysis.move_skipped"), getName(), JOptionPane.WARNING_MESSAGE);
		return 1;
	}
	
}
