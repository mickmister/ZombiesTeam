package main.eventCardTypes;

import java.awt.Point;

import main.GameHandler;
import main.TileCell;
import main.eventCardParents.UseForRoundCard;

public class Claustrophobia extends UseForRoundCard
{
	private boolean hasMovedPlayer;
	
	public Claustrophobia()
	{
		super(PossibleTarget.Pick, "Claustrophobia", "Target player may not enter any building during their next turn.  If in a building, target player must move out of the building.", 1);
		this.hasMovedPlayer = false;
	}

	@Override
	public int behavior(int num)
	{
		if (!this.hasMovedPlayer)
		{
			this.hasMovedPlayer = true;
			Point tileLoc = getTargetPlayer().getTileLocation();
			Point cellLoc = getTargetPlayer().getCellLocation();
			TileCell cell = GameHandler.instance.getMap().getMapTile(tileLoc.y, tileLoc.x).getCell(cellLoc.y, cellLoc.x);
			if (cell.isBuilding())
			{
				for (int y = 0; y < 3; y += 1)
				{
					for (int x = 0; x < 3; x += 1)
					{
						TileCell newCell = GameHandler.instance.getMap().getMapTile(tileLoc.y, tileLoc.x).getCell(y, x);
						if (newCell.isRoad())
						{
							getTargetPlayer().teleport(tileLoc, new Point(x, y));
							return 1;
						}
					}
				}
			}
		}
		return 1;
	}
}