package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.RB;

import javax.swing.JOptionPane;

import main.MapTileDeck.SpecialNames;

public class FireAxe extends PlayUntilRevoked
{
	public FireAxe()
	{
		super(PossibleTarget.Self, RB.get("FireAxe.name"), RB.get("FireAxe.desc"), //$NON-NLS-1$ //$NON-NLS-2$
				SpecialNames.FireStation);
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, RB.get("FireAxe.message"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		return num + 1;
	}
}