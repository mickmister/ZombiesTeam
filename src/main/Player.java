package main;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * This class will hold the given player's status such as life tokens, bullet tokens, and zombies
 * captured.
 * 
 * @author watersdr. Created Mar 26, 2014.
 */
public class Player
{
	private int number;
	private int lifeTokens;
	private int bulletTokens;
	private int zombiesCaptured;
	private int xTile;
	private int yTile;
	private int xCell;
	private int yCell;
	private int movesRemaining;
	private int zombieCombatRoll;
	private ArrayList<String> activeEventCards;
	private ArrayList<EventCard> handOfEventCards;
	private boolean cardPlayed;
	
	public Player(int number)
	{
		this.number = number;
		this.lifeTokens = 3;
		this.bulletTokens = 3;
		this.zombiesCaptured = 0;
		this.xTile = 5;
		this.yTile = 5;
		this.xCell = 1;
		this.yCell = 1;
		this.movesRemaining = 0;
		this.zombieCombatRoll = 0;
		this.activeEventCards = new ArrayList<String>();
		this.handOfEventCards = new ArrayList<EventCard>();
		this.cardPlayed = false;
		for (int i = 0; i < 3; i+=1)
		{
			this.handOfEventCards.add(GameHandler.instance.getEventDeck().getNextCard());		
		}
		
		
		GameHandler.instance.getMap().getMapTile(this.yTile, this.xTile).getCell(this.yCell, this.xCell).addPlayer(this);
	}
	
	public boolean checkCardPlayed()
	{
		return this.cardPlayed;
	}
	
	public void setCardPlayed(boolean played)
	{
		this.cardPlayed = played;
	}
	
	public EventCard getCardAtIndex(int i)
	{
		return this.handOfEventCards.get(i);
	}
	
	public void addActiveEventCard(String name)
	{
		this.activeEventCards.add(name);
	}
	
	public boolean checkIfCardIsActive(String name)
	{
		return this.activeEventCards.contains(name);
	}
	
	public void removeActiveEventCard(String name)
	{
		if (!this.activeEventCards.remove(name))
		{
			throw new IllegalArgumentException();
		}
	}
	
	public int getNumber()
	{
		return this.number;
	}
	
	public int getLifeTokens()
	{
		return this.lifeTokens;
	}
	
	public int getBulletTokens()
	{
		return this.bulletTokens;
	}
	
	public int getZombiesCaptured()
	{
		return this.zombiesCaptured;
	}
	
	public Point getTileLocation()
	{
		return new Point(this.xTile, this.yTile);
	}
	
	public Point getCellLocation()
	{
		return new Point(this.xCell, this.yCell);
	}
	
	public int getMovesRemaining()
	{
		return this.movesRemaining;
	}
	
	public void setMovesRemaining(int moves)
	{
		this.movesRemaining = moves;
	}
	
	public int getZombieCombatRoll()
	{
		return this.zombieCombatRoll;
	}
	
	public void setZombieCombatRoll(int zombieCombatRoll)
	{
		this.zombieCombatRoll = zombieCombatRoll;
	}
	
	public boolean isPlayersTurn()
	{
		return this.number == GameHandler.instance.getTurn();
	}
	
	public void tryMoveLeft()
	{
		Map map = GameHandler.instance.getMap();
		MapTile currentTile = map.getMapTile(this.yTile, this.xTile);
		TileCell currentCell = currentTile.getCell(this.yCell, this.xCell);
		TileCell targetCell = null;
		
		if (this.xCell == 0)
		{
			targetCell = map.getMapTile(this.yTile, this.xTile - 1).getCell(this.yCell, 2);
			if (checkDifferentTileMove(currentCell, targetCell))
			{
				this.xTile -= 1;
				this.xCell = 2;
				this.movesRemaining -= 1;
				// Zombie check
				GameHandler.instance.gotoCombatOrMoveState();
			}
		}
		else
		{
			targetCell = currentTile.getCell(this.yCell, this.xCell - 1);
			if (checkSameTileMove(currentCell, targetCell))
			{
				this.xCell -= 1;
				this.movesRemaining -= 1;
				// Zombie check
				GameHandler.instance.gotoCombatOrMoveState();
			}
		}
	}
	
	public void tryMoveRight()
	{
		Map map = GameHandler.instance.getMap();
		MapTile currentTile = map.getMapTile(this.yTile, this.xTile);
		TileCell currentCell = currentTile.getCell(this.yCell, this.xCell);
		TileCell targetCell = null;
		
		if (this.xCell == 2)
		{
			targetCell = map.getMapTile(this.yTile, this.xTile + 1).getCell(this.yCell, 0);
			if (checkDifferentTileMove(currentCell, targetCell))
			{
				this.xTile += 1;
				this.xCell = 0;
				this.movesRemaining -= 1;
				// Zombie check
				GameHandler.instance.gotoCombatOrMoveState();
			}
		}
		else
		{
			targetCell = currentTile.getCell(this.yCell, this.xCell + 1);
			if (checkSameTileMove(currentCell, targetCell))
			{
				this.xCell += 1;
				this.movesRemaining -= 1;
				// Zombie check
				GameHandler.instance.gotoCombatOrMoveState();
			}
		}
	}
	
