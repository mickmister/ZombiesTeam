package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.MapTileDeck.SpecialNames;
import main.eventCardParents.CustomUseDiscardable;

public class FirstAidKit extends CustomUseDiscardable
{
	
	public FirstAidKit()
	{
		super(PossibleTarget.Self, "First Aid Kit", "Play this card when you are in the Hospital. You may discard this item instead"
				+ "of losing a health token as a result of combat.", SpecialNames.Hospital);
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, "Your First Aid Kit has saved you from using a life token!", getName(), JOptionPane.INFORMATION_MESSAGE);
		GameHandler.instance.getEventDeck().removeDiscardedCard(this);
		return 1;
	}
	
}
