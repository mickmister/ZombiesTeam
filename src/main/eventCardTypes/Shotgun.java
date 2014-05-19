package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.eventCardParents.MultipleUseCard;

public class Shotgun extends MultipleUseCard
{
	public Shotgun()
	{
		super(PossibleTarget.Self, ECRB.get("Shotgun.name"), ECRB.get("Shotgun.description"), 3); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, ECRB.get("Shotgun.message"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		return num + 1;
	}
}