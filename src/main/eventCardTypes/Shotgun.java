package main.eventCardTypes;

import gui.Dialog;

import javax.swing.*;

public class Shotgun extends MultipleUseCard
{
	public Shotgun()
	{
		super(PossibleTarget.Self, "Shotgun", "Get +1 to your next 3 combat rolls", 3);
	}
	
	@Override
	public int behavior(int num)
	{
		Dialog.showMessage(null, "Your combat roll was increased by 1 by the Shotgun card!", "Shotgun Card", JOptionPane.INFORMATION_MESSAGE);
		return num + 1;
	}
}