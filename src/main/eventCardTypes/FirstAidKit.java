package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.MapTileDeck.SpecialNames;
import main.eventCardParents.CustomUseDiscardable;

public class FirstAidKit extends CustomUseDiscardable
{
	public FirstAidKit()
	{
		super(PossibleTarget.Self, ECRB.get("FirstAidKit.name"), //$NON-NLS-1$
				ECRB.get("FirstAidKit.description"), //$NON-NLS-1$
				SpecialNames.Hospital, 1);
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, ECRB.get("FirstAidKit.message"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		GameHandler.instance.getEventDeck().removeDiscardedCard(this);
		return 1;
	}
}