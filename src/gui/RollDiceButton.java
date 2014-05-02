package gui;

import java.awt.event.*;

import javax.swing.*;

import main.*;
import main.GameHandler.GameState;

public class RollDiceButton extends JButton implements ActionListener, DataListener
{
	public RollDiceButton()
	{
		setText("Roll Dice");
		addActionListener(this);
		GameHandler.instance.addDataListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		rollDiceClicked();
	}
	
	public void rollDiceClicked()
	{
		int result = RollDice.rollDice();
		if (GameHandler.instance.getCurrentState().equals(GameState.playerMovementDieRoll))
		{
			DialogHandler.showMessage(getTopLevelAncestor(), "Your player movement roll was a " + result + "!", "Roll Dice",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieMovementDieRoll))
		{
			DialogHandler.showMessage(getTopLevelAncestor(), "Your zombie movement roll was a " + result + "!", "Roll Dice",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieCombat))
		{
			DialogHandler.showMessage(getTopLevelAncestor(), "Your combat roll was a " + result + "!", "Roll Dice", JOptionPane.INFORMATION_MESSAGE);
		}
		RollDice.rollAction(result);
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		boolean canEnable = GameHandler.instance.getGuiStateData().rollDiceButtonEnabled;
		boolean myTurn = ((Window) getTopLevelAncestor()).getPlayer().isPlayersTurn();
		setEnabled(canEnable && myTurn);
	}
}