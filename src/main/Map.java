package main;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Map extends JPanel
{
	private MapTile[][] mapTiles;
	
	public Map()
	{
		this.mapTiles = new MapTile[11][11];
		
		setLayout(new GridLayout(11, 11));
		setPreferredSize(new Dimension(2400, 2400));
		
		for (int y = 0; y < 11; y += 1)
		{
			for (int x = 0; x < 11; x += 1)
			{
				this.mapTiles[y][x] = new MapTile("tetris", null);
				add(this.mapTiles[y][x]);
			}
		}
	}
}