package main;

import gui.DialogHandler;
import gui.GameWin;
import internationalization.RB;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.eventCardTypes.FirstAidKit;

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
		this.handOfEventCards = new ArrayList<EventCard>();
		this.cardPlayed = false;
		for (int i = 0; i < 3; i += 1)
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
	
	public void drawNewCards()
	{
		for (int i = 0; i < 3; i += 1)
		{
			if (this.handOfEventCards.get(i) == null)
			{
				EventCard card = GameHandler.instance.getEventDeck().getNextCard();
				this.handOfEventCards.set(i, card);
			}
		}
	}
	
	public EventCard getCardFromHand(int i)
	{
		return this.handOfEventCards.get(i);
	}
	
	public EventCard removeCardFromHand(int i)
	{
		EventCard card = this.handOfEventCards.get(i);
		this.handOfEventCards.set(i, null);
		return card;
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
				incrementZombiesCaptured();
				return true;
			}
			
			else if (this.zombieCombatRoll + this.bulletTokens > 3)
			{
				if (promptUseBulletTokens() == false)
				{
					if (!checkFirstAidCard())
					{
						loseLifeToken();
					}
					return false;
				}
				else
				{
					cell.setZombie(false);
					this.bulletTokens = this.bulletTokens - (4 - this.zombieCombatRoll);
					incrementZombiesCaptured();
					return true;
				}
			}
			
			else
			{
				if (!checkFirstAidCard())
				{
					loseLifeToken();
				}
				return false;
			}
		}
		return true;
	}
	
	private boolean checkFirstAidCard()
	{
		int didAction = GameHandler.instance.getEventDeck().doDiscardedCardAction(this, FirstAidKit.class, 0);
		return didAction == 1;
	}
	
	public boolean loseLifeToken()
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
	
	public void loseBulletToken()
	{
		if (this.bulletTokens > 0)
		{
			this.bulletTokens--;
		}
	}
	
	public void incrementZombiesCaptured()
	{
		this.zombiesCaptured += 1;
		if (this.zombiesCaptured >= 25)
		{
			// You win!
			new GameWin(this, true);
		}
		MapTile helipad = GameHandler.instance.getMap().getHelipad();
		if (helipad != null)
		{
			boolean allEmpty = true;
			for (int x = 0; x < 3; x += 1)
			{
				for (int y = 0; y < 3; y += 1)
				{
					if (helipad.getCell(y, x).hasZombie())
					{
						allEmpty = false;
					}
				}
			}
			if (allEmpty)
			{
				new GameWin(this, false);
			}
		}
	}
	
	private void resetPlayer()
	{
		resetPlayerLocation();
		this.bulletTokens = 3;
		this.lifeTokens = 3;
		this.zombiesCaptured = (int) Math.ceil(this.zombiesCaptured / 2.0);
		// Go from ZombieCombat to PlayerMovement to continue turn.
		GameHandler.instance.nextGameState();
		DialogHandler.showMessage(null, RB.get("Player.player_death_message"), RB.get("Player.player_death_title"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public void resetPlayerLocation()
	{
		GameHandler.instance.getMap().getMapTile(this.yTile, this.xTile).getCell(this.yCell, this.xCell).removePlayer(this);
		this.xTile = 5;
		this.yTile = 5;
		this.xCell = 1;
		this.yCell = 1;
		GameHandler.instance.getMap().getMapTile(this.yTile, this.xTile).getCell(this.yCell, this.xCell).addPlayer(this);
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
		int result = DialogHandler.showChoice(null, RB.get("Player.use_bullet_tokens_message"), RB.get("Player.use_bullet_tokens_title"), //$NON-NLS-1$ //$NON-NLS-2$
				JOptionPane.QUESTION_MESSAGE);
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