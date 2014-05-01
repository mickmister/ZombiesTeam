package main.eventCardTypes;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.GameHandler.GameState;

public class Shotgun extends MultipleUseCard
{
	public Shotgun()
	{
		super(PossibleTarget.Self, "Shotgun", "Get +1 to your next 3 combat rolls", 3);
	}
	
	@Override
	public int behavior(int num)
	{
		GameState state = GameHandler.instance.getCurrentState();
		if(state == GameState.zombieCombat)
		{
			JOptionPane.showMessageDialog(null, "Your combat roll was increased by 1 by the Shotgun card!");
			return num + 1;			
		}
		else
		{
			throw new UnsupportedOperationException("Trying to call behavior() of Shotgun card in an invalid state: " + state);
		}
		
	}
}