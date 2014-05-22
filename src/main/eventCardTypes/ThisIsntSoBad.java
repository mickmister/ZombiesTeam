package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import java.awt.Point;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.GameHandler.GameState;
import main.TileCell;
import main.eventCardParents.StateChangeCard;

public class ThisIsntSoBad extends StateChangeCard
{
	private boolean doneSetUp = false;
	
	public ThisIsntSoBad()
	{
		super(PossibleTarget.None, ECRB.get("ThisIsntSoBad.name"), ECRB.get("ThisIsntSoBad.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		if (num == 0)
		{
			return setUp();
		}
		else if (num == 1)
		{
			return isActive();
		}
		else if (num == 2)
		{
			return teleportZombie();
		}
		else
		{
			return restore();
		}
	}
	
	private int setUp()
	{
		DialogHandler.showMessage(null, "It is now player " + (getActivator().getNumber() + 1) + "'s turn to move 2 zombies.", getName(), JOptionPane.INFORMATION_MESSAGE);
		GameHandler.instance.setGameState(GameState.zombieMovement);
		setTurn(GameHandler.instance.getTurn());
		GameHandler.instance.setTurn(getActivator().getNumber());
		getActivator().setMovesRemaining(2);
		GameHandler.instance.getMap().selectNextZombie();
		GameHandler.instance.getGuiStateData().mapTileDeckButtonEnabled = true;
		GameHandler.instance.getGuiStateData().rollDiceButtonEnabled = false;
		GameHandler.instance.fireDataChangedEvent(null);
		this.doneSetUp = true;
		return 42;
	}
	
	private int isActive()
	{
		if (this.doneSetUp)
		{
			return 42;
		}
		else
		{
			return 0;
		}
	}
	
	private int teleportZombie()
	{
		Point tileLocation = GameHandler.instance.getMap().getTileFromIndex();
		Point cellLocation = GameHandler.instance.getMap().getCellFromIndex();
		
		TileCell currentCell = GameHandler.instance.getMap().getMapTile(tileLocation.y, tileLocation.x).getCell(cellLocation.y, cellLocation.x);
		currentCell.setZombie(false);
		while (true)
		{
			int randomIndex = (int) (Math.random() * 10 * 10 * 3 * 3);
			GameHandler.instance.getMap().setZombieMovementIndex(randomIndex);
			
			Point newTileLocation = GameHandler.instance.getMap().getTileFromIndex();
			Point newCellLocation = GameHandler.instance.getMap().getCellFromIndex();
			
			TileCell newCell = GameHandler.instance.getMap().getMapTile(newTileLocation.y, newTileLocation.x).getCell(newCellLocation.y, newCellLocation.x);
			if (!newCell.hasZombie() && newCell.isAccessible())
			{
				newCell.setZombie(true);
				return 42;
			}
		}
	}
	
	private int restore()
	{
		DialogHandler.showMessage(null, "Returning to the normal player's turn.", getName(), JOptionPane.INFORMATION_MESSAGE);
		GameHandler.instance.getMap().setZombieMovementIndex(-1);
		GameHandler.instance.getGuiStateData().mapTileDeckButtonEnabled = true;
		GameHandler.instance.getGuiStateData().rollDiceButtonEnabled = false;
		GameHandler.instance.setGameState(GameState.tilePlacement);
		GameHandler.instance.setTurn(getTurn());
		checkRemove();
		GameHandler.instance.fireDataChangedEvent(null);
		return 42;
	}
}