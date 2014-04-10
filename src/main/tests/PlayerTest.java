package main.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;

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
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddsSelfToMap()
	{
		try
		{
			new GameHandler(4);
			Player test = new Player(2);
			TileCell cell = GameHandler.instance.getMap().getMapTile(5, 5).getCell(1, 1);
			Field field = cell.getClass().getDeclaredField("playersOccupying");
			field.setAccessible(true);
			ArrayList<Player> players = (ArrayList<Player>) field.get(cell);
			assertEquals(true, players.contains(test));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Exception occurred when accessing private ArrayList for check: " + e.getMessage());
		}
	}
}