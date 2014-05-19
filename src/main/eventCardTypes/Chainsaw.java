package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.MapTileDeck.SpecialNames;
import main.eventCardParents.CustomUseDiscardable;

public class Chainsaw extends CustomUseDiscardable
{
	public Chainsaw()
	{
		super(PossibleTarget.Self, ECRB.get("Chainsaw.name"), //$NON-NLS-1$
				ECRB.get("Chainsaw.description"), SpecialNames.LawnAndGarden, 1); //$NON-NLS-1$
		
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, ECRB.get("Chainsaw.messages"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		return num + 2;
	}
}