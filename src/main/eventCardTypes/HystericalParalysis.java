package main.eventCardTypes;

import javax.swing.JOptionPane;

import gui.DialogHandler;
import main.*;

public class HystericalParalysis extends OneUseCard
{
	
	public HystericalParalysis()
	{
		super(PossibleTarget.Pick, "Hysterical Paralysis", "Target player's next turn will be skipped.");
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler.instance.nextTurn();
		DialogHandler.showMessage(null, "Your turn has been skipped because you are paralyzed!", this.getName(), JOptionPane.WARNING_MESSAGE);
		return 1;
	}
	
}
