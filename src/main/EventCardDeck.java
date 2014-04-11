package main;

import java.util.ArrayList;
import java.util.Collections;

public class EventCardDeck
{
	private ArrayList<EventCard> deck;
	
	public EventCardDeck()
	{
		this.deck = new ArrayList<EventCard>();
		Collections.shuffle(this.deck);
	}
	
	/*public EventCard getNextCard()
	{
		return this.deck.remove(0);
	}*/
}