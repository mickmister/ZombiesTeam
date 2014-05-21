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

public class WereScrewed extends SingleUseCard implements InstantUseCard
{

	public WereScrewed() 
	{
		super(PossibleTarget.Self, "We're Screwed!", "Place zombies on ten legal spaces where there are no zombies");
	}

	@Override
	public int behavior(int num)
	{
		Map map = GameHandler.instance.getMap();
		ArrayList<TileCell> cells = map.getNonZombiedCells(false);
		Collections.shuffle(cells);
		int i;
		for(i = 0; i < cells.size(); i++)
		{
			cells.get(i).setZombie(true);
			if(i == 9)
			{
				break;
			}
		}
		DialogHandler.showMessage(null, "You placed " + (i + 1) + " zombies on the board!", getName(), JOptionPane.INFORMATION_MESSAGE);
		return 1;
	}
}