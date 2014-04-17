package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.DataListener;
import main.EventCard;
import main.EventCard.PossibleTarget;
import main.GameHandler;
import main.Player;

public class EventCardButton extends JButton implements DataListener, ActionListener {
	
	
	private int index;

	public EventCardButton(int index)
	{
		this.index = index;
		GameHandler.instance.addDataListener(this);
		this.addActionListener(this);
	}

	@Override
	public void dataChanged(DataChangedEvent e) {
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		EventCard card = player.getCardFromHand(this.index);
		if(card == null)
		{
			setEnabled(false);
		}
		else
		{
			setText("<html><center>" + card.getName() + "<br>" + card.getDescription());
			setEnabled(true);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		if (!player.checkCardPlayed())
		{			
			EventCard card = player.removeCardFromHand(this.index);
			GameHandler.instance.fireDataChangedEvent(null);
			if(card.getPossibleTarget() == PossibleTarget.Pick)
			{
				//dialog
			}
			else if(card.getPossibleTarget() == PossibleTarget.Self)
			{
				card.setTargetPlayer(player);
			}
			else
			{
				card.setTargetPlayer(null);
			}
			GameHandler.instance.getEventDeck().addActiveCard(card);
			player.setCardPlayed(true);
			
		}
		
	}

}
