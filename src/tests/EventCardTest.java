package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gui.DialogHandler;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
import main.eventCardParents.EventCard;
import main.eventCardParents.EventCard.PossibleTarget;
import main.eventCardTypes.AdrenalineRush;
import main.eventCardTypes.AllTheMarbles;
import main.eventCardTypes.AlternateFoodSource;
import main.eventCardTypes.BadSenseOfDirection;
import main.eventCardTypes.BrainCramp;
import main.eventCardTypes.ButterFingers;
import main.eventCardTypes.Chainsaw;
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
import main.eventCardTypes.UntiedShoe;
import main.eventCardTypes.WereScrewed;
import main.eventCardTypes.ZombieMaster;

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
	public void testAlternateFoodSource()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		EventCard card = new AlternateFoodSource();
		card.setActivator(player);
		card.setTargetPlayer(null);
		GameHandler.instance.getEventDeck().addActiveCard(card);
		assertEquals(1, GameHandler.instance.getEventDeck().doCardAction(null, AlternateFoodSource.class, 0));
	}
	
	@Test
	public void testBrainCramp()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		EventCard card = new BrainCramp();
		card.setActivator(player);
		card.setTargetPlayer(player);
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
		card.setActivator(player1);
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
		DialogHandler.defaultReturn = 0;
		card.behavior(0);
		assertEquals(1, player2.getBulletTokens());
		card.behavior(0);
		assertEquals(0, player2.getBulletTokens());
		Shotgun card2 = new Shotgun();
		card2.setActivator(player2);
		game.getEventDeck().addActiveCard(card2);
		assertTrue(game.getEventDeck().activeDeckContains(card2));
		DialogHandler.defaultReturn = 1;
		card.behavior(0);
		assertFalse(game.getEventDeck().activeDeckContains(card2));
		// assertEquals(0, player2.getBulletTokens()); //was asserting if card was removed
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
		catch (Exception e)
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
		assertEquals(6, player.getBulletTokens());
	}
	
	@Test
	public void testChainsaw()
	{
		new GameHandler(2);
		EventCardDeck deck = GameHandler.instance.getEventDeck();
		Player player = GameHandler.instance.getPlayer(0);
		Chainsaw card = new Chainsaw();
		assertEquals(SpecialNames.LawnAndGarden, card.getBuildingName());
		card.setActivator(player);
		card.setTargetPlayer(player);
		deck.discard(card);
		assertEquals(5, deck.doDiscardedCardAction(player, Chainsaw.class, 3));
		assertEquals(5, deck.doDiscardedCardAction(player, Chainsaw.class, 3));
		assertEquals(5, deck.doDiscardedCardAction(player, Chainsaw.class, 3)); // check that it may
																				// be done multiple
																				// times
		assertTrue(deck.discardedDeckContains(card));
		GameHandler.instance.getEventDeck().removeUseForRoundCards(player);
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
	public void testAllTheMarbles()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		EventCard card = new AllTheMarbles();
		card.setActivator(player);
		GameHandler.instance.getEventDeck().addDiscardedCard(card);
		
		player.setMovesRemaining(5);
		assertEquals(true, GameHandler.instance.getEventDeck().discardedDeckContains(card));
		assertEquals(1, card.action(0));
		assertEquals(0, player.getMovesRemaining());
		
		GameHandler.instance.nextTurn();
		player = GameHandler.instance.getPlayer(1);
		assertEquals(true, GameHandler.instance.getEventDeck().discardedDeckContains(card));
		
		player.setMovesRemaining(5);
		assertEquals(1, card.action(0));
		assertEquals(0, player.getMovesRemaining());
		GameHandler.instance.nextTurn();
		GameHandler.instance.nextTurn();
		assertEquals(false, GameHandler.instance.getEventDeck().discardedDeckContains(card));
	}
	
	@Test
	public void testCorrectBuildings()
	{
		// Chainsaw - Lawn and Garden
		// Fire Axe - Fire Station
		// Lots of Ammo - Sporting Goods
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
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testThatCustomDiscardableGetsRemoved()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		EventCardDeck deck = GameHandler.instance.getEventDeck();
		MolotovCocktail card = new MolotovCocktail();
		card.setTargetPlayer(player);
		card.setActivator(player);
		deck.discard(card);
		assertTrue(deck.discardedDeckContains(card));
		GameHandler.instance.nextTurn();
		assertFalse(deck.discardedDeckContains(card));
	}
	
	@Test
	public void testDontThinkTheyreDead()
	{
		DialogHandler.defaultReturn = JOptionPane.YES_OPTION;
		for (int i = 0; i < 1000; i++)
		{
			new GameHandler(2);
			Player player1 = GameHandler.instance.getPlayer(0);
			Player player2 = GameHandler.instance.getPlayer(1);
			DontThinkTheyreDead card = new DontThinkTheyreDead();
			card.setTargetPlayer(player2);
			card.setActivator(player1);
			player2.incrementZombiesCaptured();
			player2.incrementZombiesCaptured();
			int bulletTokens = card.getTargetPlayer().getBulletTokens();
			int lifeTokens = card.getTargetPlayer().getLifeTokens();
			int numZombies = card.getTargetPlayer().getZombiesCaptured();
			int rolls = card.behavior(0);
			int roll1 = rolls % (2 << 15);
			int roll2 = rolls >> 16;
			
			if (roll1 < 4 && roll2 < 4)
			{
				int diff = 8 - roll1 - roll2;
				if (bulletTokens >= diff)
				{
					assertEquals(bulletTokens - diff, card.getTargetPlayer().getBulletTokens());
					assertEquals(lifeTokens, card.getTargetPlayer().getLifeTokens());
				}
				else if (bulletTokens >= 4 - roll1)
				{
					assertEquals(bulletTokens - (4 - roll1), card.getTargetPlayer().getBulletTokens());
					assertEquals(lifeTokens - 1, card.getTargetPlayer().getLifeTokens());
				}
				else if (bulletTokens >= 4 - roll2)
				{
					assertEquals(bulletTokens - (4 - roll2), card.getTargetPlayer().getBulletTokens());
					assertEquals(lifeTokens - 1, card.getTargetPlayer().getLifeTokens());
				}
				else
				{
					assertEquals(bulletTokens, card.getTargetPlayer().getBulletTokens());
					assertEquals(lifeTokens - 2, card.getTargetPlayer().getLifeTokens());
				}
			}
			else if (roll1 < 4)
			{
				if (bulletTokens >= 4 - roll1)
				{
					assertEquals(bulletTokens - (4 - roll1), card.getTargetPlayer().getBulletTokens());
				}
				else
				{
					assertEquals(lifeTokens - 1, card.getTargetPlayer().getLifeTokens());
				}
			}
			else if (roll2 < 4)
			{
				if (bulletTokens >= 4 - roll2)
				{
					assertEquals(bulletTokens - (4 - roll2), card.getTargetPlayer().getBulletTokens());
				}
				else
				{
					assertEquals(lifeTokens - 1, card.getTargetPlayer().getLifeTokens());
				}
			}
			else
			{
				assertEquals(bulletTokens, card.getTargetPlayer().getBulletTokens());
				assertEquals(lifeTokens, card.getTargetPlayer().getLifeTokens());
				assertEquals(numZombies, card.getTargetPlayer().getZombiesCaptured());
			}
		}
		DialogHandler.defaultReturn = JOptionPane.NO_OPTION;
		for (int i = 0; i < 1000; i++)
		{
			new GameHandler(2);
			Player player1 = GameHandler.instance.getPlayer(0);
			Player player2 = GameHandler.instance.getPlayer(1);
			DontThinkTheyreDead card = new DontThinkTheyreDead();
			card.setTargetPlayer(player2);
			card.setActivator(player1);
			player2.incrementZombiesCaptured();
			player2.incrementZombiesCaptured();
			int numZombies = card.getTargetPlayer().getZombiesCaptured();
			int rolls = card.behavior(0);
			int roll1 = rolls % (2 << 15);
			int roll2 = rolls >> 16;
			if (roll1 <= 3 || roll2 <= 3)
			{
				assertEquals(numZombies - 2, card.getTargetPlayer().getZombiesCaptured());
			}
			else
			{
				assertEquals(numZombies, card.getTargetPlayer().getZombiesCaptured());
			}
		}
	}
	
	@Test
	public void testCouldntGetAnyWorse()
	{
		DialogHandler.defaultReturn = 0;
		new GameHandler(2);
		MapTile tile = getGenericBuilding();
		GameHandler.instance.getMap().setTempTile(tile);
		GameHandler.instance.getMap().setTempPos(new Point(4, 5));
		GameHandler.instance.getMap().placeTempTile();
		CouldntGetAnyWorse card = new CouldntGetAnyWorse();
		card.setTargetPlayer(GameHandler.instance.getPlayer(0));
		card.behavior(0);
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				TileCell cell = tile.getCell(y, x);
				if (cell.isDoor() || cell.isBuilding())
				{
					assertTrue(cell.hasZombie());
				}
			}
		}
	}
	
	private MapTile getGenericBuilding()
	{
		MapTile tile = new MapTile(Shape.special, SpecialNames.ArmySurplus, "2 3 2 1 1 1 0 0 0" + " " + "2 0 2");
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				tile.getCell(y, x).setZombie(false);
			}
		}
		return tile;
	}
	
	@Test
	public void testSlightMiscalculation()
	{
		DialogHandler.defaultReturn = 0;
		new GameHandler(2);
		MapTile tile = getGenericBuilding();
		tile.getCell(1, 0).setZombie(true);
		tile.getCell(1, 1).setZombie(true);
		tile.getCell(1, 2).setZombie(true);
		int initialZombieCount = 3;
		
		GameHandler.instance.getMap().setTempTile(tile);
		GameHandler.instance.getMap().setTempPos(new Point(4, 5));
		GameHandler.instance.getMap().placeTempTile();
		SlightMiscalculation card = new SlightMiscalculation();
		card.setTargetPlayer(GameHandler.instance.getPlayer(0));
		card.behavior(0);
		
		int zombieCount = 0;
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				if (tile.getCell(y, x).hasZombie())
				{
					zombieCount++;
				}
			}
		}
		assertEquals(initialZombieCount * 2, zombieCount);
		
		tile.getCell(1, 0).setZombie(false);
		
		card.behavior(0);
		zombieCount = 0;
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				if (tile.getCell(y, x).hasZombie())
				{
					zombieCount++;
				}
			}
		}
		assertEquals(initialZombieCount * 2, zombieCount);
	}
	
	@Test
	public void testWereScrewed()
	{
		new GameHandler(2);
		Map map = GameHandler.instance.getMap();
		Player player = GameHandler.instance.getPlayer(0);
		WereScrewed card = new WereScrewed();
		card.setTargetPlayer(player);
		MapTile tile1 = new MapTile(Shape.quad);
		MapTile tile2 = new MapTile(Shape.quad);
		map.setTempTile(tile1);
		map.setTempPos(new Point(4, 5));
		map.placeTempTile();
		map.setTempTile(tile2);
		map.setTempPos(new Point (6, 5));
		map.placeTempTile();
		card.behavior(0);
		int numZombies = getNumZombies(map);
		assertEquals(10, numZombies);		
	}
	
	private int getNumZombies(main.Map map)
	{	
		int numZombies = 0;
		for(int tileY = 0; tileY < 11; tileY++)
		{
			for(int tileX = 0; tileX < 11; tileX++)
			{
				for(int cellY = 0; cellY < 3; cellY++)
				{
					for(int cellX = 0; cellX < 3; cellX++)
					{
						TileCell cell = map.getMapTile(tileY, tileX).getCell(cellY, cellX);
						if(cell.hasZombie())
						{
							numZombies++;
						}
					}
				}
			}
		}
		return numZombies;
	}
	
	@Test
	public void testZombieMaster()
	{
		new GameHandler(2);
		Map map = GameHandler.instance.getMap();
		Player player = GameHandler.instance.getPlayer(0);
		ZombieMaster card = new ZombieMaster();
		card.setTargetPlayer(player);
		MapTile tile1 = new MapTile(Shape.quad);
		MapTile tile2 = new MapTile(Shape.quad);
		map.setTempTile(tile1);
		map.setTempPos(new Point(4, 5));
		map.placeTempTile();
		map.setTempTile(tile2);
		map.setTempPos(new Point (6, 5));
		map.placeTempTile();
		card.behavior(0);
		int numZombies = getNumZombies(map);
		assertEquals(5, numZombies);
		assertFalse(map.getMapTile(5, 5).getCell(1, 1).hasZombie());
	}
}
