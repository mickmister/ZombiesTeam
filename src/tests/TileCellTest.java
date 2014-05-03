package tests;

import static org.junit.Assert.*;
import main.*;
import main.TileCell.*;

import org.junit.*;

public class TileCellTest
{
	@Test
	public void testConstructor()
	{
		TileCell cell = new TileCell(CellType.grass);
		assertNotNull(cell);
	}
	
	@Test
	public void testSetLifeToken()
	{
		TileCell cell = new TileCell(CellType.grass);
		cell.setLifeToken(true);
		assertTrue(cell.hasLifeToken());
		
		cell.setLifeToken(false);
		assertFalse(cell.hasLifeToken());
	}
	
	@Test
	public void testSetBulletToken()
	{
		TileCell cell = new TileCell(CellType.grass);
		cell.setBulletToken(true);
		assertTrue(cell.hasBulletToken());
		
		cell.setBulletToken(false);
		assertFalse(cell.hasBulletToken());
	}
	
	@Test
	public void testSetZombie()
	{
		TileCell cell = new TileCell(CellType.grass);
		cell.setZombie(true);
		assertTrue(cell.hasZombie());
		
		cell.setZombie(false);
		assertFalse(cell.hasZombie());
	}
}