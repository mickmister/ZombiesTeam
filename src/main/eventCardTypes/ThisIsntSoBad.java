package main.eventCardTypes;

import gui.DialogHandler;

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
		super(PossibleTarget.None, "This Isn't So Bad", "Move any 2 zombies in play to a random location on the map.");
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler game = GameHandler.instance;
		// Set up
		if (num == 0)
		{
			game.setGameState(GameState.zombieMovement);
			setTurn(game.getTurn());
			game.setTurn(getActivator().getNumber());
			getActivator().setMovesRemaining(2);
			this.doneSetUp = true;
			System.out.println("Setting up Card.");
			DialogHandler.showMessage(null, "Entering zombie teleportation.", getName(), JOptionPane.INFORMATION_MESSAGE);
			return 42;
		}
		else if (num == 1)
		{
			if (this.doneSetUp)
			{
				return 42;
			}
			return 0;
		}
		else if (num == 2)
		{
			Point tileLocation = game.getMap().getTileFromIndex();
			Point cellLocation = game.getMap().getCellFromIndex();
			
			TileCell currentCell = game.getMap().getMapTile(tileLocation.y, tileLocation.x).getCell(cellLocation.y, cellLocation.x);
			currentCell.setZombie(false);
			while (true)
			{
				int randomIndex = (int) (Math.random() * 10 * 10 * 3 * 3);
				game.getMap().setZombieMovementIndex(randomIndex);
				
				Point newTileLocation = game.getMap().getTileFromIndex();
				Point newCellLocation = game.getMap().getCellFromIndex();
				
				TileCell newCell = game.getMap().getMapTile(newTileLocation.y, newTileLocation.x).getCell(newCellLocation.y, newCellLocation.x);
				if (!newCell.hasZombie() && newCell.isAccessible())
				{
					newCell.setZombie(true);
					return 42;
				}
			}
		}
		else
		{
			game.setGameState(GameState.tilePlacement);
			game.setTurn(getTurn());
			return 42;
		}
	}
	
}
