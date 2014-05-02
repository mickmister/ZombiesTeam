package tests;

import static org.junit.Assert.*;

import java.lang.reflect.*;
import java.util.*;

import main.*;
import main.EventCard.PossibleTarget;
import main.GameHandler.GameState;
import main.eventCardTypes.*;

import org.junit.*;

public class EventCardTest
{
	
	@Test
	public void testAdrenalineRushMovementBehavior()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		AdrenalineRush card = new AdrenalineRush();
		int base1 = 5;
		int base2 = 3;
		int expected1 = 10;
		int expected2 = 6;
		int result;
		game.nextGameState();
		game.nextGameState(); // now in player movement die roll
		result = card.behavior(base1);
		assertEquals(expected1, result);
		result = card.behavior(base2);
		assertEquals(expected2, result);
	}
	
	@Test
	public void testAdrenalineRushCombatBehavior()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		AdrenalineRush card = new AdrenalineRush();
		int base1 = 5;
		int base2 = 3;
		int expected1 = 7;
		int expected2 = 5;
		int result;
		game.nextGameState();
		game.nextGameState();
		game.getMap().getMapTile(5, 5).getCell(1, 1).setZombie(true);
		game.nextGameState(); // now in zombie combat
		result = card.behavior(base1);
		assertEquals(expected1, result);
		result = card.behavior(base2);
		assertEquals(expected2, result);
	}
	
	@Test
	public void testAdrenalineRushIdentifiers()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(0);
		AdrenalineRush card = new AdrenalineRush();
		card.setTargetPlayer(player);
		assertEquals("Adrenaline Rush", card.getName());
		assertEquals("You can move a lot now!", card.getDescription());
		assertEquals(PossibleTarget.Self, card.getPossibleTarget());
		assertEquals(player, card.getTargetPlayer());
	}
	
	@Test
	public void testAdrenalineRushAction()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(0);
		game.nextGameState();
		game.nextGameState();
		AdrenalineRush card = new AdrenalineRush();
		int base = 5;
		int expected = 10;
		int result;
		card.setTargetPlayer(player);
		result = card.action(base);
		assertEquals(expected, result);
		
	}
	
	@Test
	public void testShotgunBehavior()
	{
		new GameHandler(2);
		Field gameState;
		try
		{
			gameState = GameHandler.class.getDeclaredField("currentState");
			gameState.setAccessible(true);
			gameState.set(GameHandler.instance, GameState.zombieCombat);
			Shotgun card = new Shotgun();
			int base1 = 4;
			int base2 = 5;
			int expected1 = 5;
			int expected2 = 6;
			int result;
			result = card.behavior(base1);
			assertEquals(expected1, result);
			result = card.behavior(base2);
			assertEquals(expected2, result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testShotgunAction()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(0);
		try
		{
			EventCardDeck deck = game.getEventDeck();
			Shotgun card = new Shotgun();
			card.setTargetPlayer(player);
			deck.addActiveCard(card);
			Field field = EventCardDeck.class.getDeclaredField("activeCards");
			field.setAccessible(true);
			Field gameState = GameHandler.class.getDeclaredField("currentState");
			gameState.setAccessible(true);
			gameState.set(GameHandler.instance, GameState.zombieCombat);
			ArrayList<EventCard> activeCards = (ArrayList<EventCard>) field.get(deck);
			assertTrue(activeCards.contains(card));
			
			int base1 = 3;
			int expected1 = 4;
			int base2 = 1;
			int expected2 = 2;
			int base3 = 5;
			int expected3 = 6;
			int result;
			
			result = card.action(base1);
			assertEquals(expected1, result);
			assertTrue(activeCards.contains(card));
			
			result = card.action(base2);
			assertEquals(expected2, result);
			assertTrue(activeCards.contains(card));
			
			result = deck.doCardAction(player, Shotgun.class, base3);
			assertEquals(expected3, result);
			assertFalse(activeCards.contains(card));
		}
		catch (Exception e)
		{
			
		}
	}
	
	@Test
	public void testSkipTurnAction()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(0);
		HystericalParalysis card = new HystericalParalysis();
		card.setTargetPlayer(player);
		
		assertEquals(0, game.getTurn());
		game.nextTurn();
		assertEquals(1, game.getTurn());
		game.nextTurn();
		card.action(0);
		assertEquals(1, game.getTurn());
	}
	
	@Test
	public void testUntiedShoe()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(0);
		UntiedShoe card = new UntiedShoe();
		card.setTargetPlayer(player);
		int base1 = 3;
		int base2 = 5;
		int expected1 = 1;
		int expected2 = 2;
		
		int result = card.action(base1);
		assertEquals(expected1, result);
		result = card.action(base2);
		assertEquals(expected2, result);
		
	}
	
	@Test
	public void testFear()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(0);
		Fear card = new Fear();
		card.setTargetPlayer(player);
		
		game.nextGameState();
		game.nextGameState(); //player movement die roll now
		
		int result = card.action(0);
		assertEquals(1, result);
		assertEquals(GameState.zombieMovementDieRoll, game.getCurrentState());
	}
}
