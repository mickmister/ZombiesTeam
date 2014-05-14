package main.eventCardTypes;

import gui.*;

import javax.swing.*;

import main.*;
import main.GameHandler.GameState;

public class AdrenalineRush extends OneUseCard
{
	
	public AdrenalineRush()
	{
		super(PossibleTarget.Self, "Adrenaline Rush", Messages.getString("EventCard.AdrenalineRush.desc"));
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler game = GameHandler.instance;
		if (game.getCurrentState() == GameState.playerMovementDieRoll)
		{
			DialogHandler.showMessage(null, Messages.getString("EventCard.AdrenalineRush.move_mult"), getName(), JOptionPane.INFORMATION_MESSAGE);
			return num * 2;
		}
		else
		// if (game.getCurrentState() == GameState.zombieCombat)
		{
			DialogHandler.showMessage(null, Messages.getString("EventCard.AdrenalineRush.combat_add"), getName(), JOptionPane.INFORMATION_MESSAGE);
			return num + 2;
		}
	}
}