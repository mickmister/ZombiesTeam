package main.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import main.MapTile;
import main.MapTile.Shape;
import main.TileCell;

import org.junit.Test;

/**
 * Tests the functionality of creating and rotating different map tile types.
 * 
 * @author watersdr, kochelmj. Created Mar 26, 2014.
 */
public class MapTileTest
{
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