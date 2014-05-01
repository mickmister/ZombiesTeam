package main.eventCardTypes;

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
		JOptionPane.showMessageDialog(null, "Your combat roll was increased by 1 by the Shotgun card!");
		return num + 1;
	}
}