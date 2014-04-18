package main;

import java.awt.*;

import main.GameHandler.*;
import main.MapTile.Shape;

public class Map
{
	private final int SIZE = 11;
	private MapTile[][] mapTiles;
	private MapTile tempTile;
	private MapTile tempZombieTile;
	private MapTile tempBulletTile;
	private Point tempPos;
	
	public Map()
	{
		this.mapTiles = new MapTile[this.SIZE][this.SIZE];
		for (int y = 0; y < this.SIZE; y += 1)
		{
			for (int x = 0; x < this.SIZE; x += 1)
			{
				this.mapTiles[y][x] = new MapTile(Shape.empty, null);
			}
		}
		this.mapTiles[this.SIZE / 2][this.SIZE / 2] = new MapTile(Shape.quad, null);
		this.tempTile = null;
		this.tempZombieTile = null;
		this.tempBulletTile = null;
		this.tempPos = new Point(this.SIZE / 2, this.SIZE / 2);
	}
	
	public MapTile getMapTile(int row, int col)
	{
		return this.mapTiles[row][col];
	}
	
	public void addTempTile(MapTile tile)
	{
		this.tempTile = tile;
		this.tempPos = new Point(this.SIZE / 2, this.SIZE / 2);
	}
	
	public void placeTempTile() throws IllegalStateException
	{
		if (checkValidPosition(this.tempTile, this.tempPos.x, this.tempPos.y))
		{
			this.mapTiles[this.tempPos.y][this.tempPos.x] = this.tempTile;
			this.tempZombieTile = this.tempTile;
			this.tempBulletTile = this.tempTile;
			
			this.tempTile = null;
		}
		else
		{
			throw new IllegalStateException();
		}
	}
	
	public MapTile getTempTile()
	{
		return this.tempTile;
	}
	
	public MapTile getTempZombieTile()
	{
		return this.tempZombieTile;
	}
	
	public MapTile getTempBulletTile()
	{
		return this.tempBulletTile;
	}
	
	public Point getTempPos()
	{
		return this.tempPos;
	}
	
	public void setTempPos(Point point)
	{
		point.x = Math.max(0, point.x);
		point.x = Math.min(this.SIZE - 1, point.x);
		point.y = Math.max(0, point.y);
		point.y = Math.min(this.SIZE - 1, point.y);
		this.tempPos = point;
	}
	
	public void setTempTile(MapTile tile)
	{
		this.tempTile = tile;
	}
	
	public boolean checkValidPosition(MapTile newTile, int xPos, int yPos)
	{
		int emptyCount = 0;
		int edgeCount = 0;
		// Check if not hovering over empty
		MapTile current = this.mapTiles[yPos][xPos];
		if (!current.getShape().equals(Shape.empty))
		{
			return false;
		}
		
		// Check left
		if (xPos > 0)
		{
			MapTile left = this.mapTiles[yPos][xPos - 1];
			if (left.getShape().equals(Shape.empty))
			{
				emptyCount++;
			}
			if (left.getRightCell().isRoad() != newTile.getLeftCell().isRoad() && !left.getShape().equals(Shape.empty))
			{
				return false;
			}
		}
		else
		{
			edgeCount++;
		}
		
		// Check right
		if (xPos < this.SIZE - 1)
		{
			MapTile right = this.mapTiles[yPos][xPos + 1];
			if (right.getShape().equals(Shape.empty))
			{
				emptyCount++;
			}
			if (right.getLeftCell().isRoad() != newTile.getRightCell().isRoad() && !right.getShape().equals(Shape.empty))
			{
				return false;
			}
		}
		else
		{
			edgeCount++;
		}
		
		// Check top
		if (yPos > 0)
		{
			MapTile top = this.mapTiles[yPos - 1][xPos];
			if (top.getShape().equals(Shape.empty))
			{
				emptyCount++;
			}
			if (top.getBottomCell().isRoad() != newTile.getTopCell().isRoad() && !top.getShape().equals(Shape.empty))
			{
				return false;
			}
		}
		else
		{
			edgeCount++;
		}
		
		// Check bottom
		if (yPos < this.SIZE - 1)
		{
			MapTile bottom = this.mapTiles[yPos + 1][xPos];
			if (bottom.getShape().equals(Shape.empty))
			{
				emptyCount++;
			}
			if (bottom.getTopCell().isRoad() != newTile.getBottomCell().isRoad() && !bottom.getShape().equals(Shape.empty))
			{
				return false;
			}
		}
		else
		{
			edgeCount++;
		}
		
		if (edgeCount + emptyCount < 4)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getCurrentMessage(Player player)
	{
		if (!player.isPlayersTurn())
		{
			return "Not your turn";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.tilePlacement))
		{
			return "Draw and place a map tile";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombiePlacement))
		{
			MapTile tile = GameHandler.instance.getMap().getTempZombieTile();
			return "Place " + tile.getZombiesToPlace() + " more zombie(s)";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.bulletTokenPlacement))
		{
			MapTile tile = GameHandler.instance.getMap().getTempBulletTile();
			return "Place " + tile.getBulletsToPlace() + " more bullet token(s)";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.lifeTokenPlacement))
		{
			MapTile tile = GameHandler.instance.getMap().getTempBulletTile();
			return "Place " + tile.getLifeToPlace() + " more life token(s)";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.playerMovementDieRoll))
		{
			return "Roll the dice to move";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.playerMovement))
		{
			return player.getMovesRemaining() + " move(s) remaining";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieCombat))
		{
			return "Roll the dice to fight";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieMovementDieRoll))
		{
			return "Roll the dice to move zombies";
		}
		return "ERROR: No status defined!";
	}
}