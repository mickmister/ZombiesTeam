package gui;

import internationalization.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.DataListener;
import main.GameHandler;
import main.GameHandler.GameState;
import main.RollDice;

public class RollDiceButton extends JButton implements ActionListener, DataListener
{
	public RollDiceButton()
	{
		setText(Messages.getString("RollDiceButton.roll_dice")); //$NON-NLS-1$
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
			DialogHandler.showMessage(getTopLevelAncestor(),
					Messages.getString("RollDiceButton.player_movement_roll") + result + "!", Messages.getString("RollDiceButton.roll_dice"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieMovementDieRoll))
		{
			DialogHandler.showMessage(getTopLevelAncestor(),
					Messages.getString("RollDiceButton.zombie_movement_roll") + result + "!", Messages.getString("RollDiceButton.roll_dice"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieCombat))
		{
			DialogHandler
					.showMessage(
							getTopLevelAncestor(),
							Messages.getString("RollDiceButton.zombie_combat_roll") + result + "!", Messages.getString("RollDiceButton.roll_dice"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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