package main.eventCardTypes;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.Player;

public class UntiedShoe extends OneUseCard
{

	public UntiedShoe()
	{
		super(PossibleTarget.Pick, "Your Shoe's Untied", "Divide target player's movement in half");
	}

	@Override
	public int behavior(int num)
	{
		JOptionPane.showMessageDialog(null, "Your movement roll was cut in half because your shoes are untied!");
		return num/2;
	}
	
}
