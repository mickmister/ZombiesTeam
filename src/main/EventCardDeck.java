package main;

import java.util.*;

public class EventCardDeck
{
	private ArrayList<EventCard> deck;
	
	public EventCardDeck()
	{
		this.deck = new ArrayList<EventCard>();
		add("Double Trouble", "You can move a lot now!");
		add("Double Trouble", "You can move a lot now!");
		add("Double Trouble", "You can move a lot now!");
		add("Double Trouble", "You can move a lot now!");
		add("Double Trouble", "You can move a lot now!");
		add("Double Trouble", "You can move a lot now!");		
		Collections.shuffle(this.deck);
	}
	
	private void add(String name, String description)
	{
		EventCard card = new EventCard(name, description);
		this.deck.add(card);
	}
	
	
	  public EventCard getNextCard() { return this.deck.remove(0); }
	 
}