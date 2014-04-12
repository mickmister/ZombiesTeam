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
					GameHandler.instance.getMap().addTempTile(tile);
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