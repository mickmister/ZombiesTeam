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
		GameHandler game = GameHandler.instance;
		// Set up
		if (num == 0)
		{
			DialogHandler.showMessage(null, "Setting up ThisIsntSoBad card.", getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			game.setGameState(GameState.zombieMovement);
			setTurn(game.getTurn());
			game.setTurn(getActivator().getNumber());
			getActivator().setMovesRemaining(2);
			DialogHandler
					.showMessage(
							null,
							"Turn: " + game.getTurn() + "\nState: " + game.getCurrentState() + "\nMoves: " + game.getPlayer(game.getTurn()).getMovesRemaining(), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			this.doneSetUp = true;
			return 42;
		}
		else if (num == 1)
		{
			if (this.doneSetUp)
			{
				DialogHandler.showMessage(null, "Has set up, returning 42.", getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
				return 42;
			}
			DialogHandler.showMessage(null, "Has NOT set up, returning 0.", getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			return 0;
		}
		else if (num == 2)
		{
			DialogHandler.showMessage(null, "Teleporting zombies!", getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
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
			DialogHandler.showMessage(null, "Restoring game state.", getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			game.setGameState(GameState.tilePlacement);
			game.setTurn(getTurn());
			return 42;
		}
	}
}