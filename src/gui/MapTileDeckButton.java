package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

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
		// Do crap.
	}
}