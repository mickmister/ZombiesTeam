package main.eventCardTypes;

import internationalization.Messages;
import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.MapTileDeck.SpecialNames;

public class FireAxe extends PlayUntilRevoked
{
	
	public FireAxe()
	{
		super(PossibleTarget.Self, Messages.getString("FireAxe.name"), Messages.getString("FireAxe.desc"), //$NON-NLS-1$ //$NON-NLS-2$
				SpecialNames.FireStation);
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, Messages.getString("FireAxe.message"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		return num + 1;
	}
	
}
