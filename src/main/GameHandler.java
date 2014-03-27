package main;

import java.util.ArrayList;

public class GameHandler
{
	private int numberOfPlayers;
	private ArrayList<Window> windows;
	private int turn;
	
	public GameHandler(int numberOfPlayers)
	{
		this.numberOfPlayers = numberOfPlayers;
		this.windows = new ArrayList<Window>();
		for (int i = 0; i < this.numberOfPlayers; i += 1)
		{
			this.windows.add(new Window(i));
		}
		this.turn = 0;
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
