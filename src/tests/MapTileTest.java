package tests;

import static org.junit.Assert.*;

import java.awt.*;

import main.*;
import main.GameHandler.GameState;
import main.MapTile.Shape;
import main.MapTileDeck.SpecialNames;

import org.junit.*;

/**
 * Tests the functionality of creating and rotating different map tile types.
 * 
 * @author Jacob Ryan, Donnie Waters, and Coach. Created Mar 26, 2014.
 */
public class MapTileTest
{
	@Test
	public void testTempZombiePos()
	{
		MapTile test = new MapTile(Shape.quad);
		
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
	public void testTempBulletPos()
	{
		MapTile test = new MapTile(Shape.quad);
		
		assertEquals(null, test.getTempBulletPos());
		test.setTempBulletPos(new Point(1, 2));
		assertEquals(new Point(1, 2), test.getTempBulletPos());
		
		test.setTempBulletPos(new Point(0, 3));
		test.setTempBulletPos(new Point(3, 0));
		test.setTempBulletPos(new Point(3, 3));
		test.setTempBulletPos(new Point(0, -1));
		test.setTempBulletPos(new Point(-1, 0));
		test.setTempBulletPos(new Point(-1, -1));
		test.setTempBulletPos(new Point(0, 0));
		assertEquals(new Point(1, 2), test.getTempBulletPos());
	}
	
	@Test
	public void testTempLifePos()
	{
		MapTile test = new MapTile(Shape.quad);
		
		assertEquals(null, test.getTempLifePos());
		test.setTempLifePos(new Point(1, 2));
		assertEquals(new Point(1, 2), test.getTempLifePos());
		
		test.setTempLifePos(new Point(0, 3));
		test.setTempLifePos(new Point(3, 0));
		test.setTempLifePos(new Point(3, 3));
		test.setTempLifePos(new Point(0, -1));
		test.setTempLifePos(new Point(-1, 0));
		test.setTempLifePos(new Point(-1, -1));
		test.setTempLifePos(new Point(0, 0));
		assertEquals(new Point(1, 2), test.getTempLifePos());
	}
	
	@Test
	public void testPlaceTempZombie()
	{
		new GameHandler(2);
		MapTile test = new MapTile(Shape.quad);
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
	public void testPlaceTempBullet()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		MapTile building = new MapTile(Shape.special, SpecialNames.ArmySurplus, "2 3 2 1 1 1 0 0 0" + " " + "0 0 2");
		// Special tile that has 0 zombies, 0 lifetokens, and 3 bullet tokens.
		Map map = game.getMap();
		map.setTempTile(building);
		map.setTempPos(new Point(6, 5));
		map.placeTempTile();
		game.nextGameState();
		
		// placing a bullet at the center of tile
		building.setTempBulletPos(new Point(1, 1));
		building.placeTempBullet();
		assertEquals(true, building.getCell(1, 1).hasBulletToken());
		assertEquals(1, building.getBulletsToPlace());
		
		// Cannot place bullet on top of another bullet token.
		building.setTempBulletPos(new Point(1, 1));
		building.placeTempBullet();
		assertEquals(1, building.getBulletsToPlace());
		
		// placing a bullet at top left of tile
		building.setTempBulletPos(new Point(0, 0));
		building.placeTempBullet();
		assertEquals(true, building.getCell(0, 0).hasBulletToken());
		assertEquals(0, building.getBulletsToPlace());
	}
	
	@Test
	public void testPlaceTempLife()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		MapTile building = new MapTile(Shape.special, SpecialNames.ArmySurplus, "2 3 2 1 1 1 0 0 0" + " " + "0 2 0");
		// Special tile that has 0 zombies, 3 lifetokens, and 0 bullet tokens.
		Map map = game.getMap();
		map.setTempTile(building);
		map.setTempPos(new Point(6, 5));
		map.placeTempTile();
		game.nextGameState();
		
		// placing a bullet at the center of tile
		building.setTempLifePos(new Point(1, 1));
		building.placeTempLife();
		assertEquals(true, building.getCell(1, 1).hasLifeToken());
		assertEquals(1, building.getLifeToPlace());
		
		// Cannot place life on top of another life token.
		building.setTempLifePos(new Point(1, 1));
		building.placeTempLife();
		assertEquals(1, building.getLifeToPlace());
		
		// placing a bullet at top left of tile
		building.setTempLifePos(new Point(0, 0));
		building.placeTempLife();
		assertEquals(true, building.getCell(0, 0).hasLifeToken());
		assertEquals(0, building.getLifeToPlace());
	}
	
	@Test
	public void testToString()
	{
		MapTile tile = new MapTile(Shape.quad);
		String quadString = "MapTile quad shape:\n" + "[ grass\troad\tgrass\t]\n" + "[ road\troad\troad\t]\n" + "[ grass\troad\tgrass\t]\n";
		assertEquals(quadString, tile.toString());
	}
}