package tests;

import static org.junit.Assert.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import main.*;
import main.GameHandler.GameState;
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
		
		map.addTempTile(tile);
		map.setTempPos(new Point(1, 1));
		try
		{
			map.placeTempTile();
			fail("Did not throw IllegalStateException");
		}
		catch (IllegalStateException e)
		{
		}
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
		GameHandler.instance.nextGameState();
		player.setMovesRemaining(5);
		assertEquals("5 zombie(s) to move", map.getCurrentMessage(player));
	}
	
	@Test
	public void testPlaceMovingZombie()
	{
		new GameHandler(2);
		Map map = GameHandler.instance.getMap();
		JButton view = new JButton();
		assertEquals(-1, map.getZombieMovementIndex());
		
		map.setZombieMovementIndex(10);
		assertEquals(10, map.getZombieMovementIndex());
		
		map.placeMovingZombie(new KeyEvent(view, 0, 0, 0, KeyEvent.VK_DOWN, '\0'));
		assertEquals(10, map.getZombieMovementIndex());
		
		map.selectNextZombie();
		assertEquals(-1, map.getZombieMovementIndex());
		
		map.getMapTile(5, 5).getCell(0, 1).setZombie(true);
		map.selectNextZombie();
		int previous = map.getZombieMovementIndex();
		map.placeMovingZombie(new KeyEvent(view, 0, 0, 0, KeyEvent.VK_SPACE, '\0'));
		assertEquals(previous, map.getZombieMovementIndex());
		map.placeMovingZombie(new KeyEvent(view, 0, 0, 0, KeyEvent.VK_LEFT, '\0'));
		assertEquals(previous, map.getZombieMovementIndex());
		map.placeMovingZombie(new KeyEvent(view, 0, 0, 0, KeyEvent.VK_RIGHT, '\0'));
		assertEquals(previous, map.getZombieMovementIndex());
		map.placeMovingZombie(new KeyEvent(view, 0, 0, 0, KeyEvent.VK_UP, '\0'));
		assertEquals(previous, map.getZombieMovementIndex());
		
		map.getMapTile(5, 5).getCell(1, 1).setZombie(true);
		map.placeMovingZombie(new KeyEvent(view, 0, 0, 0, KeyEvent.VK_DOWN, '\0'));
		assertEquals(previous, map.getZombieMovementIndex());
		
		map.getMapTile(5, 5).getCell(1, 1).setZombie(false);
		map.placeMovingZombie(new KeyEvent(view, 0, 0, 0, KeyEvent.VK_DOWN, '\0'));
		assertEquals(previous + 30, map.getZombieMovementIndex());
		map.placeMovingZombie(new KeyEvent(view, 0, 0, 0, KeyEvent.VK_DOWN, '\0'));
		assertEquals(previous + 30, map.getZombieMovementIndex());
		
		map.getMapTile(5, 5).getCell(1, 1).setZombieMoved(false);
		GameHandler.instance.getPlayer(0).setMovesRemaining(1);
		map.placeMovingZombie(new KeyEvent(view, 0, 0, 0, KeyEvent.VK_DOWN, '\0'));
		assertEquals(previous + 60, map.getZombieMovementIndex());
		assertEquals(GameState.zombiePlacement, GameHandler.instance.getCurrentState());
	}
	
	@Test
	public void testGetHelipad()
	{
		new GameHandler(2);
		assertEquals(null, GameHandler.instance.getMap().getHelipad());
		
		int count = 4 * 4 + 12;
		for (int i = 0; i < count; i += 1)
		{
			GameHandler.instance.getTileDeck().getNextCard();
		}
		MapTile helipad = GameHandler.instance.getTileDeck().getNextCard();
		GameHandler.instance.getMap().addTempTile(helipad);
		GameHandler.instance.getMap().setTempPos(new Point(5, 6));
		GameHandler.instance.getMap().placeTempTile();
		MapTile test = GameHandler.instance.getMap().getHelipad();
		assertEquals(helipad, test);
	}
}