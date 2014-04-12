package tests;

import java.awt.*;

import main.*;
import main.MapTile.Shape;

import org.junit.*;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author watersdr. Created Mar 31, 2014.
 */
public class TilePlacementTest
{
	// TESTS FOR L
	@Test(expected = IllegalStateException.class)
	public void testPlaceTempLTile()
	{
		Map testMap = new Map();
		MapTile testL = new MapTile(Shape.L, null);
		testMap.setTempTile(testL);
		testMap.setTempPos(new Point(6, 5));
		
		testMap.placeTempTile();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testPlaceTempLTileOnTopOfValid()
	{
		// Placing a valid L tile on top of the town square
		Map testMap = new Map();
		MapTile testL = new MapTile(Shape.L, null);
		testMap.setTempTile(testL);
		testMap.setTempPos(new Point(5, 4));
		testMap.placeTempTile();
		
		// Testing to make sure we can't place a tile on top of an already
		// placed tile
		MapTile testL2 = new MapTile(Shape.L, null);
		testMap.setTempTile(testL2);
		testMap.setTempPos(new Point(5, 4));
		testMap.placeTempTile();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testPlaceTempLTileOnTopOfTownSquare()
	{
		// Placing a tile on top of town square should throw exception
		Map testMap = new Map();
		MapTile testL = new MapTile(Shape.L, null);
		testMap.setTempTile(testL);
		testMap.setTempPos(new Point(5, 5));
		testMap.placeTempTile();
	}
	
	// TESTS FOR STRAIGHT
	@Test(expected = IllegalStateException.class)
	public void testPlaceTempStraightTile()
	{
		Map testMap = new Map();
		MapTile testStraight = new MapTile(Shape.straight, null);
		testMap.setTempTile(testStraight);
		testMap.setTempPos(new Point(5, 6));
		
		testMap.placeTempTile();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testPlaceTempStraightTileOnTopOfValid()
	{
		// Placing a valid straight tile to the right of the town square
		Map testMap = new Map();
		MapTile testStraight = new MapTile(Shape.straight, null);
		testMap.setTempTile(testStraight);
		testMap.setTempPos(new Point(6, 5));
		testMap.placeTempTile();
		
		// Testing to make sure we can't place a tile on top of an already
		// placed tile
		MapTile testStraight2 = new MapTile(Shape.L, null);
		testMap.setTempTile(testStraight2);
		testMap.setTempPos(new Point(6, 5));
		testMap.placeTempTile();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testPlaceTempStraightTileOnTopOfTownSquare()
	{
		// Placing a tile on top of town square should throw exception
		Map testMap = new Map();
		MapTile testStraight = new MapTile(Shape.straight, null);
		testMap.setTempTile(testStraight);
		testMap.setTempPos(new Point(5, 5));
		testMap.placeTempTile();
	}
	
	// TESTS FOR TETRIS
	@Test(expected = IllegalStateException.class)
	public void testPlaceTempTetrisTile()
	{
		Map testMap = new Map();
		MapTile testTetr = new MapTile(Shape.T, null);
		testMap.setTempTile(testTetr);
		testMap.setTempPos(new Point(5, 6));
		
		testMap.placeTempTile();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testPlaceTempTetrisTileOnTopOfValid()
	{
		// Placing a valid straight tile to the right of the town square
		Map testMap = new Map();
		MapTile testTetr = new MapTile(Shape.T, null);
		testMap.setTempTile(testTetr);
		testMap.setTempPos(new Point(6, 5));
		testMap.placeTempTile();
		
		// Testing to make sure we can't place a tile on top of an already
		// placed tile
		MapTile testTetr2 = new MapTile(Shape.T, null);
		testMap.setTempTile(testTetr2);
		testMap.setTempPos(new Point(6, 5));
		testMap.placeTempTile();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testPlaceTempTetrisTileOnTopOfTownSquare()
	{
		// Placing a tile on top of town square should throw exception
		Map testMap = new Map();
		MapTile testTetr = new MapTile(Shape.T, null);
		testMap.setTempTile(testTetr);
		testMap.setTempPos(new Point(5, 5));
		testMap.placeTempTile();
	}
	
}
