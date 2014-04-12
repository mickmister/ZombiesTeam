package main;

import static org.junit.Assert.*;

import java.awt.*;

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
}