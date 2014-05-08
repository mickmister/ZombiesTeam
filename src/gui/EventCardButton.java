package gui;

import internationalization.*;

import java.awt.event.*;

import javax.swing.*;

import main.*;
import main.EventCard.PossibleTarget;
import main.eventCardTypes.*;

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
			setText("<html><center>" + card.getName() + "<br>" + card.getDescription()); //$NON-NLS-1$ //$NON-NLS-2$
			setEnabled(true);
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
		if (!player.checkCardPlayed())
		{
			EventCard card = player.getCardFromHand(this.index);
			if(card instanceof PlayUntilRevoked) // || card instanceof PlayWhenDiscarded
			{
				if(card.checkCorrectBuilding(player))
				{
					DialogHandler.showMessage(null, "Special building card played successfully!", "Special Building Card", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					DialogHandler.showMessage(null, "Not in correct building for this card.", "Invalid Placement", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			player.removeCardFromHand(this.index);
			GameHandler.instance.fireDataChangedEvent(null);
			if (card.getPossibleTarget() == PossibleTarget.Pick)
			{
				int numPlayers = GameHandler.instance.getNumberOfPlayers();
				String[] choicesTotal = {
						Messages.getString("EventCardButton.player_1"), Messages.getString("EventCardButton.player_2"), Messages.getString("EventCardButton.player_3"), Messages.getString("EventCardButton.player_4") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
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
				Object result = JOptionPane.showInputDialog(getTopLevelAncestor(),
						Messages.getString("EventCardButton.select_target_player"), Messages.getString("EventCardButton.target_player"), //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.PLAIN_MESSAGE, null, choices, Messages.getString("EventCardButton.player_1")); //$NON-NLS-1$
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
					if(player.getNumber() == 0)
					{
						target = 1;
					}
					else
					{
						target = 0;
					}
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
			card.setActivator(player);
			GameHandler.instance.getEventDeck().addActiveCard(card);
			player.setCardPlayed(true);
			GameHandler.instance.getEventDeck().doCardAction(card.getTargetPlayer(), BadSenseOfDirection.class, player.getNumber());
			GameHandler.instance.getEventDeck().doCardAction(card.getTargetPlayer(), ButterFingers.class, player.getNumber());
			
		}
		else
		{
			DialogHandler.showMessage(getTopLevelAncestor(), Messages.getString("EventCardButton.cannot_play_2_cards_message"), //$NON-NLS-1$
					Messages.getString("EventCardButton.cannot_play_2_cards_title"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$
		}
	}

}