package main;

import java.util.*;

public class EventCardDeck
{
	private ArrayList<EventCard> deck;
	
	public EventCardDeck()
	{
		this.deck = new ArrayList<EventCard>();
		Collections.shuffle(this.deck);
	}
	
	/*
	 * public EventCard getNextCard() { return this.deck.remove(0); }
	 */
}