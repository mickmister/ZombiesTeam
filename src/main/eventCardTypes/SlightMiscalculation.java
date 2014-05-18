package main.eventCardTypes;

import gui.DialogHandler;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.MapTile;

public class SlightMiscalculation extends OneUseCard
{

	public SlightMiscalculation()
	{
		super(PossibleTarget.Self, "Slight Miscalculation", "Play on any building. The number of zombies present is doubled, up to all the legal spaces");
	}

	@Override
	public int behavior(int num)
	{
		MapTile tileChoice = DialogHandler.showBulidingChoice(getName());
		
		doubleZombiesOnTile(tileChoice);
		
		return 1;
	}

	private void doubleZombiesOnTile(MapTile tileChoice) 
	{
		int zombieCount = 0;
		for(int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				if(tileChoice.getCell(y, x).hasZombie())
				{
					zombieCount++;
				}
			}
		}
		for(int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				if(!tileChoice.getCell(y, x).hasZombie() && tileChoice.getCell(y, x).isAccessible())
				{
					tileChoice.getCell(y, x).setZombie(true);
					zombieCount--;
				}
				if(zombieCount == 0) return;
			}
		}		
	}
}
