package main.eventCardTypes;

import gui.DialogHandler;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.MapTile;
import main.MapTileDeck.SpecialNames;
import main.eventCardParents.SingleUseDiscardable;
import main.Player;
import main.TileCell;

public class Grenade extends SingleUseDiscardable
{
	
	public Grenade()
	{
		super(PossibleTarget.Self, "Grenade", "Play this card in front of you when you are in the Army Surplus Store. Discard this item to kill"
				+ "all zombies in 1 building on your current tile and add them to your collection. You must also lose 1 health.",
				SpecialNames.ArmySurplus);
	}
	
	@Override
	public int behavior(int num)
	{
		Player player = getTargetPlayer();
		MapTile tile = GameHandler.instance.getMap().getMapTile(player.getTileLocation().y, player.getTileLocation().x);
		int numZombies = killZombiesInBuilding(tile, player);
		DialogHandler.showMessage(null, "You killed " + numZombies + " zombies with your Grenade!", getName(), JOptionPane.INFORMATION_MESSAGE);
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
