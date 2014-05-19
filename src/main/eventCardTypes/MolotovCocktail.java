package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.MapTile;
import main.MapTileDeck.SpecialNames;
import main.TileCell;
import main.eventCardParents.CustomUseDiscardable;

public class MolotovCocktail extends CustomUseDiscardable
{
	public MolotovCocktail()
	{
		super(
				PossibleTarget.Self,
				"Molotov Cocktail",
				"Play this card in front of you when you are in the Gas Station. Discard this item to add +2 to all combat rolls against all zombies in your current building for 1 turn.",
				SpecialNames.GasStation, 1);
	}
	
	@Override
	public int behavior(int num)
	{
		MapTile tile = GameHandler.instance.getMap().getMapTile(getTargetPlayer().getTileLocation().y, getTargetPlayer().getTileLocation().x);
		TileCell cell = tile.getCell(getTargetPlayer().getCellLocation().y, getTargetPlayer().getCellLocation().x);
		if (cell.isBuilding() || cell.isDoor())
		{
			DialogHandler.showMessage(null, "Your Molotov Cocktail increased your combat roll by 2!", getName(), JOptionPane.INFORMATION_MESSAGE);
			return num + 2;
		}
		else
		{
			return num;
		}
	}
}