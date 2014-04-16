package main;

import java.util.*;

import main.EventCard.EventType;

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
		EventCard card = new EventCard(name, description, EventType.movement);
		this.deck.add(card);
	}
	private void addMovementCard(String name, String description, double multiplier)
	{
		
		
	}
	private void addCombatCard(String name, String description, int addition)
	{
		
	}
	
	public EventCard getNextCard() { return this.deck.remove(0); }
	 
}