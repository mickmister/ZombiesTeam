package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.MapTileDeck.SpecialNames;
import main.eventCardParents.CustomUseDiscardable;

public class Chainsaw extends CustomUseDiscardable
{
	
	public Chainsaw()
	{
		super(PossibleTarget.Self, "Chainsaw",
				"Play this card in front of you when you are in the Lawn and Garden Center. Discard this item to gain +2"
						+ "to all combat roll for the rest of your turn.", SpecialNames.LawnAndGarden);
		
	}
	
	@Override
	public int behavior(int num)
	{
		DialogHandler.showMessage(null, "You gained +2 on your combat roll from your Chainsaw!", getName(), JOptionPane.INFORMATION_MESSAGE);
		return num + 2;
	}
	
}
