package main.eventCardTypes;

import gui.*;

import javax.swing.*;

import main.*;

public class GainTwoHealthNoMove extends OneUseCard
{
	public GainTwoHealthNoMove()
	{
		super(PossibleTarget.Self, "Gain Two Health No Move", "Instead of making a movement roll, gain 2 health");
	}
	
	@Override
	public int behavior(int num)
	{
		Player player = getTargetPlayer();
		player.setMovesRemaining(0);
		player.addLifeToken();
		player.addLifeToken();
		GameHandler.instance.nextGameState();
		DialogHandler.showMessage(null, "You played it safe and rested to gain 2 health", getName(), JOptionPane.INFORMATION_MESSAGE);
		return 1;
	}
	
}
