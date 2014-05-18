package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gui.DialogHandler;

import java.awt.Point;
import java.lang.reflect.Method;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.Map;
import main.MapTile;
import main.Player;
import main.TileCell;
import main.TileCell.CellType;
import main.eventCardParents.EventCard;

import org.junit.Test;

public class PlayerTest
{
	@Test
	public void testConstructor()
	{
		Player test = new Player(0);
		assertNotNull(test);
	}
	
	@Test
	public void testStartingValues()
	{
		Player test = new Player(2);
		assertEquals(2, test.getNumber());
		assertEquals(3, test.getLifeTokens());
		assertEquals(3, test.getBulletTokens());
		assertEquals(0, test.getZombiesCaptured());
		assertEquals(new Point(5, 5), test.getTileLocation());
		assertEquals(new Point(1, 1), test.getCellLocation());
		assertEquals(0, test.getMovesRemaining());
		assertEquals(0, test.getZombieCombatRoll());
	}
	
	@Test
	public void testChangingValues()
	{
		Player test = new Player(2);
		test.setMovesRemaining(10);
		assertEquals(10, test.getMovesRemaining());
		test.setZombieCombatRoll(8);
		assertEquals(8, test.getZombieCombatRoll());
	}
	
	@Test
	public void testAddsSelfToMap()
	{
		new GameHandler(4);
		Player test = GameHandler.instance.getPlayer(2);
		TileCell cell = GameHandler.instance.getMap().getMapTile(5, 5).getCell(1, 1);
		assertEquals(test, cell.getPlayersOccupying().get(2));
	}
	
	@Test
	public void testIsMyTurn()
	{
		new GameHandler(4);
		GameHandler.instance.nextTurn(); // 0 -> 1
		GameHandler.instance.nextTurn(); // 1 -> 2
		Player test = new Player(2);
		assertEquals(true, test.isPlayersTurn());
		GameHandler.instance.nextTurn(); // 2 -> 3
		assertEquals(false, test.isPlayersTurn());
	}
	
	@Test
	public void testLoseLifeToken()
	{
		Player test = new Player(0);
		assertEquals(true, test.loseLifeToken());
		assertEquals(2, test.getLifeTokens());
		assertEquals(true, test.loseLifeToken());
		assertEquals(1, test.getLifeTokens());
		assertEquals(true, test.loseLifeToken());
		assertEquals(0, test.getLifeTokens());
		assertEquals(false, test.loseLifeToken());
		assertEquals(3, test.getLifeTokens());
	}
	