	public void tryMoveUp()
	{
		Map map = GameHandler.instance.getMap();
		MapTile currentTile = map.getMapTile(this.yTile, this.xTile);
		TileCell currentCell = currentTile.getCell(this.yCell, this.xCell);
		TileCell targetCell = null;
		
		if (this.yCell == 0)
		{
			targetCell = map.getMapTile(this.yTile - 1, this.xTile).getCell(2, this.xCell);
			if (checkDifferentTileMove(currentCell, targetCell))
			{
				this.yTile -= 1;
				this.yCell = 2;
				this.movesRemaining -= 1;
				// Zombie check
				GameHandler.instance.gotoCombatOrMoveState();
			}
		}
		else
		{
			targetCell = currentTile.getCell(this.yCell - 1, this.xCell);
			if (checkSameTileMove(currentCell, targetCell))
			{
				this.yCell -= 1;
				this.movesRemaining -= 1;
				// Zombie check
				GameHandler.instance.gotoCombatOrMoveState();
			}
		}
	}
	
	public void tryMoveDown()
	{
		Map map = GameHandler.instance.getMap();
		MapTile currentTile = map.getMapTile(this.yTile, this.xTile);
		TileCell currentCell = currentTile.getCell(this.yCell, this.xCell);
		TileCell targetCell = null;
		
		if (this.yCell == 2)
		{
			targetCell = map.getMapTile(this.yTile + 1, this.xTile).getCell(0, this.xCell);
			if (checkDifferentTileMove(currentCell, targetCell))
			{
				this.yTile += 1;
				this.yCell = 0;
				this.movesRemaining -= 1;
				// Zombie check
				GameHandler.instance.gotoCombatOrMoveState();
			}
		}
		else
		{
			targetCell = currentTile.getCell(this.yCell + 1, this.xCell);
			if (checkSameTileMove(currentCell, targetCell))
			{
				this.yCell += 1;
				this.movesRemaining -= 1;
				// Zombie check
				GameHandler.instance.gotoCombatOrMoveState();
			}
		}
	}
	
	public boolean fightZombie(TileCell cell)
	{
		if (cell.hasZombie())
		{
			if (this.zombieCombatRoll > 3)
			{
				cell.setZombie(false);
				this.zombiesCaptured++;
				return true;
			}
			
			else if (this.zombieCombatRoll + this.bulletTokens > 3)
			{
				if (promptUseBulletTokens() == false)
				{
					loseLifeToken();
					return false;
				}
				else
				{
					cell.setZombie(false);
					this.bulletTokens = this.bulletTokens - (4 - this.zombieCombatRoll);
					this.zombiesCaptured++;
					return true;
				}
			}
			
			else
			{
				loseLifeToken();
				return false;
			}
		}
		return true;
	}
	
	private boolean loseLifeToken()
	{
		if (this.lifeTokens > 0)
		{
			this.lifeTokens--;
			return true;
		}
		else
		{
			resetPlayer();
			return false;
		}
	}
	
	private void resetPlayer()
	{
		GameHandler.instance.getMap().getMapTile(this.yTile, this.xTile).getCell(this.yCell, this.xCell).removePlayer(this);
		this.xTile = 5;
		this.yTile = 5;
		this.xCell = 1;
		this.yCell = 1;
		this.bulletTokens = 3;
		this.lifeTokens = 3;
		this.zombiesCaptured = (int) Math.ceil(this.zombiesCaptured / 2.0);
		GameHandler.instance.getMap().getMapTile(this.yTile, this.xTile).getCell(this.yCell, this.xCell).addPlayer(this);
		// Go from ZombieCombat to PlayerMovement to continue turn.
		GameHandler.instance.nextGameState();
		JOptionPane.showMessageDialog(null, "You died!  Your player will be reset.");
	}
	
	private boolean checkDifferentTileMove(TileCell currentCell, TileCell targetCell)
	{
		if (currentCell.isRoad() && targetCell.isRoad())
		{
			currentCell.removePlayer(this);
			targetCell.addPlayer(this);
			return true;
		}
		return false;
	}
	
	private boolean checkSameTileMove(TileCell currentCell, TileCell targetCell)
	{
		boolean a = currentCell.isBuilding() && !currentCell.isDoor() && targetCell.isRoad();
		boolean b = currentCell.isRoad() && targetCell.isBuilding() && !targetCell.isDoor();
		if (a || b)
		{
			return false;
		}
		else
		{
			if (targetCell.isAccessible())
			{
				currentCell.removePlayer(this);
				targetCell.addPlayer(this);
				return true;
			}
			
			return false;
		}
	}
	
	private boolean promptUseBulletTokens()
	{
		int result = JOptionPane.showConfirmDialog(null, "Do you want to use bullet tokens to defeat the zombie?", "Shoot or Die",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return result == JOptionPane.YES_OPTION;
	}
	
	public void addBulletToken()
	{
		this.bulletTokens++;
	}
	
	public void addLifeToken()
	{
		this.lifeTokens++;
	}
}