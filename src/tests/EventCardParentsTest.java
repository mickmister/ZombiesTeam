package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import main.GameHandler;
import main.MapTile;
import main.Player;
import main.MapTile.Shape;
import main.MapTileDeck.SpecialNames;
import main.eventCardParents.EventCard;
import main.eventCardTypes.AdrenalineRush;
import main.eventCardTypes.FireAxe;

import org.junit.Test;

public class EventCardParentsTest
{
	@Test
	public void testCheckCorrectBuilding()
	{
		new GameHandler(2);
		EventCard card = new FireAxe();
		Player player = GameHandler.instance.getPlayer(0);
		assertEquals(false, card.checkCorrectBuilding(player));
		
		MapTile fireStation = new MapTile(Shape.special, SpecialNames.FireStation, "2 2 2 2 3 2 0 1 2" + " " + "6 4 2");
		GameHandler.instance.getMap().addTempTile(fireStation);
		GameHandler.instance.getMap().setTempPos(new Point(5, 4));
		GameHandler.instance.getMap().placeTempTile();
		player.teleport(new Point(5, 4), new Point(1, 2)); // Should be outside of the Fire Station.
		assertEquals(false, card.checkCorrectBuilding(player));
		
		player.teleport(new Point(5, 4), new Point(1, 1)); // Should be the door of the Fire Station.
		assertEquals(true, card.checkCorrectBuilding(player));
		player.teleport(new Point(5, 4), new Point(0, 0)); // Should be inside of the Fire Station.
		assertEquals(true, card.checkCorrectBuilding(player));
	}
	@Test
	public void testInvalidCheckCorrectBuilding()
	{
		new GameHandler(2);
		EventCard card = new AdrenalineRush();
		try
		{
			card.checkCorrectBuilding(GameHandler.instance.getPlayer(0));
			fail("Did not throw expected IllegalArgumentException!");
		}
		catch (IllegalArgumentException e)
		{
		}
	}
	
	@Test
	public void testToString()
	{
		EventCard card = new AdrenalineRush();
		assertEquals(card.getName(), card.toString());
		assertEquals("Adrenaline Rush", card.toString());
	}
}