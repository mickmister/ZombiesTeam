package main;

import java.util.*;

import main.eventCardTypes.*;

public class EventCardDeck
{
	private ArrayList<EventCard> deck;
	private ArrayList<EventCard> activeCards;
	
	public EventCardDeck()
	{
		this.deck = new ArrayList<EventCard>();
		this.activeCards = new ArrayList<EventCard>();
		for (int i = 0; i < 200; i++)
		{
			this.deck.add(new AdrenalineRush());
		}
		
		Collections.shuffle(this.deck);
	}
	
	public void addActiveCard(EventCard card)
	{
		this.activeCards.add(card);
	}
	
	public void removeActiveCard(EventCard card)
	{
		this.activeCards.remove(card);
	}
	
	public int doCardAction(Player p, Class<? extends EventCard> className, int num)
	{
		for (EventCard card : this.activeCards)
		{
			if (card.getTargetPlayer() == p)
			{
				if (card.getClass() == className)
				{
					return card.action(num);
				}
				
			}
		}
		
		return num;
	}
	
	public EventCard getNextCard()
	{
		return this.deck.remove(0);
	}
	
}