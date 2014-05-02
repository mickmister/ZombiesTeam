package gui;

import java.awt.event.*;

import javax.swing.*;

import main.*;
import main.EventCard.PossibleTarget;
import main.eventCardTypes.BadSenseOfDirection;

public class EventCardButton extends JButton implements DataListener, ActionListener
{
	
	private int index;
	
	public EventCardButton(int index)
	{
		this.index = index;
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
			setText("<html><center>" + card.getName() + "<br>" + card.getDescription());
			setEnabled(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		if (!player.checkCardPlayed())
		{
			EventCard card = player.removeCardFromHand(this.index);
			GameHandler.instance.fireDataChangedEvent(null);
			if (card.getPossibleTarget() == PossibleTarget.Pick)
			{
				int numPlayers = GameHandler.instance.getNumberOfPlayers();
				String[] choicesTotal = { "Player 1", "Player 2", "Player 3", "Player 4" };
				String[] choices = new String[numPlayers - 1];
				int j = 0;
				int turn = GameHandler.instance.getTurn();
				for (int i = 0; i < numPlayers; i += 1)
				{
					if (i != turn)
					{
						choices[j] = choicesTotal[i];
						j++;
					}
				}
				Object result = JOptionPane.showInputDialog(getTopLevelAncestor(), "Select the target player.", "Target Player",
						JOptionPane.PLAIN_MESSAGE, null, choices, "Player 1");
				int target = -1;
				for (int i = 0; i < numPlayers - 1; i += 1)
				{
					if (choices[i].equals(result))
					{
						if (i >= turn)
						{
							target = i + 1;
						}
						else
						{
							target = i;
						}
					}
				}
				if (target == -1)
				{
					target = player.getNumber();
				}
				card.setTargetPlayer(GameHandler.instance.getPlayer(target));
			}
			else if (card.getPossibleTarget() == PossibleTarget.Self)
			{
				card.setTargetPlayer(player);
			}
			else
			{
				card.setTargetPlayer(null);
			}
			GameHandler.instance.getEventDeck().addActiveCard(card);
			player.setCardPlayed(true);
			GameHandler.instance.getEventDeck().doCardAction(card.getTargetPlayer(), BadSenseOfDirection.class, player.getNumber());
		}
		else
		{
			DialogHandler.showMessage(getTopLevelAncestor(),
					"You have already played an Event Card this turn.\n\nYou must wait until your next turn to play another.",
					"Cannot Play 2 Event Cards", JOptionPane.WARNING_MESSAGE);
		}
	}
}