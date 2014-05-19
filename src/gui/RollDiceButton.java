package gui;

import internationalization.RB;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
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
		setFont(new Font("Segoe UI", Font.PLAIN, 30)); //$NON-NLS-1$
		setText("   " + RB.get("RollDiceButton.roll_dice")); //$NON-NLS-1$ //$NON-NLS-2$
		setIcon(new ImageIcon(ImageManager.DICE_PICTURE));
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
					RB.get("RollDiceButton.player_movement_roll") + result + "!", RB.get("RollDiceButton.roll_dice"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieMovementDieRoll))
		{
			DialogHandler.showMessage(getTopLevelAncestor(),
					RB.get("RollDiceButton.zombie_movement_roll") + result + "!", RB.get("RollDiceButton.roll_dice"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieCombat))
		{
			DialogHandler.showMessage(getTopLevelAncestor(),
					RB.get("RollDiceButton.zombie_combat_roll") + result + "!", RB.get("RollDiceButton.roll_dice"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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