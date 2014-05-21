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

public class WereScrewed extends SingleUseCard implements InstantUseCard
{
	
	public WereScrewed()
	{
		super(PossibleTarget.Self, ECRB.get("WereScrewed.name"), ECRB.get("WereScrewed.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		Map map = GameHandler.instance.getMap();
		ArrayList<TileCell> cells = map.getNonZombiedCells(false);
		Collections.shuffle(cells);
		int i;
		for (i = 0; i < cells.size(); i++)
		{
			cells.get(i).setZombie(true);
			if (i == 9)
			{
				break;
			}
		}
		DialogHandler.showMessage(null,
				ECRB.get("WereScrewed.message_1") + (i + 1) + ECRB.get("WereScrewed.message_2"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		return 1;
	}
}