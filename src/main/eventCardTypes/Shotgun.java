package main.eventCardTypes;

import gui.*;

import javax.swing.*;

import internationalization.*;

public class Shotgun extends MultipleUseCard
{
	public Shotgun()
	{
		super(PossibleTarget.Self, "Shotgun", Messages.getString("EventCard.Shotgun.desc"), 3);
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, Messages.getString("EventCard.Shotgun.combat_add"), getName(), JOptionPane.INFORMATION_MESSAGE);
		return num + 1;
	}
}