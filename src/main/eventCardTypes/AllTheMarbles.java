package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.MapTileDeck.SpecialNames;
import main.eventCardParents.CustomUseDiscardable;

public class AllTheMarbles extends CustomUseDiscardable
{
	public AllTheMarbles()
	{
		super(PossibleTarget.None, ECRB.get("AllTheMarbles.name"), ECRB.get("AllTheMarbles.description"), SpecialNames.ToyStore, 2); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler.instance.getPlayer(GameHandler.instance.getTurn()).setMovesRemaining(0);
		GameHandler.instance.nextGameState();
		GameHandler.instance.nextGameState();
		DialogHandler.showMessage(null, ECRB.get("AllTheMarbles.message"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		return 1;
	}
}