package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.MapTile;
import main.MapTileDeck.SpecialNames;
import main.Player;
import main.TileCell;
import main.eventCardParents.SingleUseDiscardable;

public class Grenade extends SingleUseDiscardable
{
	
	public Grenade()
	{
		super(PossibleTarget.Self, ECRB.get("Grenade.name"), ECRB.get("Grenade.description"), //$NON-NLS-1$ //$NON-NLS-2$
				SpecialNames.ArmySurplus);
	}
	
	@Override
	public int behavior(int num)
	{
		Player player = getTargetPlayer();
		MapTile tile = GameHandler.instance.getMap().getMapTile(player.getTileLocation().y, player.getTileLocation().x);
		int numZombies = killZombiesInBuilding(tile, player);
		DialogHandler.showMessage(null,
				ECRB.get("Grenade.message_1") + numZombies + ECRB.get("Grenade.message_2"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		return 1;
	}
	
	private int killZombiesInBuilding(MapTile tile, Player player)
	{
		int numZombies = 0;
		ArrayList<TileCell> list = new ArrayList<TileCell>();
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				if (tile.getCell(y, x).isBuilding() || tile.getCell(y, x).isDoor())
				{
					list.add(tile.getCell(y, x));
				}
			}
		}
		for (TileCell cell : list)
		{
			if (cell.hasZombie())
			{
				player.incrementZombiesCaptured();
				cell.setZombie(false);
				numZombies++;
			}
		}
		return numZombies;
	}
	
}
