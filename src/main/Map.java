package main;

import java.awt.Point;

import javax.swing.JOptionPane;

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
				this.mapTiles[y][x] = new MapTile(Shape.empty, null);
			}
		}
		this.mapTiles[SIZE / 2][SIZE / 2] = new MapTile(Shape.quad, null);
		this.tempTile = null;
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
	
	public void placeTempTile()
	{
		if (checkValidPosition(this.tempTile, this.tempPos.x, this.tempPos.y))
		{
			this.mapTiles[this.tempPos.y][this.tempPos.x] = tempTile;
			this.tempTile = null;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Not a valid location for the tile.");
		}
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
		point.x = Math.max(0, point.x);
		point.x = Math.min(SIZE - 1, point.x);
		point.y = Math.max(0, point.y);
		point.y = Math.min(SIZE - 1, point.y);
		this.tempPos = point;
	}
	
	public boolean checkValidPosition(MapTile newTile, int xPos, int yPos)
	{
		
		//Check if not hovering over empty
		MapTile current = this.mapTiles[yPos][xPos];
		if(!current.getShape().equals(Shape.empty))
		{
			return false;
		}
		
		//Check left
		if(xPos > 0)
		{
			MapTile left = this.mapTiles[yPos][xPos - 1];
			if(left.getRightCell().getAcessible() != newTile.getLeftCell().getAcessible() || !left.getShape().equals(Shape.empty))
			{
				return false;
			}
		}
		
		//Check right
		if(xPos < SIZE -1)
		{
			MapTile right = this.mapTiles[yPos][xPos + 1];
			if(right.getLeftCell().getAcessible() != newTile.getRightCell().getAcessible() || !right.getShape().equals(Shape.empty))
			{
				return false;
			}
		}
		
		//Check top
		if(yPos > 0)
		{
			MapTile top = this.mapTiles[yPos - 1][xPos];
			if(top.getBottomCell().getAcessible() != newTile.getTopCell().getAcessible() || !top.getShape().equals(Shape.empty))
			{
				return false;
			}	
		}
		
		//Check bottom
		if(yPos < SIZE -1)
		{
			MapTile bottom = this.mapTiles[yPos + 1][xPos];
			if(bottom.getTopCell().getAcessible() != newTile.getBottomCell().getAcessible() || !bottom.getShape().equals(Shape.empty))
			{
				return false;
			}
		}
		
		
		return true;
	}
}