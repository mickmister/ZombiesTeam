package main;

import java.util.ArrayList;
import java.util.Collections;

import main.MapTile.Shape;

public class MapTileDeck
{
	private int NUM_CARDS = 10; // Number of each kind of Map Tile
	private ArrayList<MapTile> deck;
	
	public MapTileDeck()
	{
		this.deck = new ArrayList<MapTile>();
		addMapTiles();
		Collections.shuffle(this.deck);
	}
	
	public MapTile getNextCard()
	{
		return this.deck.remove(0);
	}
	
	private void addMapTiles()
	{
		for (int i = 0; i < this.NUM_CARDS; i++)
		{
			this.deck.add(new MapTile(Shape.L, null));
			this.deck.add(new MapTile(Shape.quad, null));
			this.deck.add(new MapTile(Shape.straight, null));
			this.deck.add(new MapTile(Shape.T, null));
		}
	}
}