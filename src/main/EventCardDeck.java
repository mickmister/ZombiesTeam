package main;

import java.util.ArrayList;
import java.util.Collections;

import main.eventCardParents.CustomUseDiscardable;
import main.eventCardParents.EventCard;
import main.eventCardParents.SingleUseDiscardable;
import main.eventCardTypes.BrainCramp;
import main.eventCardTypes.ButterFingers;

public class EventCardDeck
{
	private ArrayList<EventCard> deck;
	private ArrayList<EventCard> activeCards;
	private ArrayList<EventCard> discardedCards;
	
	public EventCardDeck()
	{
		this.deck = new ArrayList<EventCard>();
		this.activeCards = new ArrayList<EventCard>();
		this.discardedCards = new ArrayList<EventCard>();
		
		for (int i = 0; i < 200; i++)
		{
			// this.deck.add(new AdrenalineRush());
			// this.deck.add(new Shotgun());
			// this.deck.add(new HystericalParalysis());
			// this.deck.add(new GainTwoHealthNoMove());
			// this.deck.add(new UntiedShoe());
			//this.deck.add(new KeysAreStillIn());
			this.deck.add(new ButterFingers());
			this.deck.add(new BrainCramp());
			//this.deck.add(new Skateboard());
			//this.deck.add(new FireAxe());
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
	
	public void addDiscardedCard(EventCard card)
	{
		this.discardedCards.add(card);
	}
	
	public void removeDiscardedCard(EventCard card)
	{
		this.discardedCards.remove(card);
	}
	
	public void removeUseForRoundCards(Player activator)
	{
		for (EventCard card : this.discardedCards)
		{
			if (card.getActivator().equals(activator))
			{
				card.checkRemove();
			}
		}
		for (EventCard card : this.activeCards)
		{
			if (card.getActivator().equals(activator))
			{
				if (card instanceof UseForRoundCard)
				{
					card.checkRemove();
				}
			}
		}
	}
	
	public int doCardAction(Player p, Class<? extends EventCard> className, int num)
	{
		for (EventCard card : this.activeCards)
		{
			if (card.getClass() == className)
			{
				if (card.getTargetPlayer() == p)
				{
					return card.action(num);
				}
			}
		}
		
		return num;
	}
	
	public int doDiscardedCardAction(Player p, Class<? extends EventCard> className, int num)
	{
		for (EventCard card : this.discardedCards)
		{
			if (card.getClass() == className)
			{
				if (card.getTargetPlayer() == p)
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
	
	public ArrayList<EventCard> getDiscardedCardsForPlayer(Player p)
	{
		ArrayList<EventCard> result = new ArrayList<EventCard>();
		for (EventCard card : this.discardedCards)
		{
			if (card.getActivator() == p)
			{
				result.add(card);
			}
		}
		return result;
	}
	
	public void discard(EventCard card)
	{
		removeActiveCard(card);
		if (card instanceof SingleUseDiscardable)
		{
			card.action(0);
		}
		else
		{
			addDiscardedCard(card);
		}
	}
	
	public boolean activeDeckContains(EventCard card)
	{
		return this.activeCards.contains(card);
	}
	
	public boolean discardedDeckContains(EventCard card)
	{
		return this.discardedCards.contains(card);
	}
}