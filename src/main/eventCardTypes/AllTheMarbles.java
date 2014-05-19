package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.MapTileDeck.SpecialNames;
import main.eventCardParents.CustomUseDiscardable;

public class AllTheMarbles extends CustomUseDiscardable
{
	public AllTheMarbles()
	{
		super(PossibleTarget.None, "All the Marbles", "Play this card in front of you when you are in the Toy Store."
				+ " Discard this item to prevent all zombies from moving until after your next turn.", SpecialNames.ToyStore, 2);
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler.instance.getPlayer(GameHandler.instance.getTurn()).setMovesRemaining(0);
		GameHandler.instance.nextGameState();
		GameHandler.instance.nextGameState();
		DialogHandler.showMessage(null, "Zombie movement has been skipped by All the Marbles!", getName(), JOptionPane.INFORMATION_MESSAGE);
		return 1;
	}
}