package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.DataListener;
import main.GameHandler;
import main.Window;

public class EventCardDeckButton extends JButton implements ActionListener, DataListener
{
	public EventCardDeckButton()
	{
		setText("Event Card Deck");
		addActionListener(this);
		GameHandler.instance.addDataListener(this);
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

	@Override
	public void dataChanged(DataChangedEvent e)
	{
		boolean canEnable = GameHandler.instance.getGuiStateData().eventCardDeckButtonEnabled;
		boolean myTurn = ((Window)getTopLevelAncestor()).getPlayer().isPlayersTurn();
		setEnabled(canEnable && myTurn);
	}
}