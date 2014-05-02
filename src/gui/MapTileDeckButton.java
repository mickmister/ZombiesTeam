package gui;

import java.awt.event.*;

import javax.swing.*;

import main.*;
import main.GameHandler.GameState;

public class MapTileDeckButton extends JButton implements ActionListener, DataListener
{
	public MapTileDeckButton()
	{
		setText("Map Tile Deck");
		addActionListener(this);
		GameHandler.instance.addDataListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		mapTileDeckClicked();
	}
	
	public void mapTileDeckClicked()
	{
		if (((Window) getTopLevelAncestor()).getPlayer().isPlayersTurn())
		{
			if (GameHandler.instance.getCurrentState() == GameState.tilePlacement)
			{
				if (GameHandler.instance.getMap().getTempTile() == null)
				{
					MapTile tile = GameHandler.instance.getTileDeck().getNextCard();
					if (tile != null)
					{
						boolean possible = false;
						for (int x = 0; x < 11; x += 1)
						{
							for (int y = 0; y < 11; y += 1)
							{
								for (int r = 0; r < 4; r += 1)
								{
									if (GameHandler.instance.getMap().checkValidPosition(tile, x, y))
									{
										possible = true;
									}
									tile.rotateTile();
								}
							}
						}
						if (possible)
						{
							GameHandler.instance.getMap().addTempTile(tile);
						}
						else
						{
							Dialog.showMessage(null, "No available places to put Map Tile, skipping.", "Map Tile", JOptionPane.INFORMATION_MESSAGE);
							GameHandler.instance.nextGameState();
							GameHandler.instance.nextGameState();
						}
					}
					else
					{
						Dialog.showMessage(null, "No more Map Tile cards, skipping.", "Map Tile", JOptionPane.INFORMATION_MESSAGE);
						GameHandler.instance.nextGameState();
						GameHandler.instance.nextGameState();
					}
				}
			}
		}
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		boolean canEnable = GameHandler.instance.getGuiStateData().mapTileDeckButtonEnabled;
		boolean myTurn = ((Window) getTopLevelAncestor()).getPlayer().isPlayersTurn();
		setEnabled(canEnable && myTurn);
	}
}