package main;

import java.util.*;

import main.eventCardTypes.*;

public class EventCardDeck
{
	private ArrayList<EventCard> deck;
	private ArrayList<EventCard> activeCards;
	private ArrayList<EventCard> discardedActiveCards;
	
	public EventCardDeck()
	{
		this.deck = new ArrayList<EventCard>();
		this.activeCards = new ArrayList<EventCard>();
		this.discardedActiveCards = new ArrayList<EventCard>();
		
		for (int i = 0; i < 200; i++)
		{
			// this.deck.add(new AdrenalineRush());
			// this.deck.add(new Shotgun());
			// this.deck.add(new HystericalParalysis());
			// this.deck.add(new GainTwoHealthNoMove());
			// this.deck.add(new UntiedShoe());
			this.deck.add(new KeysAreStillIn());
			this.deck.add(new ButterFingers());
			this.deck.add(new Skateboard());
			this.deck.add(new FireAxe());
		}
		
		Collections.shuffle(this.deck);
	}
	
	public void addDiscardedActiveCard(EventCard card)
	{
		this.discardedActiveCards.add(card);
	}
	
	public void removeDiscardedActiveCard(EventCard card)
	{
		this.discardedActiveCards.remove(card);
	}
	
	public void addActiveCard(EventCard card)
	{
		this.activeCards.add(card);
	}
	
	public EventCard removeByActivator(Player activator)
	{
		for (EventCard card : this.activeCards)
		{
			if (card.getActivator().equals(activator))
			{
				removeActiveCard(card);
				return card;
			}
		}
		return null;
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
	
	public int doDiscardedCardAction(Player p, Class<? extends EventCard> className, int num)
	{
		for (EventCard card : this.discardedActiveCards)
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
	
	public ArrayList<EventCard> getActiveCardsForPlayer(Player p)
	{
		ArrayList<EventCard> result = new ArrayList<EventCard>();
		for (EventCard card : this.activeCards)
		{
			if (card.getActivator() == p)
			{
				result.add(card);
			}
		}
		return result;
	}
	
	public boolean activeDeckContains(EventCard card)
	{
		return this.activeCards.contains(card);
	}
}