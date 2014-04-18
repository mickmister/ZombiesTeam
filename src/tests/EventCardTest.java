package tests;

import static org.junit.Assert.assertEquals;
import main.EventCard.PossibleTarget;
import main.GameHandler;
import main.Player;
import main.eventCardTypes.AdrenalineRush;
import main.eventCardTypes.Shotgun;

import org.junit.Test;

public class EventCardTest {

	@Test
	public void testAdrenalineRushMovementBehavior() {
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		AdrenalineRush card = new AdrenalineRush();
		int base1 = 5;
		int base2 = 3;
		int expected1 = 10;
		int expected2 = 6;
		int result;
		game.nextGameState();
		game.nextGameState();	//now in player movement die roll
		result = card.behavior(base1);
		assertEquals(result, expected1);
		result = card.behavior(base2);
		assertEquals(result, expected2);
	}
	
	@Test
	public void testAdrenalineRushCombatBehavior() {
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		AdrenalineRush card = new AdrenalineRush();
		int base1 = 5;
		int base2 = 3;
		int expected1 = 7;
		int expected2 = 5;
		int result;
		game.nextGameState();
		game.nextGameState();
		game.getMap().getMapTile(5, 5).getCell(1, 1).setZombie(true);
		game.nextGameState();	//now in zombie combat
		result = card.behavior(base1);
		assertEquals(result, expected1);
		result = card.behavior(base2);
		assertEquals(result, expected2);
	}
	
	@Test
	public void testAdrenalineRushIdentifiers()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(0);
		AdrenalineRush card = new AdrenalineRush();
		card.setTargetPlayer(player);
		assertEquals(card.getName(), "Adrenaline Rush");
		assertEquals(card.getDescription(), "You can move a lot now!");
		assertEquals(card.getPossibleTarget(), PossibleTarget.Self);
		assertEquals(card.getTargetPlayer(), player);
	}
	
	@Test
	public void testAdrenalineRushAction()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(0);
		AdrenalineRush card = new AdrenalineRush();
		int base1 = 5;
		int expected1 = 5;
		int expected2 = 10;
		int result;
		card.setTargetPlayer(player);
		result = card.action(base1);
		assertEquals(result, expected1);
		game.nextGameState();
		game.nextGameState();
		result = card.action(base1);
		assertEquals(result, expected2);
		
	}
	
	@Test
	public void testShotgunBehavior()
	{
		new GameHandler(2);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(0);
		Shotgun card = new Shotgun();
		int base1 = 4;
		int base2 = 5;
		int result;
		
	}

}
