package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.DataListener;
import main.GameHandler;
import main.GameHandler.GameState;
import main.Player;

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
		if (GameHandler.instance.getCurrentState() == GameState.playerMovementDieRoll
				|| GameHandler.instance.getCurrentState() == GameState.zombieMovementDieRoll)
		{
			rollDiceClicked();
		}
	}
	
	public void rollDiceClicked()
	{
		int rollNum = (int) (Math.random() * 6 + 1);
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(game.getTurn());
		player.setMovesRemaining(rollNum);
		JOptionPane.showMessageDialog(null, "You rolled a " + rollNum + "!");
		System.out.println("Rolled a " + rollNum);
		game.nextGameState();
	}

	@Override
	public void dataChanged(DataChangedEvent e)
	{
		setEnabled(GameHandler.instance.getGuiStateData().rollDiceButtonEnabled);
	}
}