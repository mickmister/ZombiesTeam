package tests;

import static org.junit.Assert.*;

import java.awt.*;

import main.*;
import main.GameHandler.GameState;
import main.MapTile.Shape;
import main.TileCell.CellType;

import org.junit.*;

/**
 * Tests the functionality of creating and rotating different map tile types.
 * 
 * @author watersdr, kochelmj. Created Mar 26, 2014.
 */
public class MapTileTest
{
	private void assertSameCells(String string, MapTile tile)
	{
		String[] numbers = string.split(" ");
		int i = 0;
		for (int y = 0; y < 3; y += 1)
		{
			for (int x = 0; x < 3; x += 1)
			{
				assertEquals(convertNumToType(numbers[i]), tile.getCell(y, x).toString());
				i += 1;
			}
		}
	}
	
	private String convertNumToType(String num)
	{
		if (num.equals("0"))
		{
			return CellType.grass.toString();
		}
		else
		{
			return CellType.road.toString();
		}
	}
	
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
	public void testEmptyTile()
	{
		MapTile tile = new MapTile(Shape.empty, null);
		assertEquals(Shape.empty, tile.getShape());
		
		assertSameCells("0 0 0 0 0 0 0 0 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 0 0 0 0 0 0 0 0", tile);
	}
	
	@Test
	public void testQuadTile()
	{
		MapTile tile = new MapTile(Shape.quad, null);
		assertEquals(Shape.quad, tile.getShape());
		
		assertSameCells("0 1 0 1 1 1 0 1 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 1 0 1 1 1 0 1 0", tile);
	}
	
	@Test
	public void testLTile()
	{
		MapTile tile = new MapTile(Shape.L, null);
		assertEquals(Shape.L, tile.getShape());
		
		assertSameCells("0 0 0 0 1 1 0 1 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 1 0 0 1 1 0 0 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 1 0 1 1 0 0 0 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 0 0 1 1 0 0 1 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 0 0 0 1 1 0 1 0", tile);
	}
	
	@Test
	public void testTTile()
	{
		MapTile tile = new MapTile(Shape.T, null);
		assertEquals(Shape.T, tile.getShape());
		
		assertSameCells("0 0 0 1 1 1 0 1 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 1 0 0 1 1 0 1 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 1 0 1 1 1 0 0 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 1 0 1 1 0 0 1 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 0 0 1 1 1 0 1 0", tile);
	}
	
	@Test
	public void testStraightTile()
	{
		MapTile tile = new MapTile(Shape.straight, null);
		assertEquals(Shape.straight, tile.getShape());
		
		assertSameCells("0 0 0 1 1 1 0 0 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 1 0 0 1 0 0 1 0", tile);
		
		tile.rotateTile();
		assertSameCells("0 0 0 1 1 1 0 0 0", tile);
	}
	
	@Test
	public void testGetAndTempBulletPos()
	{
		MapTile tile = new MapTile(Shape.straight, null);
		
		// makes sure you can't set position to invalid place
		tile.setTempBulletPos(new Point(-1, 3));
		assertNull(tile.getTempBulletPos());
		
		tile.setTempBulletPos(new Point(1, 1));
		assertEquals(new Point(1, 1), tile.getTempBulletPos());
	}
	
	@Test
	public void testGetAndSetTempLifePos()
	{
		MapTile tile = new MapTile(Shape.straight, null);
		
		// makes sure you can't set position to invalid place
		tile.setTempLifePos(new Point(-1, 3));
		assertNull(tile.getTempLifePos());
		
		tile.setTempLifePos(new Point(1, 1));
		assertEquals(new Point(1, 1), tile.getTempLifePos());
	}
	
	@Test
	public void bulletTokenPlacementtest()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		MapTile building = new MapTile(Shape.special, "2 3 2 1 1 1 0 0 0" + " " + "0 0 2");
		// special tile that has 0 zombies 0 lifetokens and 2 bullet tokens
		Map map = game.getMap();
		map.setTempTile(building);
		map.setTempPos(new Point(6, 5));
		map.placeTempTile();
		game.nextGameState();
		
		// placing a bullet at the center of tile
		building.setTempBulletPos(new Point(1, 1));
		building.placeTempBullet();
		assertTrue(building.getCell(1, 1).hasBulletToken());
		
		// placing a bullet at top left of tile
		building.setTempBulletPos(new Point(0, 0));
		building.placeTempBullet();
		assertTrue(building.getCell(0, 0).hasBulletToken());
	}
	
	@Test
	public void lifeTokenPlacementTest()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		MapTile building = new MapTile(Shape.special, "2 3 2 1 1 1 0 0 0" + " " + "0 2 0");
		// special tile that has 0 zombies 2 lifetokens and 0 bullet tokens
		Map map = game.getMap();
		map.setTempTile(building);
		map.setTempPos(new Point(6, 5));
		map.placeTempTile();
		game.nextGameState();
		
		// placing a bullet at the center of tile
		building.setTempLifePos(new Point(1, 1));
		building.placeTempLife();
		assertTrue(building.getCell(1, 1).hasLifeToken());
		
		// placing a bullet at top left of tile
		building.setTempLifePos(new Point(0, 0));
		building.placeTempLife();
		assertTrue(building.getCell(0, 0).hasLifeToken());
	}
}