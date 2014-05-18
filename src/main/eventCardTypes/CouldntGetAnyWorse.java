package main.eventCardTypes;

import gui.DialogHandler;
import main.MapTile;

public class CouldntGetAnyWorse extends OneUseCard
{
	
	public CouldntGetAnyWorse()
	{
		super(PossibleTarget.Self, "Couldn't Get Any Worse", "Place a zombie on every legal space in a building not already occupied by a zombie.");
	}
	
	@Override
	public int behavior(int num)
	{
		MapTile tileChoice = DialogHandler.showBulidingChoice(getName());
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				if (tileChoice.getCell(y, x).isAccessible() && !tileChoice.getCell(y, x).hasZombie())
				{
					tileChoice.getCell(y, x).setZombie(true);
				}
			}
		}
		return 1;
	}
	
}
