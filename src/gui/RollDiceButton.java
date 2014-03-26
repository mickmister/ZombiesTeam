package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

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
		rollDiceClicked();
	}
	
	public void rollDiceClicked()
	{
		// Do crap.
	}
}