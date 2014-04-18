package tests;

import static org.junit.Assert.*;

import java.awt.*;

import main.*;
import main.MapTile.Shape;

import org.junit.*;

public class MapTest
{
	@Test
	public void testGetAndSetTempTile()
	{
		Map map = new Map();
		MapTile tile = new MapTile(Shape.quad, null);
		assertEquals(null, map.getTempTile());
		map.setTempTile(tile);
		assertEquals(tile, map.getTempTile());
	}
	
	@Test
	public void testAddTempTile()
	{
		Map map = new Map();
		MapTile tile = new MapTile(Shape.quad, null);
		map.setTempPos(new Point(1, 1));
		map.addTempTile(tile);
		assertEquals(tile, map.getTempTile());
		assertEquals(new Point(5, 5), map.getTempPos());
	}
	
	@Test
	public void testGetAndSetTempPos()
	{
		Map map = new Map();
		assertEquals(new Point(5, 5), map.getTempPos());
		map.setTempPos(new Point(2, 2));
		assertEquals(new Point(2, 2), map.getTempPos());
		map.setTempPos(new Point(20, 20));
		assertEquals(new Point(10, 10), map.getTempPos());
		map.setTempPos(new Point(-2, -2));
		assertEquals(new Point(0, 0), map.getTempPos());
	}
	
	@Test
	public void testPlaceTempTile()
	{
		Map map = new Map();
		MapTile tile = new MapTile(Shape.quad, null);
		map.addTempTile(tile);
		map.setTempPos(new Point(4, 5));
		map.placeTempTile();
		
		assertEquals(tile, map.getMapTile(5, 4));
		assertEquals(null, map.getTempTile());
		assertEquals(tile, map.getTempZombieTile());
	}
	
	@Test
	public void testGetCurrentMessage()
	{
		new GameHandler(2);
		Map map = GameHandler.instance.getMap();
		Player player = GameHandler.instance.getPlayer(1);
		assertEquals("Not your turn", map.getCurrentMessage(player));
		
		player = GameHandler.instance.getPlayer(0);
		assertEquals("Draw and place a map tile", map.getCurrentMessage(player));
		
		MapTile specialTile = new MapTile(Shape.special, "1 1 1 1 1 1 1 1 1" + " " + "6 2 3");
		map.addTempTile(specialTile);
		map.setTempPos(new Point(4, 5));
		map.placeTempTile();
		GameHandler.instance.nextGameState();
		assertEquals("Place 6 more zombie(s)", map.getCurrentMessage(player));
		GameHandler.instance.nextGameState();
		assertEquals("Place 3 more bullet token(s)", map.getCurrentMessage(player));
		GameHandler.instance.nextGameState();
		assertEquals("Place 2 more life token(s)", map.getCurrentMessage(player));
		GameHandler.instance.nextGameState();
		assertEquals("Roll the dice to move", map.getCurrentMessage(player));
		player.setMovesRemaining(5);
		GameHandler.instance.nextGameState();
		assertEquals("5 move(s) remaining", map.getCurrentMessage(player));
		map.getMapTile(5, 5).getCell(1, 1).setZombie(true);
		GameHandler.instance.gotoCombatOrMoveState();
		assertEquals("Roll the dice to fight", map.getCurrentMessage(player));
		player.setMovesRemaining(0);
		GameHandler.instance.nextGameState();
		assertEquals("Roll the dice to move zombies", map.getCurrentMessage(player));
	}
}