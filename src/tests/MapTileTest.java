package tests;

import static org.junit.Assert.*;

import java.awt.*;

import main.*;
import main.GameHandler.GameState;
import main.MapTile.Shape;

import org.junit.*;

/**
 * Tests the functionality of creating and rotating different map tile types.
 * 
 * @author watersdr, kochelmj. Created Mar 26, 2014.
 */
public class MapTileTest
{
	@Test
	public void testTempZombiePos()
	{
		MapTile test = new MapTile(Shape.quad, null);
		
		assertEquals(null, test.getTempZombiePos());
		test.setTempZombiePos(new Point(1, 2));
		assertEquals(new Point(1, 2), test.getTempZombiePos());
		
		test.setTempZombiePos(new Point(0, 3));
		test.setTempZombiePos(new Point(3, 0));
		test.setTempZombiePos(new Point(3, 3));
		test.setTempZombiePos(new Point(0, -1));
		test.setTempZombiePos(new Point(-1, 0));
		test.setTempZombiePos(new Point(-1, -1));
		test.setTempZombiePos(new Point(0, 0));
		assertEquals(new Point(1, 2), test.getTempZombiePos());
	}
	
	@Test
	public void testPlaceTempZombie()
	{
		new GameHandler(2);
		MapTile test = new MapTile(Shape.quad, null);
		// MapTile will begin with 4 zombies to place because it is a Quad shape.
		assertEquals(4, test.getZombiesToPlace());
		
		test.setTempZombiePos(new Point(1, 0));
		test.placeTempZombie();
		assertEquals(3, test.getZombiesToPlace());
		
		assertEquals(new Point(1, 1), test.getTempZombiePos());
		
		test.setTempZombiePos(new Point(1, 0));
		test.placeTempZombie();
		assertEquals(3, test.getZombiesToPlace());
		
		test.setTempZombiePos(new Point(0, 1));
		test.placeTempZombie();
		test.setTempZombiePos(new Point(0, 2));
		test.placeTempZombie();
		test.setTempZombiePos(new Point(1, 2));
		test.placeTempZombie();
		// Since there are no more zombies to place, the tempZombiePos should be set to null and it
		// should move to the next game state (mapTilePlacement -> zombiePlacement, in this case).
		assertEquals(0, test.getZombiesToPlace());
		assertEquals(null, test.getTempZombiePos());
		assertEquals(GameState.zombiePlacement, GameHandler.instance.getCurrentState());
	}
	
	@Test
	public void testBlankTileCreation()
	{
		TileCell[][] grid = new MapTile(Shape.empty, null).createBlankGrid();
		
		for (TileCell[] element1 : grid)
		{
			for (TileCell element2 : element1)
			{
				assertFalse(element2.isAccessible());
			}
		}
		
	}
	
	@Test
	public void testCreateQuad()
	{
		MapTile mapTile = new MapTile(Shape.quad, null);
		String quadFormation = "false true false\ntrue true true\nfalse true false\n";
		
		assertEquals(quadFormation, mapTile.toString());
	}
	
	@Test
	public void testCreateAndRotateL()
	{
		MapTile mapTile = new MapTile(Shape.L, null);
		String originalLFormation = "false false false\nfalse true true\nfalse true false\n";
		String firstRotateLFormation = "false true false\nfalse true true\nfalse false false\n";
		String secondRotateLFormation = "false true false\ntrue true false\nfalse false false\n";
		String thirdRotateLFormation = "false false false\ntrue true false\nfalse true false\n";
		String fourthRotateLFormation = originalLFormation;
		
		assertEquals(originalLFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(firstRotateLFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(secondRotateLFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(thirdRotateLFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(fourthRotateLFormation, mapTile.toString());
		
	}
	
	@Test
	public void testCreateandRotateTetris()
	{
		MapTile mapTile = new MapTile(Shape.T, null);
		String originalTetrisFormation = "false false false\ntrue true true\nfalse true false\n";
		String firstRotateTetrisFormation = "false true false\nfalse true true\nfalse true false\n";
		String secondRotateTetrisFormation = "false true false\ntrue true true\nfalse false false\n";
		String thirdRotateTetrisFormation = "false true false\ntrue true false\nfalse true false\n";
		String fourthRotateTetrisFormation = originalTetrisFormation;
		
		assertEquals(originalTetrisFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(firstRotateTetrisFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(secondRotateTetrisFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(thirdRotateTetrisFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(fourthRotateTetrisFormation, mapTile.toString());
	}
	
	@Test
	public void testCreateAndRotateStraight()
	{
		MapTile mapTile = new MapTile(Shape.straight, null);
		String originalStraightFormation = "false false false\ntrue true true\nfalse false false\n";
		String firstRotateStraightFormation = "false true false\nfalse true false\nfalse true false\n";
		String secondRotateStraightFormation = originalStraightFormation;
		String thirdRotateStraightFormation = firstRotateStraightFormation;
		String fourthRotateStraightFormation = originalStraightFormation;
		
		assertEquals(originalStraightFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(firstRotateStraightFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(secondRotateStraightFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(thirdRotateStraightFormation, mapTile.toString());
		
		mapTile.rotateTile();
		assertEquals(fourthRotateStraightFormation, mapTile.toString());
	}
}