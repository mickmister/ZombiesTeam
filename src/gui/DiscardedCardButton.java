package gui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import main.*;
import main.eventCardTypes.*;

public class DiscardedCardButton extends JButton implements DataListener, ActionListener
{
	private int index;
	
	public DiscardedCardButton(int index)
	{
		this.index = index;
		GameHandler.instance.addDataListener(this);
		addActionListener(this);
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		ArrayList<EventCard> cards = GameHandler.instance.getEventDeck().getActiveCardsForPlayer(player);
		if (cards.size() > this.index)
		{
			EventCard card = cards.get(this.index);
			setVisible(true);
			setText("<html><center>DISCARD<br>" + card.getName() + "<br>-----------------------<br>" + card.getDescription()); //$NON-NLS-1$ //$NON-NLS-2$
			boolean canDiscard = (card instanceof SingleUseDiscardable) || (card instanceof CustomUseDiscardable);
			setEnabled(canDiscard);
		}
		else
		{
			setVisible(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		eventCardClicked();
	}
	
	public void eventCardClicked()
	{
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		EventCard card = GameHandler.instance.getEventDeck().getActiveCardsForPlayer(player).get(this.index);
		GameHandler.instance.getEventDeck().discard(card);
	}
}