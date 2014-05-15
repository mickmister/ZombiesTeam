package main.eventCardTypes;

import javax.swing.JOptionPane;

import gui.DialogHandler;
import main.MapTileDeck.SpecialNames;

public class MolotovCocktail extends CustomUseDiscardable
{

	public MolotovCocktail() {
		super(PossibleTarget.Self, "Molotov Cocktail", "Play this card in front of you when you are in the Gas Station. Discard this item to add +2 to" +
				" all combat rolls against all zombies in your current building for 1 turn.", SpecialNames.GasStation);
	}

	@Override
	public int behavior(int num) {
		DialogHandler.showMessage(null, "Your Molotov Cocktail increased your combat roll by 2!", getName(), JOptionPane.INFORMATION_MESSAGE);
		return num + 2;
	}

}
