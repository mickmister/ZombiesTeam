package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;

import main.MapTile;

public class MapTileDeckButton extends JButton implements ActionListener
{
	private ArrayList<MapTile> deck;
	
	public MapTileDeckButton()
	{
		setText("Map Tile Deck");
		addActionListener(this);
		
		this.deck = new ArrayList<MapTile>();
		addMapTiles();
		Collections.shuffle(this.deck);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		mapTileDeckClicked();
	}
	
	public void mapTileDeckClicked()
	{
		
	}
	
	private void addMapTiles()
	{
		
	}
}