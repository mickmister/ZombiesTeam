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
//			this.deck.add(new AdrenalineRush());
//			this.deck.add(new Shotgun());
//			this.deck.add(new HystericalParalysis());
//			this.deck.add(new GainTwoHealthNoMove());
//			this.deck.add(new UntiedShoe());
//			this.deck.add(new KeysAreStillIn());
			this.deck.add(new ButterFingers());
			this.deck.add(new Skateboard());
		}
		
		Collections.shuffle(this.deck);
	}
	
	public void addActiveCard(EventCard card)
	{
		this.activeCards.add(card);
	}
	
	public EventCard removeByActivator(Player activator)
	{
		for (EventCard card: this.activeCards)
		{
			if(card.getActivator().equals(activator))
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
	
	public EventCard getNextCard()
	{
		return this.deck.remove(0);
	}
}