package main.eventCardTypes;



import gui.Dialog;
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
		Dialog.showMessageDialog(null, "Your movement roll was cut in half because your shoes are untied!");
		return num/2;
	}
	
}
