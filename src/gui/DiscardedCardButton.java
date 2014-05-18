package gui;

import java.util.ArrayList;

import javax.swing.JButton;

import main.DataListener;
import main.GameHandler;
import main.Player;
import main.eventCardParents.EventCard;

public class DiscardedCardButton extends JButton implements DataListener
{
	private int index;
	
	public DiscardedCardButton(int index)
	{
		this.index = index;
		GameHandler.instance.addDataListener(this);
		setEnabled(false);
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		ArrayList<EventCard> cards = GameHandler.instance.getEventDeck().getDiscardedCardsForPlayer(player);
		if (cards.size() > this.index)
		{
			EventCard card = cards.get(this.index);
			setVisible(true);
			setText("<html><center>IN EFFECT:<hr><b>" + card.getName() + "</b><hr>" + card.getDescription()); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{
			setVisible(false);
		}
	}
}