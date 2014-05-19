package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.eventCardParents.SingleUseCard;

public class UntiedShoe extends SingleUseCard
{
	
	public UntiedShoe()
	{
		super(PossibleTarget.Pick, ECRB.get("UntiedShoe.name"), ECRB.get("UntiedShoe.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, ECRB.get("UntiedShoe.message"), getName(), //$NON-NLS-1$ 
				JOptionPane.INFORMATION_MESSAGE);
		return num / 2;
	}
	
}
