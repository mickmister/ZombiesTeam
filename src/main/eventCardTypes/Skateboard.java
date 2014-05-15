package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.MapTileDeck.SpecialNames;

public class Skateboard extends PlayUntilRevoked
{
	
	public Skateboard()
	{
		super(PossibleTarget.Self, "Skateboard",
				"Play this card in front of you when you are in the Skate Shop. You may add +2 to all of your movement rolls.",
				SpecialNames.SkateShop);
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, "You added 2 to your movement roll with your Skateboard! Radical!", getName(),
				JOptionPane.INFORMATION_MESSAGE);
		return num + 2;
	}
	
}
