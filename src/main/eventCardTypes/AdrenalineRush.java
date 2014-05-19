package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.GameHandler.GameState;
import main.eventCardParents.SingleUseCard;

public class AdrenalineRush extends SingleUseCard
{
	
	public AdrenalineRush()
	{
		super(PossibleTarget.Self, ECRB.get("AdrenalineRush.name"), ECRB.get("AdrenalineRush.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler game = GameHandler.instance;
		if (game.getCurrentState() == GameState.playerMovementDieRoll)
		{
			DialogHandler.showMessage(null, ECRB.get("AdrenalineRush.message_a"), getName(), //$NON-NLS-1$
					JOptionPane.INFORMATION_MESSAGE);
			return num * 2;
		}
		else
		// if (game.getCurrentState() == GameState.zombieCombat)
		{
			DialogHandler.showMessage(null, ECRB.get("AdrenalineRush.message_b"), getName(), //$NON-NLS-1$
					JOptionPane.INFORMATION_MESSAGE);
			return num + 2;
		}
	}
}