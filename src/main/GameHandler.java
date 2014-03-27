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
}
