package main;
import static org.junit.Assert.*;

import org.junit.Test;


/**
 * TODO Put here a description of what this class does.
 *
 * @author watersdr.
 *         Created Mar 26, 2014.
 */
public class MapTileTest {

	@Test
	public void testBlankTileCreation() {
		TileCell[][] grid = MapTile.createBlankGrid();
		assertFalse(grid[0][0] == null);
	}
	
	@Test
	public void testCreateQuad(){
		MapTile mapTile = new MapTile("quad", null);
		String quadFormation = "false true false\ntrue true true\nfalse true false\n";
		
		assertEquals(mapTile.toString(), quadFormation);		
	}
	
	@Test
	public void testCreateAndRotateL(){
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

}
