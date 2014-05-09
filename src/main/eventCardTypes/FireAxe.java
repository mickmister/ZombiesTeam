package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.MapTileDeck.SpecialNames;

public class FireAxe extends PlayUntilRevoked {

	public FireAxe() {
		super(PossibleTarget.Self, "Fire Axe", "Play this card in front of you when you are in the Fire Station. Add +1 to all subsequent combat rolls", 
				SpecialNames.FireStation);
	}

	@Override
	public int behavior(int num) 
	{
		DialogHandler.showMessage(null, "Your combat roll got +1'd by your Fire Axe!", this.getName(), JOptionPane.INFORMATION_MESSAGE);
		return num + 1;
	}
	

}
