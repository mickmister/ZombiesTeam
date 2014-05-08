package main.eventCardTypes;

import gui.*;

import javax.swing.*;

import internationalization.*;

public class UntiedShoe extends OneUseCard
{
	
	public UntiedShoe()
	{
		super(PossibleTarget.Pick, "Your Shoe's Untied", Messages.getString("EventCard.UntiedShoe.desc"));
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, Messages.getString("EventCard.UntiedShoe.roll_cut"), "Untied Shoe",
				JOptionPane.INFORMATION_MESSAGE);
		return num / 2;
	}
	
}
