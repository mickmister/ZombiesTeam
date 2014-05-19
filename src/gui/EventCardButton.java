package gui;

import internationalization.RB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.DataListener;
import main.GameHandler;
import main.Player;
import main.eventCardParents.CustomUseDiscardable;
import main.eventCardParents.EventCard;
import main.eventCardParents.EventCard.PossibleTarget;
import main.eventCardParents.PlayUntilRevokedCard;
import main.eventCardParents.SingleUseDiscardable;
import main.eventCardTypes.BadSenseOfDirection;
import main.eventCardTypes.ButterFingers;
import main.eventCardTypes.Claustrophobia;
import main.eventCardTypes.CouldntGetAnyWorse;
import main.eventCardTypes.DontThinkTheyreDead;
import main.eventCardTypes.SlightMiscalculation;

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
			setText("<html><center><b>" + card.getName() + "</b><hr>" + card.getDescription()); //$NON-NLS-1$ //$NON-NLS-2$
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
			if (checkCorrectBuilding(card, player))
			{
				player.removeCardFromHand(this.index);
				if (card.getPossibleTarget() == PossibleTarget.Pick)
				{
					int target = promptUserForTarget(player);
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
				
				activateImmediateCards(card, player);
				GameHandler.instance.fireDataChangedEvent(null);
			}
		}
		else
		{
			DialogHandler.showMessage(getTopLevelAncestor(), RB.get("EventCardButton.cannot_play_2_cards_message"), //$NON-NLS-1$
					RB.get("EventCardButton.cannot_play_2_cards_title"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$
		}
	}
	
	private boolean checkCorrectBuilding(EventCard card, Player player)
	{
		if (card instanceof PlayUntilRevokedCard || card instanceof SingleUseDiscardable || card instanceof CustomUseDiscardable)
		{
			if (card.checkCorrectBuilding(player))
			{
				DialogHandler.showMessage(getTopLevelAncestor(),
						RB.get("EventCardButton.special_building_message"), RB.get("EventCardButton.special_building_title"), //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			else
			{
				DialogHandler.showMessage(getTopLevelAncestor(),
						RB.get("EventCardButton.not_correct_building_message"), RB.get("EventCardButton.not_correct_building_title"), //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if (card instanceof CouldntGetAnyWorse || card instanceof SlightMiscalculation)
		{
			if (GameHandler.instance.getMap().getCurrentSpecialBuildings().size() > 0)
			{
				return true;
			}
			else
			{
				DialogHandler.showMessage(getTopLevelAncestor(),
						RB.get("EventCardButton.no_buildings_message"), RB.get("EventCardButton.no_buildings_title"), //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	private void activateImmediateCards(EventCard card, Player player)
	{
		GameHandler.instance.getEventDeck().doCardAction(card.getTargetPlayer(), BadSenseOfDirection.class, player.getNumber());
		GameHandler.instance.getEventDeck().doCardAction(card.getTargetPlayer(), ButterFingers.class, player.getNumber());
		GameHandler.instance.getEventDeck().doCardAction(card.getTargetPlayer(), DontThinkTheyreDead.class, player.getNumber());
		GameHandler.instance.getEventDeck().doCardAction(card.getTargetPlayer(), Claustrophobia.class, 0);
	}
	
	private int promptUserForTarget(Player player)
	{
		int numPlayers = GameHandler.instance.getNumberOfPlayers();
		String[] choicesTotal = {
				RB.get("EventCardButton.player_1"), RB.get("EventCardButton.player_2"), RB.get("EventCardButton.player_3"), RB.get("EventCardButton.player_4") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		String[] choices = new String[numPlayers - 1];
		int j = 0;
		int turn = ((Window) getTopLevelAncestor()).getPlayer().getNumber();
		for (int i = 0; i < numPlayers; i += 1)
		{
			if (i != turn)
			{
				choices[j] = choicesTotal[i];
				j++;
			}
		}
		Object result = JOptionPane.showInputDialog(getTopLevelAncestor(),
				RB.get("EventCardButton.select_target_player"), RB.get("EventCardButton.target_player"), //$NON-NLS-1$ //$NON-NLS-2$
				JOptionPane.PLAIN_MESSAGE, null, choices, RB.get("EventCardButton.player_1")); //$NON-NLS-1$
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
			if (player.getNumber() == 0)
			{
				target = 1;
			}
			else
			{
				target = 0;
			}
		}
		
		return target;
	}
}