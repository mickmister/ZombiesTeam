package main;

import gui.*;

import java.util.*;

import main.DataListener.DataChangedEvent;

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
	
	/**
	 *         tilePlacement
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
	 * @author ryanjm.
	 *         Created Apr 12, 2014.
	 */
	public enum GameState
	{
		tilePlacement, zombiePlacement, playerMovementDieRoll, zombieCombat, playerMovement, zombieMovementDieRoll, zombieMovement
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
	 * Increments the current turn, and automatically wraps back to 0 after the last player.
	 */
	public void nextTurn()
	{
		this.turn = (this.turn + 1) % this.numberOfPlayers;
	}
	
	public GameState getCurrentState()
	{
		return this.currentState;
	}
	
	/**
	 * Moves to the next game state in the defined flow of a turn. This goes: tilePlacement ->
	 * zombiePlacement -> playerMovementDieRoll -> playerMovement -> zombieMovementDieRoll ->
	 * zombieMovement
	 */
	public void nextGameState()
	{
		GameState old = this.currentState;
		Player player = getPlayer(getTurn());
		switch (this.currentState)
		{
			case tilePlacement:
				this.currentState = GameState.zombiePlacement;
				this.guiStateData.mapTileDeckButtonEnabled = false;
				break;
			case zombiePlacement:
				this.currentState = GameState.playerMovementDieRoll;
				this.guiStateData.rollDiceButtonEnabled = true;
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
				// TODO: Remove this testing line.
				try
				{
					Thread.sleep(1000);
				}
				catch (Exception e)
				{
				}
				nextGameState();
				break;
			case zombieMovement:
				this.currentState = GameState.tilePlacement;
				this.guiStateData.mapTileDeckButtonEnabled = true;
				nextTurn();
				break;
		}
		fireDataChangedEvent(null);
		System.out.println("Changed state from: " + old.toString() + " -> " + this.currentState.toString());
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
}