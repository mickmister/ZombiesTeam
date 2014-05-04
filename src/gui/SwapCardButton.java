package gui;

import java.awt.event.*;

import javax.swing.*;

import main.*;

public class SwapCardButton extends JButton implements DataListener, ActionListener
{
	private int index;
	
	public SwapCardButton(int index)
	{
		this.index = index;
		setText("Swap");
		GameHandler.instance.addDataListener(this);
		addActionListener(this);
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		EventCard card = player.getCardFromHand(this.index);
		if (card == null)
		{
			setEnabled(false);
		}
		else
		{
			setEnabled(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		swapCardClicked();
	}
	
	public void swapCardClicked()
	{
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		if (!player.checkCardPlayed())
		{
			player.removeCardFromHand(this.index);
			GameHandler.instance.fireDataChangedEvent(null);
			DialogHandler.showMessage(getTopLevelAncestor(), "You swapped out Event Card " + (this.index + 1) + " for a new one.", "Event Card Swap",
					JOptionPane.INFORMATION_MESSAGE);
			
			player.drawNewCards();
			GameHandler.instance.fireDataChangedEvent(null);
			player.setCardPlayed(true);
		}
		else
		{
			DialogHandler.showMessage(getTopLevelAncestor(),
					"You have already played an Event Card this turn.\n\nYou must wait until your next turn to play another.",
					"Cannot Play 2 Event Cards", JOptionPane.WARNING_MESSAGE);
		}
	}
}