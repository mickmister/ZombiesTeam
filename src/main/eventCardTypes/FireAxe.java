package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.MapTileDeck.SpecialNames;
import main.eventCardParents.PlayUntilRevokedCard;

public class FireAxe extends PlayUntilRevokedCard
{
	public FireAxe()
	{
		super(PossibleTarget.Self, ECRB.get("FireAxe.name"), ECRB.get("FireAxe.desc"), //$NON-NLS-1$ //$NON-NLS-2$
				SpecialNames.FireStation);
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, ECRB.get("FireAxe.message"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		return num + 1;
	}
}