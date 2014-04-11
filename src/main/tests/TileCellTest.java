package main.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.Player;
import main.TileCell;

import org.junit.Test;

public class TileCellTest
{
	@Test
	public void testConstructorForNonAccesibleCell()
	{
		TileCell cell = new TileCell(null, false, false, false);
		assertNotNull(cell);
	}
	
	@Test
	public void testSetLifeToken()
	{
		TileCell cell = new TileCell(null, false, false, false);
		cell.setLifeToken(true);
		assertTrue(cell.hasLifeToken());
		
		cell.setLifeToken(false);
		assertFalse(cell.hasLifeToken());
	}
	
	@Test
	public void testSetBulletToken()
	{
		TileCell cell = new TileCell(null, false, false, false);
		cell.setBulletToken(true);
		assertTrue(cell.hasBulletToken());
		
		cell.setBulletToken(false);
		assertFalse(cell.hasBulletToken());
	}
	
	@Test
	public void testSetZombie()
	{
		TileCell cell = new TileCell(null, false, false, false);
		cell.setZombie(true);
		assertTrue(cell.hasZombie());
		
		cell.setZombie(false);
		assertFalse(cell.hasZombie());
	}
}