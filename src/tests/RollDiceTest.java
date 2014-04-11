package tests;

import static org.junit.Assert.*;
import main.DiceRoll;
import main.GameHandler;

import org.junit.Test;

public class RollDiceTest
{
	
	@Test
	public void testRollDice()
	{
		GameHandler game = new GameHandler(2);
		for (int i = 0; i < 1000000; i++)
		{
			int roll = DiceRoll.rollDice();
			if (roll > 6)
			{
				fail("Roll was greater than 6.");
			}
			if (roll < 1)
			{
				fail("Roll was less than 1.");
			}
		}
	}
	
}
