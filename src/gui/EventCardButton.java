package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.DataListener;
import main.EventCard;
import main.GameHandler;
import main.Player;

public class EventCardButton extends JButton implements DataListener, ActionListener {
	
	
	private int index;
	private EventCard card;

	public EventCardButton(int index)
	{
		this.index = index;
		GameHandler.instance.addDataListener(this);
		this.addActionListener(this);
	}

	@Override
	public void dataChanged(DataChangedEvent e) {
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		this.card = player.getCardAtIndex(this.index);
		setText("<html><center>" + this.card.getName() + "<br>" + this.card.getDescription());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		if (!player.checkCardPlayed())
		{			
			player.addActiveEventCard(this.card.getName());
			player.setCardPlayed(true);
		}
		
	}

}
