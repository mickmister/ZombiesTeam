package main;

import java.awt.*;
import java.awt.event.*;

import main.GameHandler.GameState;
import main.MapTile.Shape;

public class Map
{
	private final int SIZE = 11;
	private MapTile[][] mapTiles;
	private MapTile tempTile;
	private MapTile tempZombieTile;
	private MapTile tempBulletTile;
	private Point tempPos;
	private int zombieMovementIndex;
	
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
		this.zombieMovementIndex = -1;
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
	
	public int getZombieMovementIndex()
	{
		return this.zombieMovementIndex;
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
	
	public void setZombieMovementIndex(int index)
	{
		this.zombieMovementIndex = index;
	}
	
	public void selectNextZombie()
	{
		int max = 10 * 10 * 3 * 3;
		for (int i = 0; i < max; i += 1)
		{
			this.zombieMovementIndex = (this.zombieMovementIndex + 1) % max;
			Point cell = getCellFromIndex();
			Point tile = getTileFromIndex();
			TileCell check = getMapTile(tile.y, tile.x).getCell(cell.y, cell.x);
			if (check.hasZombie())
			{
				return;
			}
		}
		this.zombieMovementIndex = -1;
	}
	
	public void placeMovingZombie(KeyEvent e)
	{
		int old = this.zombieMovementIndex;
		Point cell = getCellFromIndex();
		Point tile = getTileFromIndex();
		TileCell oldTile = getMapTile(tile.y, tile.x).getCell(cell.y, cell.x);
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
				this.zombieMovementIndex -= 1;
				break;
			case KeyEvent.VK_RIGHT:
				this.zombieMovementIndex += 1;
				break;
			case KeyEvent.VK_UP:
				this.zombieMovementIndex -= 10 * 3;
				break;
			case KeyEvent.VK_DOWN:
				this.zombieMovementIndex += 10 * 3;
				break;
		}
		cell = getCellFromIndex();
		tile = getTileFromIndex();
		TileCell newTile = getMapTile(tile.y, tile.x).getCell(cell.y, cell.x);
		if (newTile.isAccessible() && !newTile.hasZombie() && !oldTile.hasZombieMoved())
		{
			oldTile.setZombie(false);
			newTile.setZombie(true);
			newTile.setZombieMoved(true);
			Player player = GameHandler.instance.getPlayer(GameHandler.instance.getTurn());
			player.setMovesRemaining(player.getMovesRemaining() - 1);
			if (player.getMovesRemaining() == 0)
			{
				GameHandler.instance.nextGameState();
			}
		}
		else
		{
			this.zombieMovementIndex = old;
		}
	}
	
	private Point getCellFromIndex()
	{
		Point p = getRawCell();
		int x = p.x % 3;
		int y = p.y % 3;
		return new Point(x, y);
	}
	
	private Point getTileFromIndex()
	{
		Point p = getRawCell();
		int x = p.x / 3;
		int y = p.y / 3;
		return new Point(x, y);
	}
	
	private Point getRawCell()
	{
		int y = this.zombieMovementIndex / (10 * 3);
		int x = this.zombieMovementIndex % (10 * 3);
		return new Point(x, y);
	}
	
	public boolean checkValidPosition(MapTile newTile, int xPos, int yPos)
	{
		int emptyCount = 0;
		int edgeCount = 0;
		int connectionCount = 0;
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
			else if (left.getRightCell().isRoad() && newTile.getLeftCell().isRoad())
			{
				connectionCount++;
			}
			else if (left.getRightCell().isRoad() != newTile.getLeftCell().isRoad())
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
			else if (right.getLeftCell().isRoad() && newTile.getRightCell().isRoad())
			{
				connectionCount++;
			}
			else if (right.getLeftCell().isRoad() != newTile.getRightCell().isRoad())
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
			else if (top.getBottomCell().isRoad() && newTile.getTopCell().isRoad())
			{
				connectionCount++;
			}
			else if (top.getBottomCell().isRoad() != newTile.getTopCell().isRoad())
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
			else if (bottom.getTopCell().isRoad() && newTile.getBottomCell().isRoad())
			{
				connectionCount++;
			}
			else if (bottom.getTopCell().isRoad() != newTile.getBottomCell().isRoad())
			{
				return false;
			}
		}
		else
		{
			edgeCount++;
		}
		
		if (edgeCount + emptyCount < 4 && connectionCount >= 1)
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
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieMovement))
		{
			return player.getMovesRemaining() + " zombie(s) to move";
		}
		return "ERROR: No status defined!";
	}
	
	public MapTile getHelipad()
	{
		for (int x = 0; x < this.SIZE; x += 1)
		{
			for (int y = 0; y < this.SIZE; y += 1)
			{
				MapTile tile = this.mapTiles[y][x];
				boolean isHelipad = true;
				for (int i = 0; i < 3; i += 1)
				{
					for (int j = 0; j < 3; j += 1)
					{
						if (!tile.getCell(i, j).isRoad())
						{
							isHelipad = false;
						}
					}
				}
				if (isHelipad)
				{
					return tile;
				}
			}
		}
		return null;
	}
}