	@Test
	public void testDifferentTileMove()
	{
		try
		{
			Player player = new Player(0);
			Method method = Player.class.getDeclaredMethod("checkDifferentTileMove", TileCell.class, TileCell.class);
			method.setAccessible(true);
			TileCell road = new TileCell(CellType.road);
			TileCell building = new TileCell(CellType.building);
			TileCell door = new TileCell(CellType.door);
			
			assertEquals(true, method.invoke(player, road, road));
			assertEquals(false, method.invoke(player, road, building));
			assertEquals(false, method.invoke(player, road, door));
			assertEquals(false, method.invoke(player, building, road));
			assertEquals(false, method.invoke(player, building, building));
			assertEquals(false, method.invoke(player, building, door));
			assertEquals(false, method.invoke(player, door, road));
			assertEquals(false, method.invoke(player, door, building));
			assertEquals(false, method.invoke(player, door, door));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testSameTileMove()
	{
		try
		{
			Player player = new Player(0);
			Method method = Player.class.getDeclaredMethod("checkSameTileMove", TileCell.class, TileCell.class);
			method.setAccessible(true);
			TileCell grass = new TileCell(CellType.grass);
			TileCell road = new TileCell(CellType.road);
			TileCell building = new TileCell(CellType.building);
			TileCell door = new TileCell(CellType.door);
			
			assertEquals(false, method.invoke(player, grass, grass));
			// assertEquals(false, method.invoke(player, grass, road));
			// assertEquals(false, method.invoke(player, grass, building));
			// assertEquals(false, method.invoke(player, grass, door));
			assertEquals(false, method.invoke(player, road, grass));
			assertEquals(true, method.invoke(player, road, road));
			assertEquals(false, method.invoke(player, road, building));
			assertEquals(true, method.invoke(player, road, door));
			assertEquals(false, method.invoke(player, building, grass));
			assertEquals(false, method.invoke(player, building, road));
			assertEquals(true, method.invoke(player, building, building));
			assertEquals(true, method.invoke(player, building, door));
			assertEquals(false, method.invoke(player, door, grass));
			assertEquals(true, method.invoke(player, door, road));
			assertEquals(true, method.invoke(player, door, building));
			assertEquals(true, method.invoke(player, door, door));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void fightZombie()
	{
		Player player = new Player(0);
		TileCell tile = new TileCell(CellType.road);
		
		assertEquals(true, player.fightZombie(tile));
		assertEquals(0, player.getZombiesCaptured());
		
		tile.setZombie(true);
		player.setZombieCombatRoll(4);
		assertEquals(true, player.fightZombie(tile));
		assertEquals(1, player.getZombiesCaptured());
		
		tile.setZombie(true);
		player.setZombieCombatRoll(0);
		assertEquals(false, player.fightZombie(tile));
		assertEquals(2, player.getLifeTokens());
		
		tile.setZombie(true);
		player.setZombieCombatRoll(2);
		// Will NOT use bullet tokens and will lose a life token.
		DialogHandler.defaultReturn = JOptionPane.NO_OPTION;
		assertEquals(false, player.fightZombie(tile));
		assertEquals(1, player.getZombiesCaptured());
		assertEquals(3, player.getBulletTokens());
		assertEquals(1, player.getLifeTokens());
		
		tile.setZombie(true);
		player.setZombieCombatRoll(2);
		// Will use bullet tokens to defeat the zombie.
		DialogHandler.defaultReturn = JOptionPane.YES_OPTION;
		assertEquals(true, player.fightZombie(tile));
		assertEquals(2, player.getZombiesCaptured());
		assertEquals(1, player.getBulletTokens());
		assertEquals(1, player.getLifeTokens());
	}
	
	@Test
	public void testTryMoveLeft()
	{
		new GameHandler(2);
		Player player = new Player(0);
		player.setMovesRemaining(5);
		
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(1, 0).setCellType(CellType.grass);
		player.tryMoveLeft();
		assertEquals(1, player.getCellLocation().x);
		assertEquals(5, player.getMovesRemaining());
		
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(1, 0).setCellType(CellType.road);
		player.tryMoveLeft();
		assertEquals(0, player.getCellLocation().x);
		assertEquals(4, player.getMovesRemaining());
		
		// Now check move to other tile.
		GameHandler.instance.getMap().getMapTile(5, 4).getCell(1, 2).setCellType(CellType.grass);
		player.tryMoveLeft();
		assertEquals(0, player.getCellLocation().x);
		assertEquals(4, player.getMovesRemaining());
		
		GameHandler.instance.getMap().getMapTile(5, 4).getCell(1, 2).setCellType(CellType.road);
		player.tryMoveLeft();
		assertEquals(2, player.getCellLocation().x);
		assertEquals(3, player.getMovesRemaining());
	}
	
	@Test
	public void testTryMoveRight()
	{
		new GameHandler(2);
		Player player = new Player(0);
		player.setMovesRemaining(5);
		
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(1, 2).setCellType(CellType.grass);
		player.tryMoveRight();
		assertEquals(1, player.getCellLocation().x);
		assertEquals(5, player.getMovesRemaining());
		
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(1, 2).setCellType(CellType.road);
		player.tryMoveRight();
		assertEquals(2, player.getCellLocation().x);
		assertEquals(4, player.getMovesRemaining());
		
		// Now check move to other tile.
		GameHandler.instance.getMap().getMapTile(5, 6).getCell(1, 0).setCellType(CellType.grass);
		player.tryMoveRight();
		assertEquals(2, player.getCellLocation().x);
		assertEquals(4, player.getMovesRemaining());
		
		GameHandler.instance.getMap().getMapTile(5, 6).getCell(1, 0).setCellType(CellType.road);
		player.tryMoveRight();
		assertEquals(0, player.getCellLocation().x);
		assertEquals(3, player.getMovesRemaining());
	}
	
	@Test
	public void testTryMoveUp()
	{
		new GameHandler(2);
		Player player = new Player(0);
		player.setMovesRemaining(5);
		
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(0, 1).setCellType(CellType.grass);
		player.tryMoveUp();
		assertEquals(1, player.getCellLocation().y);
		assertEquals(5, player.getMovesRemaining());
		
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(0, 1).setCellType(CellType.road);
		player.tryMoveUp();
		assertEquals(0, player.getCellLocation().y);
		assertEquals(4, player.getMovesRemaining());
		
		// Now check move to other tile.
		GameHandler.instance.getMap().getMapTile(4, 5).getCell(2, 1).setCellType(CellType.grass);
		player.tryMoveUp();
		assertEquals(0, player.getCellLocation().y);
		assertEquals(4, player.getMovesRemaining());
		
		GameHandler.instance.getMap().getMapTile(4, 5).getCell(2, 1).setCellType(CellType.road);
		player.tryMoveUp();
		assertEquals(2, player.getCellLocation().y);
		assertEquals(3, player.getMovesRemaining());
	}
	
	@Test
	public void testTryMoveDown()
	{
		new GameHandler(2);
		Player player = new Player(0);
		player.setMovesRemaining(5);
		
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(2, 1).setCellType(CellType.grass);
		player.tryMoveDown();
		assertEquals(1, player.getCellLocation().y);
		assertEquals(5, player.getMovesRemaining());
		
		GameHandler.instance.getMap().getMapTile(5, 5).getCell(2, 1).setCellType(CellType.road);
		player.tryMoveDown();
		assertEquals(2, player.getCellLocation().y);
		assertEquals(4, player.getMovesRemaining());
		
		// Now check move to other tile.
		GameHandler.instance.getMap().getMapTile(6, 5).getCell(0, 1).setCellType(CellType.grass);
		player.tryMoveDown();
		assertEquals(2, player.getCellLocation().y);
		assertEquals(4, player.getMovesRemaining());
		
		GameHandler.instance.getMap().getMapTile(6, 5).getCell(0, 1).setCellType(CellType.road);
		player.tryMoveDown();
		assertEquals(0, player.getCellLocation().y);
		assertEquals(3, player.getMovesRemaining());
	}
	
	@Test
	public void testTokenAdditions()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		assertEquals(3, player.getBulletTokens());
		assertEquals(3, player.getLifeTokens());
		player.addBulletToken();
		player.addLifeToken();
		assertEquals(4, player.getBulletTokens());
		assertEquals(4, player.getLifeTokens());
	}
	
	@Test
	public void testCardHandBehavior()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		player.drawNewCards();
		EventCard card = player.getCardFromHand(0);
		EventCard removed = player.removeCardFromHand(0);
		assertEquals(card, removed);
		player.drawNewCards();
		player.setCardPlayed(true);
		assertTrue(player.checkCardPlayed());
	}
	
	@Test
	public void testWinConditions()
	{
		new GameHandler(2);
		Player player = GameHandler.instance.getPlayer(0);
		
		for (int i = 0; i < 25; i += 1)
		{
			player.setZombieCombatRoll(4);
			TileCell tileCell = new TileCell(CellType.road);
			tileCell.setZombie(true);
			player.fightZombie(tileCell);
		}
		
		new GameHandler(2);
		player = GameHandler.instance.getPlayer(0);
		Map map = GameHandler.instance.getMap();
		int count = 4 * 4 + 12;
		for (int i = 0; i < count; i += 1)
		{
			GameHandler.instance.getTileDeck().getNextCard();
		}
		MapTile helipad = GameHandler.instance.getTileDeck().getNextCard();
		map.addTempTile(helipad);
		map.setTempPos(new Point(5, 6));
		map.placeTempTile();
		for (int y = 0; y < 3; y += 1)
		{
			for (int x = 0; x < 3; x += 1)
			{
				helipad.getCell(y, x).setZombie(true);
			}
		}
		for (int y = 0; y < 3; y += 1)
		{
			for (int x = 0; x < 3; x += 1)
			{
				player.setZombieCombatRoll(6);
				player.fightZombie(helipad.getCell(y, x));
			}
		}
	}
}