package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class EventCardDeckButton extends JButton implements ActionListener
{
	public EventCardDeckButton()
	{
		setText("Event Card Deck");
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		eventCardDeckClicked();
	}
	
	public void eventCardDeckClicked()
	{
		// Do crap.
	}
}