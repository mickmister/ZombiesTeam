package main;

import gui.*;
import gui.Window;

import java.awt.*;
import java.util.*;

import main.DataListener.DataChangedEvent;
import main.eventCardTypes.*;

public class GameHandler
{
	public static GameHandler instance;
	private int numberOfPlayers;
	private ArrayList<Player> players;
	private Map map;
	private MapTileDeck tileDeck;
	private EventCardDeck eventDeck;
	private int turn;
	private GameState currentState;
	private ArrayList<Window> windows;
	private ArrayList<DataListener> listeners;
	private GuiStateData guiStateData;
	
	//@formatter:off
	/**
	 *         tilePlacement
	 *               |
	 *     bulletTokenPlacement
	 *               |
	 *      lifeTokenPlacement
	 *               |
	 *        zombiePlacement <---------------
	 *               |                       |
	 * ------------------------------        |
	 * |  Are there more zombies to |        |
	 * |  place in the new MapTile? |---------
	 * ------------------------------   Yes
	 *               | No
	 *               |
	 *     playerMovementDieRoll <-------------------------------------------------------------------------------
	 *               |                                                                                          |
	 *               |                                                                                          |
	 * ------------------------------                                                                           |
	 * |  Is there a zombie in the  |                            no moves                                       |
	 * | player's current TileCell? |----------> zombieCombat ---------------------------                       |
	 * ------------------------------   Yes            |                                |                       |
	 *               | No                              |                                |                       |
	 *               |                                 | move remaining                 |                       |
	 *               |                                 |                                |                       |
	 * ------------------------------                  |                                |                       |
	 * | Does the player have any   |                  |           move remaining        \                      |
	 * | moves remaining?           |----------> playerMovement -------------------------------------------------
	 * ------------------------------   Yes            |                                 /
	 *               | No                              |                                |
	 *               |                                 | no moves                       |
	 *               |                                 |                                |
	 *               |                                 |                                |
	 *     zombieMovementDieRoll <---------------------- <-------------------------------
	 *               |
	 *               |
	 *         zombieMovement
	 *               |
	 *               |
	 *           NEXT TURN
	 *
	 *
	 * @author Jacob Ryan.
	 *         Created Apr 12, 2014.
	 */
	//@formatter:on
	public enum GameState
	{
		tilePlacement, zombiePlacement, lifeTokenPlacement, bulletTokenPlacement, playerMovementDieRoll, zombieCombat, playerMovement, zombieMovementDieRoll, zombieMovement
	}
	
	public GameHandler(int numberOfPlayers)
	{
		GameHandler.instance = this;
		this.numberOfPlayers = numberOfPlayers;
		this.players = new ArrayList<Player>();
		this.map = new Map();
		this.tileDeck = new MapTileDeck();
		this.eventDeck = new EventCardDeck();
		this.turn = 0;
		this.currentState = GameState.tilePlacement;
		this.windows = new ArrayList<Window>();
		this.listeners = new ArrayList<DataListener>();
		this.guiStateData = new GuiStateData();
		
		for (int i = 0; i < this.numberOfPlayers; i += 1)
		{
			this.players.add(new Player(i));
		}
	}
	
	public void initWindows()
	{
		new ImageManager();
		for (int i = 0; i < this.numberOfPlayers; i += 1)
		{
			// Create windows in reverse order so first player appears on top.
			this.windows.add(new Window(this.numberOfPlayers - i - 1));
		}
		fireDataChangedEvent(null);
	}
	
	public Player getPlayer(int i)
	{
		return this.players.get(i);
	}
	
	public Map getMap()
	{
		return this.map;
	}
	
	public MapTileDeck getTileDeck()
	{
		return this.tileDeck;
	}
	
	public EventCardDeck getEventDeck()
	{
		return this.eventDeck;
	}
	
	public int getTurn()
	{
		return this.turn;
	}
	
	/**
	 * Increments the current turn, and automatically wraps back to 0 after the last player. Also
	 * resets the current state back to tilePlacement, makes the last player draw new Event Cards,
	 * and allows the new player to use an Event Card.
	 */
	public void nextTurn()
	{
		this.players.get(this.turn).drawNewCards();
		this.currentState = GameState.tilePlacement;
		this.turn = (this.turn + 1) % this.numberOfPlayers;
		this.players.get(this.turn).setCardPlayed(false);
		this.eventDeck.doCardAction(this.players.get(this.turn), HystericalParalysis.class, 0);
	}
	
	public GameState getCurrentState()
	{
		return this.currentState;
	}
	
