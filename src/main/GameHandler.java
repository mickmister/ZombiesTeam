package main;

import java.util.ArrayList;

public class GameHandler
{
	public static GameHandler instance;
	private int numberOfPlayers;
	private Map map;
	private MapTileDeck deck;
	private int turn;
	private ArrayList<Window> windows;
	private GameState currentState;
	
	public enum GameState
	{
		tilePlacement, zombiePlacement, playerMovementDieRoll, playerMovement, zombieMovementDieRoll, zombieMovement
	}
	
	public GameHandler(int numberOfPlayers)
	{
		GameHandler.instance = this;
		this.numberOfPlayers = numberOfPlayers;
		this.map = new Map();
		this.deck = new MapTileDeck();
		this.turn = 0;
		this.windows = new ArrayList<Window>();
		for (int i = 0; i < this.numberOfPlayers; i += 1)
		{
			this.windows.add(new Window(i));
		}
		this.currentState = GameState.tilePlacement;
	}
	
	public Player getPlayer(int i)
	{
		return this.windows.get(i).getPlayer();
	}
	
	public Map getMap()
	{
		return this.map;
	}
	
	public MapTileDeck getDeck()
	{
		return this.deck;
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
	
	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @return
	 */
	public GameState getCurrentState()
	{
		return this.currentState;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 */
	public void nextGameState()
	{
		switch (this.currentState)
		{
			case tilePlacement:
				this.currentState = GameState.zombiePlacement;
				break;
			case zombiePlacement:
				this.currentState = GameState.playerMovementDieRoll;
				break;
			case playerMovementDieRoll:
				this.currentState = GameState.playerMovement;
				break;
			case playerMovement:
				this.currentState = GameState.zombieMovementDieRoll;
				break;
			case zombieMovementDieRoll:
				this.currentState = GameState.zombieMovement;
				break;
			case zombieMovement:
				this.currentState = GameState.tilePlacement;
				nextTurn();
				break;
		}
	}
}
