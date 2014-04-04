package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

import main.GameHandler;
import main.GameHandler.GameState;
import main.Player;

public class RollDiceButton extends JButton implements ActionListener
{
	public RollDiceButton()
	{
		setText("Roll Dice");
		addActionListener(this);
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
		Random dice = new Random();
		int rollNum = dice.nextInt(6) + 1;
		GameHandler game = GameHandler.instance;
		Player player = game.getPlayer(game.getTurn());
		player.setMovesRemaining(rollNum);
		System.out.println(rollNum);
		game.nextGameState();
	}
}