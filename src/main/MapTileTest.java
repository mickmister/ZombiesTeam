package main;
import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Tests the functionality of creating and rotating different map tile types.
 *
 * @author watersdr, kochelmj.
 *         Created Mar 26, 2014.
 */
public class MapTileTest {

	@Test
	public void testBlankTileCreation() 
	{
		TileCell[][] grid = MapTile.createBlankGrid();
		
		for(int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				assertFalse(grid[i][j].getAcessible());
			}
		}
		
	}
	
	@Test
	public void testCreateQuad()
	{
		MapTile mapTile = new MapTile("quad", null);
		String quadFormation = "false true false\ntrue true true\nfalse true false\n";
		
		assertEquals(mapTile.toString(), quadFormation);		
	}
	
	@Test
	public void testCreateAndRotateL()
	{
		MapTile mapTile = new MapTile("L", null);
		String originalLFormation = "false false false\nfalse true true\nfalse true false\n";
		String firstRotateLFormation = "false true false\nfalse true true\nfalse false false\n";
		String secondRotateLFormation = "false true false\ntrue true false\nfalse false false\n";
		String thirdRotateLFormation = "false false false\ntrue true false\nfalse true false\n";
		String fourthRotateLFormation = originalLFormation;
		
		assertEquals(mapTile.toString(), originalLFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), firstRotateLFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), secondRotateLFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), thirdRotateLFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), fourthRotateLFormation);
		
		
	}
	
	@Test
	public void testCreateandRotateTetris()
	{
		MapTile mapTile = new MapTile("tetris", null);
		String originalTetrisFormation = "false false false\ntrue true true\nfalse true false\n";
		String firstRotateTetrisFormation = "false true false\nfalse true true\nfalse true false\n";
		String secondRotateTetrisFormation = "false true false\ntrue true true\nfalse false false\n";
		String thirdRotateTetrisFormation = "false true false\ntrue true false\nfalse true false\n";
		String fourthRotateTetrisFormation = originalTetrisFormation;
		
		assertEquals(mapTile.toString(), originalTetrisFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), firstRotateTetrisFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), secondRotateTetrisFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), thirdRotateTetrisFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), fourthRotateTetrisFormation);
	}
	
	@Test
	public void testCreateAndRotateStraight()
	{
		MapTile mapTile = new MapTile("straight", null);
		String originalStraightFormation = "false false false\ntrue true true\nfalse false false\n";
		String firstRotateStraightFormation = "false true false\nfalse true false\nfalse true false\n";
		String secondRotateStraightFormation = originalStraightFormation;
		String thirdRotateStraightFormation = firstRotateStraightFormation;
		String fourthRotateStraightFormation = originalStraightFormation;
		
		assertEquals(mapTile.toString(), originalStraightFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), firstRotateStraightFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), secondRotateStraightFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), thirdRotateStraightFormation);
		
		mapTile.rotateTile();
		assertEquals(mapTile.toString(), fourthRotateStraightFormation);
	}
	
}
