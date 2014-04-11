package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.DataListener;
import main.DiceRoll;
import main.GameHandler;
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
		int result = DiceRoll.rollDice();
		if (GameHandler.instance.getCurrentState().equals(GameState.playerMovementDieRoll))
		{
			JOptionPane.showMessageDialog(getTopLevelAncestor(), "Your player movement roll was a " + result + "!");
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieMovementDieRoll))
		{
			JOptionPane.showMessageDialog(getTopLevelAncestor(), "Your zombie movement roll was a " + result + "!");
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieCombat))
		{
			JOptionPane.showMessageDialog(getTopLevelAncestor(), "Your combat roll was a " + result + "!");
		}
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		boolean canEnable = GameHandler.instance.getGuiStateData().rollDiceButtonEnabled;
		boolean myTurn = ((Window) getTopLevelAncestor()).getPlayer().isPlayersTurn();
		setEnabled(canEnable && myTurn);
	}
}