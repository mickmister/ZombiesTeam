package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.lang.reflect.Method;

import main.GameHandler;
import main.Player;
import main.TileCell;

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
		try
		{
			Player test = new Player(0);
			Method method = test.getClass().getDeclaredMethod("loseLifeToken");
			method.setAccessible(true);
			assertEquals(true, method.invoke(test));
			assertEquals(2, test.getLifeTokens());
			assertEquals(true, method.invoke(test));
			assertEquals(1, test.getLifeTokens());
			assertEquals(true, method.invoke(test));
			assertEquals(0, test.getLifeTokens());
			assertEquals(false, method.invoke(test));
			assertEquals(3, test.getLifeTokens());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testDifferentTileMove()
	{
		try
		{
			Player player = new Player(0);
			Method method = Player.class.getDeclaredMethod("checkDifferentTileMove", TileCell.class, TileCell.class);
			method.setAccessible(true);
			TileCell road = new TileCell(true, false, false);
			TileCell building = new TileCell(true, true, false);
			TileCell door = new TileCell(true, true, true);
			
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
			TileCell grass = new TileCell(false, false, false);
			TileCell road = new TileCell(true, false, false);
			TileCell building = new TileCell(true, true, false);
			TileCell door = new TileCell(true, true, true);
			
			assertEquals(false, method.invoke(player, grass, grass));
			//assertEquals(false, method.invoke(player, grass, road));
			//assertEquals(false, method.invoke(player, grass, building));
			//assertEquals(false, method.invoke(player, grass, door));
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
}