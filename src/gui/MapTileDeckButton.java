package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.GameHandler;
import main.MapTile;

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
		MapTile tile = GameHandler.instance.getDeck().getNextCard();
		GameHandler.instance.getMap().addTempTile(tile);
	}
}