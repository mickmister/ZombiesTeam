package main;

import java.awt.Point;

/**
 * This class will hold the given player's status such as life tokens, bullet tokens, and zombies
 * captured.
 * 
 * @author watersdr. Created Mar 26, 2014.
 */
public class Player
{
	private int life;
	private int bullets;
	private int zombiesCaptured;
	private int number;
	private int xTile;
	private int yTile;
	private int xCell;
	private int yCell;
	private int movesRemaining;
	
	public Player(int number)
	{
		this.number = number;
		this.life = 3;
		this.bullets = 3;
		this.zombiesCaptured = 0;
		this.xTile = 5;
		this.yTile = 5;
		this.xCell = 1;
		this.yCell = 1;
		this.movesRemaining = 0;
	}
	
	public int getLifeTokens()
	{
		return this.life;
	}
	
	public int getBulletTokens()
	{
		return this.bullets;
	}
	
	public int getZombiesCaptured()
	{
		return this.zombiesCaptured;
	}
	
	public int getPlayerNumber()
	{
		return this.number;
	}
	
	public Point getTileLocation()
	{
		return new Point(this.xTile, this.yTile);
	}
	
	public Point getCellLocation()
	{
		return new Point(this.xCell, this.yCell);
	}
	
	public boolean isPlayersTurn()
	{
		return this.number == GameHandler.instance.getTurn();
	}
	public int getMovesRemaining()
	{
		return this.movesRemaining;
	}
	public void setMovesRemaining(int moves)
	{
		this.movesRemaining = moves;
	}
	public void tryMoveLeft()
	{
		Map map = GameHandler.instance.getMap();
		MapTile currentTile = map.getMapTile(yTile, xTile);
		TileCell currentCell = currentTile.getCell(yCell, xCell);
		TileCell targetCell = null;
		
		if(xCell == 0)
		{
			targetCell = map.getMapTile(yTile, xTile - 1).getCell(yCell, 2);
			if(checkDifferentTileMove(currentCell, targetCell))
			{
				xTile -= 1;
				xCell = 2;
				movesRemaining -=1;
				//Zombie check
			}
		}
		else
		{
			targetCell = currentTile.getCell(yCell, xCell - 1);
			if(checkSameTileMove(currentCell, targetCell))
			{
				xCell -=1;
				movesRemaining -=1;
				//Zombie check
			}
		}		
	}
	public void tryMoveRight()
	{
		Map map = GameHandler.instance.getMap();
		MapTile currentTile = map.getMapTile(yTile, xTile);
		TileCell currentCell = currentTile.getCell(yCell, xCell);
		TileCell targetCell = null;
		
		if(xCell == 2)
		{
			targetCell = map.getMapTile(yTile, xTile + 1).getCell(yCell, 0);
			if(checkDifferentTileMove(currentCell, targetCell))
			{
				xTile += 1;
				xCell = 0;
				movesRemaining -=1;
				//Zombie check
			}
		}
		else
		{
			targetCell = currentTile.getCell(yCell, xCell + 1);
			if(checkSameTileMove(currentCell, targetCell))
			{
				xCell +=1;
				movesRemaining -=1;
				//Zombie check
			}
		}		
	}
	
	public void tryMoveUp()
	{
		
		Map map = GameHandler.instance.getMap();
		MapTile currentTile = map.getMapTile(yTile, xTile);
		TileCell currentCell = currentTile.getCell(yCell, xCell);
		TileCell targetCell = null;
		
		if(yCell == 0)
		{
			targetCell = map.getMapTile(yTile - 1, xTile).getCell(2, xCell);
			if(checkDifferentTileMove(currentCell, targetCell))
			{
				yTile -= 1;
				yCell = 2;
				movesRemaining -=1;
				//Zombie check
			}
		}
		else
		{
			targetCell = currentTile.getCell(yCell - 1, xCell);
			if(checkSameTileMove(currentCell, targetCell))
			{
				yCell -= 1;
				movesRemaining -=1;
				//Zombie check
			}
		}		
	}
	public void tryMoveDown()
	{
		
		Map map = GameHandler.instance.getMap();
		MapTile currentTile = map.getMapTile(yTile, xTile);
		TileCell currentCell = currentTile.getCell(yCell, xCell);
		TileCell targetCell = null;
		
		if(yCell == 2)
		{
			targetCell = map.getMapTile(yTile + 1, xTile).getCell(0, xCell);
			if(checkDifferentTileMove(currentCell, targetCell))
			{
				yTile += 1;
				yCell = 0;
				movesRemaining -=1;
				//Zombie check
			}
		}
		else
		{
			targetCell = currentTile.getCell(yCell + 1, xCell);
			if(checkSameTileMove(currentCell, targetCell))
			{
				yCell += 1;
				movesRemaining -=1;
				//Zombie check
			}
		}		
	}
	private boolean checkDifferentTileMove(TileCell currentCell, TileCell targetCell)
	{
		if(currentCell.isRoad() && targetCell.isRoad())
		{
			currentCell.removePlayer(this);
			targetCell.addPlayer(this);			
			return true;
		}
		return false;
	}
	private boolean checkSameTileMove(TileCell currentCell, TileCell targetCell)
	{
		if ((currentCell.isBuilding() && targetCell.isRoad()) || (currentCell.isRoad() && targetCell.isBuilding()))
		{
			return false;
		}
		else
		{
			if(targetCell.isAcessible())
			{
				currentCell.removePlayer(this);
				targetCell.addPlayer(this);
				return true;
			}
			
			return false;
		}
	}
	
}