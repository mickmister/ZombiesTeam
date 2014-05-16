package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;

import main.EventCard;
import main.EventCard.PossibleTarget;
import main.EventCardDeck;
import main.GameHandler;
import main.GameHandler.GameState;
import main.Map;
import main.MapTile;
import main.MapTile.Shape;
import main.MapTileDeck.SpecialNames;
import main.Player;
import main.TileCell;
import main.TileCell.CellType;
import main.eventCardTypes.AdrenalineRush;
import main.eventCardTypes.BadSenseOfDirection;
import main.eventCardTypes.ButterFingers;
import main.eventCardTypes.Chainsaw;
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
import main.eventCardTypes.UntiedShoe;

import org.junit.Test;

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
		game.nextGameState(); // player movement die roll now
		
		int result = card.action(0);
		assertEquals(1, result);
		assertEquals(GameState.zombieMovementDieRoll, game.getCurrentState());
	}
	
	@Test
	public void testGainTwoHealthNoMove()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(0);
		GainTwoHealthNoMove card = new GainTwoHealthNoMove();
		card.setTargetPlayer(player);
		
		game.nextGameState();
		game.nextGameState(); // player movement die roll now
		
		int result = card.action(0);
		assertEquals(1, result);
		assertEquals(GameState.zombieMovementDieRoll, game.getCurrentState());
		assertEquals(5, player.getLifeTokens());
	}
	
	@Test
	public void testBadSenseOfDirection()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player1 = game.getPlayer(0);
		Player player2 = game.getPlayer(1);
		BadSenseOfDirection card = new BadSenseOfDirection();
		card.setTargetPlayer(player2);
		player2.setMovesRemaining(1);
		player2.tryMoveLeft();
		assertEquals(0, player2.getCellLocation().x);
		card.action(0);
		assertEquals(2, player1.getLifeTokens());
		assertEquals(1, player2.getCellLocation().x);
		
	}
	
	@Test
	public void testButterFingers()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player1 = game.getPlayer(0);
		Player player2 = game.getPlayer(1);
		ButterFingers card = new ButterFingers();
		card.setActivator(player1);
		card.setTargetPlayer(player2);
		assertEquals(3, player2.getBulletTokens());
		card.behavior(0);
		assertEquals(1, player2.getBulletTokens());
		card.behavior(0);
		assertEquals(0, player2.getBulletTokens());
		Shotgun card2 = new Shotgun();
		card2.setActivator(player2);
		game.getEventDeck().addActiveCard(card2);
		assertTrue(game.getEventDeck().activeDeckContains(card2));
		card.behavior(0);
		assertFalse(game.getEventDeck().activeDeckContains(card2));
	}
	
	@Test
	public void testSkateboard()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		Skateboard card = new Skateboard();
		assertEquals(SpecialNames.SkateShop, card.getBuildingName());
		card.setActivator(player);
		card.setTargetPlayer(player);
		int base = 5;
		int expected = 7;
		int result = card.action(base);
		assertEquals(expected, result);
	}
	
	@Test
	public void testKeysAreStillIn()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		KeysAreStillIn card = new KeysAreStillIn();
		card.setActivator(player);
		card.setTargetPlayer(player);
		GameHandler.instance.nextGameState();// zombie placement
		assertEquals(1, card.behavior(0));
		GameHandler.instance.nextGameState();
		assertEquals(GameState.playerMovement, GameHandler.instance.getCurrentState());
		assertEquals(10, player.getMovesRemaining());
	}
	
	@Test
	public void testFireAxe()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		FireAxe card = new FireAxe();
		card.setActivator(player);
		card.setTargetPlayer(player);
		GameHandler.instance.getEventDeck().addActiveCard(card);
		assertEquals(4, card.action(3));
		assertTrue(GameHandler.instance.getEventDeck().activeDeckContains(card));
	}
	
	@Test
	public void testGrenade()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		Grenade card = new Grenade();
		card.setTargetPlayer(player);
		assertEquals(0, player.getZombiesCaptured());
		MapTile zombieTile = getZombieBuildingTile();
		Map map = GameHandler.instance.getMap();
		map.setTempTile(zombieTile);
		map.setTempPos(new Point(4, 5));
		map.placeTempTile();
		player.tryMoveLeft();
		player.tryMoveLeft();
		player.tryMoveLeft();
		assertEquals(1, card.action(0));
		assertEquals(8, player.getZombiesCaptured());		
	}
	
	private MapTile getZombieBuildingTile()
	{
		MapTile tile = new MapTile(Shape.quad);
		Field cellType;
		Field hasZombie;
		try 
		{
			cellType = TileCell.class.getDeclaredField("type");		
			cellType.setAccessible(true);
			hasZombie = TileCell.class.getDeclaredField("hasZombie");
			hasZombie.setAccessible(true);
			for (int y = 0; y < 3; y++)
			{
				for (int x = 0; x < 3; x++)
				{
					cellType.set(tile.getCell(y, x), CellType.building);
					hasZombie.set(tile.getCell(y, x), true);
				}
				hasZombie.set(tile.getRightCell(), false);
				cellType.set(tile.getRightCell(), CellType.road);
				cellType.set(tile.getCell(1, 1), CellType.door);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tile;		
	}
	
	@Test
	public void testLotsOfAmmo()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		LotsOfAmmo card = new LotsOfAmmo();
		card.setTargetPlayer(player);
		assertEquals(SpecialNames.SportingGoods, card.getBuildingName());
		assertEquals(3, player.getBulletTokens());
		card.action(0);
		assertEquals(6,  player.getBulletTokens());		
	}
	
	@Test
	public void testChainsaw()
	{
		new GameHandler(2);
		EventCardDeck deck = GameHandler.instance.getEventDeck();
		Player player = GameHandler.instance.getPlayer(0);
		Chainsaw card = new Chainsaw();
		assertEquals(SpecialNames.LawnAndGarden, card.getBuildingName());
		card.setTargetPlayer(player);
		deck.addDiscardedActiveCard(card);
		assertEquals(5, deck.doDiscardedCardAction(player, Chainsaw.class, 3));
		assertEquals(5, deck.doDiscardedCardAction(player, Chainsaw.class, 3));
		assertEquals(5, deck.doDiscardedCardAction(player, Chainsaw.class, 3));	//check that it may be done multiple times
		assertTrue(deck.discardedDeckContains(card));
		card.customRemove();
		assertFalse(deck.discardedDeckContains(card));		
	}
	
	@Test
	public void testMolotovCocktail()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		MolotovCocktail card = new MolotovCocktail();
		card.setTargetPlayer(player);
		MapTile tile = getZombieBuildingTile();
		Map map = GameHandler.instance.getMap();
		map.setTempTile(tile);
		map.setTempPos(new Point(4, 5));
		map.placeTempTile();
		player.tryMoveLeft();
		player.tryMoveLeft();
		assertEquals(4, card.action(4));
		player.tryMoveLeft();
		assertEquals(6, card.action(4));
		player.tryMoveLeft();
		assertEquals(6, card.action(4));		
	}
	
	@Test
	public void testFirstAidKit()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		player.loseBulletToken();
		FirstAidKit card = new FirstAidKit();
		card.setTargetPlayer(player);
		EventCardDeck deck = GameHandler.instance.getEventDeck();
		deck.addActiveCard(card);
		TileCell cell = GameHandler.instance.getMap().getMapTile(5, 5).getLeftCell();
		cell.setZombie(true);
		GameHandler.instance.nextGameState();
		GameHandler.instance.nextGameState(); // player movement die roll now
		assertEquals(3, player.getLifeTokens());
		player.tryMoveLeft();
		player.setZombieCombatRoll(1);
		player.fightZombie(cell);
		assertEquals(3, player.getLifeTokens());
	}
	
	@Test
	public void testCorrectBuildings()
	{
		//Chainsaw - Lawn and Garden
		//Fire Axe - Fire Station
		//Lots of Ammo - Sporting Goods
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		Field buildingName;
		MapTile tile = GameHandler.instance.getMap().getMapTile(5, 5);
		Field cellType;
		try 
		{
		cellType = TileCell.class.getDeclaredField("type");		
		cellType.setAccessible(true);
		cellType.set(tile.getCell(1, 1), CellType.building);
		buildingName = MapTile.class.getDeclaredField("specialName");		
			buildingName.setAccessible(true);
			buildingName.set(tile, SpecialNames.LawnAndGarden);
			assertTrue(new Chainsaw().checkCorrectBuilding(player));
			buildingName.set(tile, SpecialNames.FireStation);
			assertTrue(new FireAxe().checkCorrectBuilding(player));
			buildingName.set(tile, SpecialNames.SportingGoods);
			assertTrue(new LotsOfAmmo().checkCorrectBuilding(player));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}
