package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

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
		super(PossibleTarget.Self, ECRB.get("MolotovCocktail.name"), //$NON-NLS-1$
				ECRB.get("MolotovCocktail.description"), //$NON-NLS-1$
				SpecialNames.GasStation, 1);
	}
	
	@Override
	public int behavior(int num)
	{
		MapTile tile = GameHandler.instance.getMap().getMapTile(getTargetPlayer().getTileLocation().y, getTargetPlayer().getTileLocation().x);
		TileCell cell = tile.getCell(getTargetPlayer().getCellLocation().y, getTargetPlayer().getCellLocation().x);
		if (cell.isBuilding() || cell.isDoor())
		{
			DialogHandler.showMessage(null, ECRB.get("MolotovCocktail.message"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			return num + 2;
		}
		else
		{
			return num;
		}
	}
}