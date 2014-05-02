package main.eventCardTypes;

import gui.Dialog;

import javax.swing.*;

import main.*;
import main.GameHandler.GameState;

public class AdrenalineRush extends OneUseCard
{
	
	public AdrenalineRush()
	{
		super(PossibleTarget.Self, "Adrenaline Rush", "You can move a lot now!");
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler game = GameHandler.instance;
		if (game.getCurrentState() == GameState.playerMovementDieRoll)
		{
			Dialog.showMessage(null, "Your movement roll was multiplied by 2 by the Adrenaline Rush card!", this.getName(), JOptionPane.INFORMATION_MESSAGE);
			return num * 2;
		}
		else
		// if (game.getCurrentState() == GameState.zombieCombat)
		{
			Dialog.showMessage(null, "Your combat roll was increased by 2 by the Adrenaline Rush card!", this.getName(), JOptionPane.INFORMATION_MESSAGE);
			return num + 2;
		}
	}
}