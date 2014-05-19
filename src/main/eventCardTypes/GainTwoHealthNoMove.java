package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.Player;
import main.eventCardParents.SingleUseCard;

public class GainTwoHealthNoMove extends SingleUseCard
{
	public GainTwoHealthNoMove()
	{
		super(PossibleTarget.Self, ECRB.get("GainTwoHealthNoMove.name"), ECRB.get("GainTwoHealthNoMove.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		Player player = getTargetPlayer();
		player.setMovesRemaining(0);
		player.addLifeToken();
		player.addLifeToken();
		GameHandler.instance.nextGameState();
		DialogHandler.showMessage(null, ECRB.get("GainTwoHealthNoMove.message"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		return 1;
	}
}