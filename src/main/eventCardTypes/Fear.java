package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.Player;
import main.eventCardParents.SingleUseCard;

public class Fear extends SingleUseCard
{
	
	public Fear()
	{
		super(PossibleTarget.Pick, ECRB.get("Fear.name"), ECRB.get("Fear.description")); //$NON-NLS-1$ //$NON-NLS-2$
		
	}
	
	@Override
	public int behavior(int num)
	{
		Player player = getTargetPlayer();
		player.setMovesRemaining(0);
		GameHandler.instance.nextGameState();
		DialogHandler.showMessage(null, ECRB.get("Fear.message"), getName(), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$
		
		return 1;
	}
	
}
