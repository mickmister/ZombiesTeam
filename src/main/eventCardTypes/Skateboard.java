package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.MapTileDeck.SpecialNames;
import main.eventCardParents.PlayUntilRevokedCard;

public class Skateboard extends PlayUntilRevokedCard
{
	
	public Skateboard()
	{
		super(PossibleTarget.Self, ECRB.get("Skateboard.name"), //$NON-NLS-1$
				ECRB.get("Skateboard.description"), //$NON-NLS-1$
				SpecialNames.SkateShop);
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, ECRB.get("Skateboard.message"), getName(), //$NON-NLS-1$
				JOptionPane.INFORMATION_MESSAGE);
		return num + 2;
	}
	
}
