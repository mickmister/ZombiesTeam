package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

public class UntiedShoe extends OneUseCard
{
	
	public UntiedShoe()
	{
		super(PossibleTarget.Pick, "Your Shoe's Untied", "Divide target player's movement in half");
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, "Your movement roll was cut in half because your shoes are untied!", "Untied Shoe",
				JOptionPane.INFORMATION_MESSAGE);
		return num / 2;
	}
	
}
