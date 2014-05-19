package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.eventCardParents.SingleUseCard;

public class HystericalParalysis extends SingleUseCard
{
	
	public HystericalParalysis()
	{
		super(PossibleTarget.Pick, ECRB.get("HystericalParalysis.name"), ECRB.get("HystericalParalysis.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler.instance.nextTurn();
		DialogHandler.showMessage(null, ECRB.get("HystericalParalysis.message"), getName(), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$
		return 1;
	}
	
}
