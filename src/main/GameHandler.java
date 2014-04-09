package main;

import gui.ImageManager;

import java.util.ArrayList;

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
	
	public enum GameState
	{
		tilePlacement, zombiePlacement, playerMovementDieRoll, playerMovement, zombieMovementDieRoll, zombieMovement
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
			this.windows.add(new Window(i));
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
		switch (this.currentState)
		{
			case tilePlacement:
				this.currentState = GameState.zombiePlacement;
				for (int i = 0; i < this.windows.size(); i += 1)
				{
					this.windows.get(i).bottomPanel.mapTileDeckButton.setEnabled(false);
				}
				break;
			case zombiePlacement:
				this.currentState = GameState.playerMovementDieRoll;
				for (int i = 0; i < this.windows.size(); i += 1)
				{
					this.windows.get(i).bottomPanel.rollDiceButton.setEnabled(true);
				}
				break;
			case playerMovementDieRoll:
				this.currentState = GameState.playerMovement;
				for (int i = 0; i < this.windows.size(); i += 1)
				{
					this.windows.get(i).bottomPanel.rollDiceButton.setEnabled(true);
				}
				break;
			case playerMovement:
				this.currentState = GameState.zombieMovementDieRoll;
				break;
			case zombieMovementDieRoll:
				this.currentState = GameState.zombieMovement;
				break;
			case zombieMovement:
				this.currentState = GameState.tilePlacement;
				for (int i = 0; i < this.windows.size(); i += 1)
				{
					this.windows.get(i).bottomPanel.mapTileDeckButton.setEnabled(true);
				}
				nextTurn();
				break;
		}
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
}