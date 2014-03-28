package main;

import java.awt.Point;

import main.MapTile.Shape;

public class Map
{
	private final int SIZE = 11;
	private MapTile[][] mapTiles;
	private MapTile tempTile;
	private Point tempPos;
	
	public Map()
	{
		this.mapTiles = new MapTile[SIZE][SIZE];
		for (int y = 0; y < SIZE; y += 1)
		{
			for (int x = 0; x < SIZE; x += 1)
			{
				this.mapTiles[y][x] = new MapTile(Shape.quad, null);
			}
		}
		this.tempTile = new MapTile(Shape.L, null);
		this.tempPos = new Point(5, 5);
	}
	
	public MapTile getMapTile(int row, int col)
	{
		return this.mapTiles[row][col];
	}
	
	public void addTempTile(MapTile tile)
	{
		this.tempTile = tile;
		this.tempPos = new Point(SIZE / 2, SIZE / 2);
	}
	
	public MapTile getTempTile()
	{
		return this.tempTile;
	}
	
	public Point getTempPos()
	{
		return this.tempPos;
	}
	
	public void setTempPos(Point point)
	{
		this.tempPos = point;
	}
}