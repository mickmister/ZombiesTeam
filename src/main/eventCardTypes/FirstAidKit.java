package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.MapTileDeck.SpecialNames;

public class FirstAidKit extends SingleUseDiscardable
{
	
	public FirstAidKit()
	{
		super(PossibleTarget.Self, "First Aid Kit", "Play this card when you are in the Hospital. You may discard this item instead"
				+ "of losing a health token as a result of combat.", SpecialNames.Hosipital);
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, "Your First Aid Kit has saved you from using a life token!", getName(), JOptionPane.INFORMATION_MESSAGE);
		return 1;
	}
	
}
