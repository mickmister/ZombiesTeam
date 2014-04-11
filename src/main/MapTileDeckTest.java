package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import main.MapTile.Shape;

import org.junit.Test;

public class MapTileDeckTest
{
	@Test
	public void testConstructor()
	{
		MapTileDeck test = new MapTileDeck();
		assertNotNull(test);
	}
	
	@Test
	public void testNumberOfCards()
	{
		MapTileDeck test = new MapTileDeck();
		int count = 4 * 4 + 12 + 1;
		for (int i = 0; i < count; i += 1)
		{
			assertNotNull(test.getNextCard());
		}
	}
	
	@Test
	public void testHelipadIsLastCard()
	{
		MapTileDeck test = new MapTileDeck();
		int count = 4 * 4 + 12;
		for (int i = 0; i < count; i += 1)
		{
			test.getNextCard();
		}
		MapTile card = test.getNextCard();
		assertEquals(Shape.special, card.getShape());
		assertEquals(9, card.getZombiesToPlace());
	}
}