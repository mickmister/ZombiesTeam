package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;
import main.MapTile;
import main.eventCardParents.SingleUseCard;

public class SlightMiscalculation extends SingleUseCard
{
	
	public SlightMiscalculation()
	{
		super(PossibleTarget.Self, ECRB.get("SlightMiscalculation.name"), //$NON-NLS-1$
				ECRB.get("SlightMiscalculation.description")); //$NON-NLS-1$
	}
	
	@Override
	public int behavior(int num)
	{
		MapTile tileChoice = DialogHandler.showBulidingChoice(null, getName());
		
		doubleZombiesOnTile(tileChoice);
		
		return 1;
	}
	
	private void doubleZombiesOnTile(MapTile tileChoice)
	{
		int zombieCount = 0;
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				if (tileChoice.getCell(y, x).hasZombie())
				{
					zombieCount++;
				}
			}
		}
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				if (!tileChoice.getCell(y, x).hasZombie() && tileChoice.getCell(y, x).isAccessible())
				{
					tileChoice.getCell(y, x).setZombie(true);
					zombieCount--;
				}
				if (zombieCount == 0)
				{
					return;
				}
			}
		}
	}
}
