package main.eventCardTypes;

import javax.swing.JOptionPane;

import gui.DialogHandler;
import main.GameHandler;
import main.Player;

public class GainTwoHealthNoMove extends OneUseCard
{
	public GainTwoHealthNoMove()
	{
		super(PossibleTarget.Self, "GainTwoHealthNoMove", "Instead of making a movement roll, gain 2 health");
	}

	@Override
	public int behavior(int num)
	{
		Player player = this.getTargetPlayer();
		player.setMovesRemaining(0);
		player.addLifeToken();
		player.addLifeToken();
		GameHandler.instance.nextGameState();
		DialogHandler.showMessage(null, "You played it safe and rested to gain 2 health", this.getName(), JOptionPane.INFORMATION_MESSAGE);
		return 1;
	}

}
