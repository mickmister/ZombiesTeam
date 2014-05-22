package main;

import java.util.ArrayList;
import java.util.Collections;

import main.eventCardParents.EventCard;
import main.eventCardParents.SingleUseDiscardable;
import main.eventCardParents.UseForRoundCard;
import main.eventCardTypes.AdrenalineRush;
import main.eventCardTypes.AllTheMarbles;
import main.eventCardTypes.AlternateFoodSource;
import main.eventCardTypes.BadSenseOfDirection;
import main.eventCardTypes.BrainCramp;
import main.eventCardTypes.ButterFingers;
import main.eventCardTypes.Chainsaw;
import main.eventCardTypes.Claustrophobia;
import main.eventCardTypes.CouldntGetAnyWorse;
import main.eventCardTypes.DontThinkTheyreDead;
import main.eventCardTypes.Fear;
import main.eventCardTypes.FireAxe;
import main.eventCardTypes.FirstAidKit;
import main.eventCardTypes.GainTwoHealthNoMove;
import main.eventCardTypes.Grenade;
import main.eventCardTypes.HystericalParalysis;
import main.eventCardTypes.KeysAreStillIn;
import main.eventCardTypes.LotsOfAmmo;
import main.eventCardTypes.MolotovCocktail;
import main.eventCardTypes.Shotgun;
import main.eventCardTypes.Skateboard;
import main.eventCardTypes.SlightMiscalculation;
import main.eventCardTypes.ThisIsntSoBad;
import main.eventCardTypes.UntiedShoe;
import main.eventCardTypes.WereScrewed;
import main.eventCardTypes.ZombieMaster;

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
		
		for (int i = 0; i < 10; i++)
		{
			/*this.deck.add(new AdrenalineRush());
			this.deck.add(new AllTheMarbles());
			this.deck.add(new AlternateFoodSource());
			this.deck.add(new BadSenseOfDirection());
			this.deck.add(new BrainCramp());
			this.deck.add(new ButterFingers());
			this.deck.add(new Chainsaw());
			this.deck.add(new Claustrophobia());
			this.deck.add(new CouldntGetAnyWorse());
			this.deck.add(new DontThinkTheyreDead());
			this.deck.add(new Fear());
			this.deck.add(new FireAxe());
			this.deck.add(new FirstAidKit());
			this.deck.add(new GainTwoHealthNoMove());
			this.deck.add(new Grenade());
			this.deck.add(new HystericalParalysis());
			this.deck.add(new KeysAreStillIn());
			this.deck.add(new LotsOfAmmo());
			this.deck.add(new MolotovCocktail());
			this.deck.add(new Shotgun());
			this.deck.add(new Skateboard());
			this.deck.add(new SlightMiscalculation());*/
			this.deck.add(new ThisIsntSoBad());
			/*this.deck.add(new UntiedShoe());
			this.deck.add(new WereScrewed());
			this.deck.add(new ZombieMaster());*/
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
	
	public void removeUseForRoundCards(Player currentPlayer)
	{
		for (int i = this.discardedCards.size() - 1; i >= 0; i -= 1)
		{
			EventCard card = this.discardedCards.get(i);
			if (card.getTargetPlayer() == null)
			{
				if (card.getActivator().equals(currentPlayer))
				{
					card.checkRemove();
				}
			}
			else
			{
				if (card.getTargetPlayer().equals(currentPlayer))
				{
					card.checkRemove();
				}
			}
		}
		for (int i = this.activeCards.size() - 1; i >= 0; i -= 1)
		{
			EventCard card = this.activeCards.get(i);
			if (card.getActivator().equals(currentPlayer))
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