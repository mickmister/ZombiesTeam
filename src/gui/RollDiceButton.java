package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

import main.GameHandler;
import main.GameHandler.GameState;

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
		if (GameHandler.instance.getCurrentState() == GameState.playerMovement || GameHandler.instance.getCurrentState() == GameState.zombieMovement)
		{
			rollDiceClicked();
		}
	}
	
	public void rollDiceClicked()
	{
		Random dice = new Random();
		int rollNum = dice.nextInt(6) + 1;
		System.out.println(rollNum);
	}
}