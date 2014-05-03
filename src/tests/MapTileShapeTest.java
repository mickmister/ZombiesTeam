package tests;

import static org.junit.Assert.*;
import main.*;
import main.MapTile.Shape;
import main.TileCell.CellType;

import org.junit.*;

public class MapTileShapeTest
{
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
	public void testSpecialTile()
	{
		MapTile tile = new MapTile(Shape.special, "4 0 0 1 1 1 0 0 0 5 6 7");
	}
	
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
}