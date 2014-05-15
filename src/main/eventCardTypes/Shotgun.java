package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

public class Shotgun extends MultipleUseCard
{
	public Shotgun()
	{
		super(PossibleTarget.Self, "Shotgun", "Get +1 to your next 3 combat rolls", 3);
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, "Your combat roll was increased by 1 by the Shotgun card!", getName(), JOptionPane.INFORMATION_MESSAGE);
		return num + 1;
	}
}