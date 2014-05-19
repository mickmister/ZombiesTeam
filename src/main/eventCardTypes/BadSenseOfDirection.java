package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import java.awt.Point;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.eventCardParents.SingleUseCard;

public class BadSenseOfDirection extends SingleUseCard
{
	
	public BadSenseOfDirection()
	{
		super(PossibleTarget.Pick, ECRB.get("BadSenseOfDirection.name"), ECRB.get("BadSenseOfDirection.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, ECRB.get("BadSenseOfDirection.message"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		GameHandler.instance.getPlayer(num).loseLifeToken();
		getTargetPlayer().teleport(new Point(5, 5), new Point(1, 1));
		return 1;
	}
	
}
