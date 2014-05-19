package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.eventCardParents.EventCard;
import main.eventCardParents.SingleUseCard;

public class ButterFingers extends SingleUseCard
{
	
	public ButterFingers()
	{
		super(PossibleTarget.Pick, ECRB.get("ButterFingers.name"), ECRB.get("ButterFingers.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		// check deck for active cards
		ArrayList<EventCard> cards = GameHandler.instance.getEventDeck().getActiveCardsForPlayer(getTargetPlayer());
		ArrayList<Object> choices = new ArrayList<Object>();
		choices.add(ECRB.get("ButterFingers.option_bullet_tokens")); //$NON-NLS-1$
		choices.addAll(cards);
		Object result = DialogHandler.showListChoice(null, ECRB.get("ButterFingers.option_message"), //$NON-NLS-1$
				getName(), JOptionPane.QUESTION_MESSAGE, choices.toArray());
		
		for (EventCard card : cards)
		{
			if (card.equals(result))
			{
				EventCard item = (EventCard) result;
				GameHandler.instance.getEventDeck().removeActiveCard(item);
				String message = ECRB.get("ButterFingers.message_1") + item.getName() + ECRB.get("ButterFingers.message_2") + (getTargetPlayer().getNumber() + 1) + ECRB.get("ButterFingers.message_3"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				DialogHandler.showMessage(null, message, getName(), JOptionPane.INFORMATION_MESSAGE);
				return 0;
			}
		}
		
		// bullet token operation and display
		int numTokens = Math.min(2, getTargetPlayer().getBulletTokens());
		getTargetPlayer().loseBulletToken();
		getTargetPlayer().loseBulletToken();
		String message = numTokens + ECRB.get("ButterFingers.message_4") + (getTargetPlayer().getNumber() + 1) + ECRB.get("ButterFingers.message_5"); //$NON-NLS-1$ //$NON-NLS-2$
		DialogHandler.showMessage(null, message, getName(), JOptionPane.INFORMATION_MESSAGE);
		return 0;
	}
}