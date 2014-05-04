package main;

import java.util.*;

import main.MapTile.Shape;

public class MapTileDeck
{
	private int NUM_CARDS = 4; // Number of each kind of Map Tile
	private ArrayList<MapTile> deck;
	
	public MapTileDeck()
	{
		this.deck = new ArrayList<MapTile>();
		addMapTiles();
		addSpecialMapTiles();
		Collections.shuffle(this.deck);
		
		String helipad = "1 1 1 1 1 1 1 1 1" + " " + "9 0 0"; // Helipad //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		this.deck.add(new MapTile(Shape.special, helipad));
	}
	
	public MapTile getNextCard()
	{
		if (this.deck.isEmpty())
		{
			return null;
		}
		else
		{
			return this.deck.remove(0);
		}
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
	
	private void addSpecialMapTiles()
	{
		String specialTiles = ""; //$NON-NLS-1$
		specialTiles += "2 2 2 2 3 2 2 1 2" + " " + "8 4 0" + "\n"; // Hospital //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 2 2 2 3 2 2 1 2" + " " + "6 2 3" + "\n"; // Lawn & Garden store //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 3 2 1 1 1 0 0 0" + " " + "3 3 0" + "\n"; // Drug store //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 2 2 2 3 2 0 1 0" + " " + "6 2 4" + "\n"; // Police Station //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 3 2 3 1 1 2 1 0" + " " + "3 1 2" + "\n"; // Hardware store //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 3 2 1 1 1 0 1 0" + " " + "3 0 1" + "\n"; // Skate shop //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 2 2 2 3 2 0 1 0" + " " + "6 2 4" + "\n"; // Sporting Goods store //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 3 2 1 1 1 0 1 0" + " " + "3 1 1" + "\n"; // Florist shop //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 2 3 2 1 1 3 1 0" + " " + "3 1 1" + "\n"; // Toy store //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 3 2 1 1 1 0 0 0" + " " + "2 0 2" + "\n"; // Army Surplus //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 2 2 2 3 2 0 1 2" + " " + "6 4 2" + "\n"; // Fire Station //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		specialTiles += "2 3 2 1 1 1 0 1 0" + " " + "3 1 2" + "\n"; // Gas Station //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		String[] lines = specialTiles.split("\n"); //$NON-NLS-1$
		for (String line : lines)
		{
			this.deck.add(new MapTile(Shape.special, line));
		}
	}
}