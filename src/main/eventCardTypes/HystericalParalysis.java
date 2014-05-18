package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.eventCardParents.SingleUseCard;

public class HystericalParalysis extends SingleUseCard
{
	
	public HystericalParalysis()
	{
		super(PossibleTarget.Pick, "Hysterical Paralysis", "Target player's next turn will be skipped.");
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler.instance.nextTurn();
		DialogHandler.showMessage(null, "Your turn has been skipped because you are paralyzed!", getName(), JOptionPane.WARNING_MESSAGE);
		return 1;
	}
	
}
