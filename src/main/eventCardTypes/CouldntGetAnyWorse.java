package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;
import main.MapTile;
import main.TileCell;
import main.eventCardParents.SingleUseCard;

public class CouldntGetAnyWorse extends SingleUseCard
{
	public CouldntGetAnyWorse()
	{
		super(PossibleTarget.Self, ECRB.get("CouldntGetAnyWorse.name"), ECRB.get("CouldntGetAnyWorse.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		MapTile tileChoice = DialogHandler.showBulidingChoice(null, getName());
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				TileCell cell = tileChoice.getCell(y, x);
				if ((cell.isDoor() || cell.isBuilding()) && !cell.hasZombie())
				{
					cell.setZombie(true);
				}
			}
		}
		return 1;
	}
}