	/**
	 * Moves to the next game state in the defined flow of a turn. See the diagram above for more
	 * information.
	 */
	public void nextGameState()
	{
		GameState old = this.currentState;
		Player player = getPlayer(getTurn());
		MapTile tile = getMap().getTempBulletTile();
		switch (this.currentState)
		{
			case tilePlacement:
				this.currentState = GameState.zombiePlacement;
				this.guiStateData.mapTileDeckButtonEnabled = false;
				break;
			case zombiePlacement:
				// tile = null if testing nextGameState
				if (tile == null || tile.getBulletsToPlace() == 0 && tile.getLifeToPlace() == 0)
				{
					this.currentState = GameState.playerMovementDieRoll;
					this.guiStateData.rollDiceButtonEnabled = true;
					int didAction = this.eventDeck.doCardAction(player, Fear.class, 0);
					if (didAction == 0)
					{
						didAction = this.eventDeck.doCardAction(player, GainTwoHealthNoMove.class, 0);
						if (didAction == 0)
						{
							this.eventDeck.doCardAction(player, KeysAreStillIn.class, 0);
						}
					}
				}
				else if (tile.getBulletsToPlace() == 0)
				{
					tile.setTempLifePos(new Point(1, 1));
					this.currentState = GameState.lifeTokenPlacement;
					this.guiStateData.rollDiceButtonEnabled = false;
				}
				else
				{
					tile.setTempBulletPos(new Point(1, 1));
					this.currentState = GameState.bulletTokenPlacement;
					this.guiStateData.rollDiceButtonEnabled = false;
				}
				break;
			case bulletTokenPlacement:
				if (tile.getLifeToPlace() == 0)
				{
					this.currentState = GameState.playerMovementDieRoll;
					this.guiStateData.rollDiceButtonEnabled = true;
					int didAction = this.eventDeck.doCardAction(player, Fear.class, 0);
					if (didAction == 0)
					{
						didAction = this.eventDeck.doCardAction(player, GainTwoHealthNoMove.class, 0);
						if (didAction == 0)
						{
							this.eventDeck.doCardAction(player, KeysAreStillIn.class, 0);
						}
					}
				}
				else
				{
					tile.setTempLifePos(new Point(1, 1));
					this.currentState = GameState.lifeTokenPlacement;
					this.guiStateData.rollDiceButtonEnabled = false;
				}
				break;
			case lifeTokenPlacement:
				this.currentState = GameState.playerMovementDieRoll;
				this.guiStateData.rollDiceButtonEnabled = true;
				int didAction = this.eventDeck.doCardAction(player, Fear.class, 0);
				if (didAction == 0)
				{
					didAction = this.eventDeck.doCardAction(player, GainTwoHealthNoMove.class, 0);
					if (didAction == 0)
					{
						this.eventDeck.doCardAction(player, KeysAreStillIn.class, 0);
					}
				}
				break;
			case playerMovementDieRoll:
				TileCell cell = getMap().getMapTile(player.getTileLocation().y, player.getTileLocation().x).getCell(player.getCellLocation().y,
						player.getCellLocation().x);
				if (cell.hasZombie())
				{
					this.currentState = GameState.zombieCombat;
					this.guiStateData.rollDiceButtonEnabled = true;
				}
				else
				{
					if (cell.hasBulletToken())
					{
						player.addBulletToken();
						cell.setBulletToken(false);
					}
					if (cell.hasLifeToken())
					{
						player.addLifeToken();
						cell.setLifeToken(false);
					}
					this.currentState = GameState.playerMovement;
					this.guiStateData.rollDiceButtonEnabled = false;
					if (player.getMovesRemaining() < 1)
					{
						// Skip from PlayerMovement -> ZombieMovementDieRoll.
						GameHandler.instance.nextGameState();
					}
				}
				break;
			case zombieCombat:
				this.currentState = GameState.playerMovement;
				this.guiStateData.rollDiceButtonEnabled = false;
				if (player.getMovesRemaining() < 1)
				{
					// Skip from PlayerMovement -> ZombieMovementDieRoll.
					GameHandler.instance.nextGameState();
				}
				break;
			case playerMovement:
				this.currentState = GameState.zombieMovementDieRoll;
				this.guiStateData.rollDiceButtonEnabled = true;
				break;
			case zombieMovementDieRoll:
				this.currentState = GameState.zombieMovement;
				this.guiStateData.rollDiceButtonEnabled = false;
				this.map.setZombieMovementIndex(0);
				this.map.selectNextZombie();
				break;
			case zombieMovement:
				this.guiStateData.mapTileDeckButtonEnabled = true;
				this.map.setZombieMovementIndex(-1);
				for (int tx = 0; tx < 11; tx += 1)
				{
					for (int ty = 0; ty < 11; ty += 1)
					{
						for (int cx = 0; cx < 3; cx += 1)
						{
							for (int cy = 0; cy < 3; cy += 1)
							{
								this.map.getMapTile(ty, tx).getCell(cy, cx).setZombieMoved(false);
							}
						}
					}
				}
				nextTurn();
				break;
		}
		fireDataChangedEvent(null);
		System.out.println("Changed state from: " + old.toString() + " -> " + this.currentState.toString()); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public void addDataListener(DataListener listener)
	{
		this.listeners.add(listener);
	}
	
	public void fireDataChangedEvent(DataChangedEvent event)
	{
		for (DataListener listener : this.listeners)
		{
			listener.dataChanged(event);
		}
	}
	
	public GuiStateData getGuiStateData()
	{
		return this.guiStateData;
	}
	
	public void gotoCombatOrMoveState()
	{
		this.currentState = GameState.playerMovementDieRoll;
		nextGameState();
	}
	
	public int getNumberOfPlayers()
	{
		return this.numberOfPlayers;
	}
}