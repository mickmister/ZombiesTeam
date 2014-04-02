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
}