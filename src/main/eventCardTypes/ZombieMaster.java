package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.Map;
import main.TileCell;
import main.eventCardParents.InstantUseCard;
import main.eventCardParents.SingleUseCard;

public class ZombieMaster extends SingleUseCard implements InstantUseCard
{
	
	public ZombieMaster()
	{
		super(PossibleTarget.Self, ECRB.get("ZombieMaster.name"), ECRB.get("ZombieMaster.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		Map map = GameHandler.instance.getMap();
		ArrayList<TileCell> cells = map.getNonZombiedCells(true);
		Collections.shuffle(cells);
		int i;
		for (i = 0; i < cells.size(); i++)
		{
			cells.get(i).setZombie(true);
			if (i == 4)
			{
				break;
			}
		}
		DialogHandler.showMessage(null, ECRB.get("ZombieMaster.message_1") + (i + 1) + ECRB.get("ZombieMaster.message_2"), getName(), //$NON-NLS-1$ //$NON-NLS-2$
				JOptionPane.INFORMATION_MESSAGE);
		return 1;
	}
}