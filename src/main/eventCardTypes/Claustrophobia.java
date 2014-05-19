package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import java.awt.Point;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.TileCell;
import main.eventCardParents.UseForRoundCard;

public class Claustrophobia extends UseForRoundCard
{
	private boolean hasMovedPlayer;
	
	public Claustrophobia()
	{
		super(PossibleTarget.Pick, ECRB.get("Claustrophobia.name"), //$NON-NLS-1$
				ECRB.get("Claustrophobia.description"), 1); //$NON-NLS-1$
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
							DialogHandler.showMessage(null, ECRB.get("Claustrophobia.message_1") + (getTargetPlayer().getNumber() + 1) //$NON-NLS-1$
									+ ECRB.get("Claustrophobia.message_2"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ 
							return 1;
						}
					}
				}
			}
		}
		return 1;
	}
}