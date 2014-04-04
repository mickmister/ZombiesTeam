package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.GameHandler;
import main.GameHandler.GameState;
import main.MapTile;
import main.Window;

public class MapTileDeckButton extends JButton implements ActionListener
{
	public MapTileDeckButton()
	{
		setText("Map Tile Deck");
		addActionListener(this);
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
					MapTile tile = GameHandler.instance.getDeck().getNextCard();
					System.out.println(GameHandler.instance.getCurrentState());
					GameHandler.instance.getMap().addTempTile(tile);
				}
			}
		}
		
	}
}