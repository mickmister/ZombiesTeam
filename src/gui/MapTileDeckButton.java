package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;

import main.MapTile;
import main.MapTile.Shape;

public class MapTileDeckButton extends JButton implements ActionListener
{
	private int NUM_CARDS = 10; //Number of each kind of Map Tile
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
		for (int i = 0; i < NUM_CARDS; i++)
		{
			this.deck.add(new MapTile(Shape.L, null));
			this.deck.add(new MapTile(Shape.quad, null));
			this.deck.add(new MapTile(Shape.straight, null));
			this.deck.add(new MapTile(Shape.T, null));
		}
	}
}