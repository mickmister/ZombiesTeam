package main.eventCardTypes;

import gui.DialogHandler;

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
		super(PossibleTarget.Self, "Zombie Master", "Place 5 zombies on any legal spaces in town not occupied by a player.");		
	}

	@Override
	public int behavior(int num)
	{
		Map map = GameHandler.instance.getMap();
		ArrayList<TileCell> cells = map.getNonZombiedCells(true);
		Collections.shuffle(cells);
		int i;
		for(i = 0; i < cells.size(); i++)
		{
			cells.get(i).setZombie(true);
			if(i == 4)
			{
				break;
			}
		}
		DialogHandler.showMessage(null, "You placed " + (i + 1) + " zombies on the board where there are no players!", getName(), JOptionPane.INFORMATION_MESSAGE);
		return 1;
	}
}