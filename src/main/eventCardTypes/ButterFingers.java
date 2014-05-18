package main.eventCardTypes;

import gui.DialogHandler;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.eventCardParents.EventCard;
import main.eventCardParents.SingleUseCard;

public class ButterFingers extends SingleUseCard
{
	
	public ButterFingers()
	{
		super(PossibleTarget.Pick, "Butter Fingers", "Target player must discard a weapon or item in play or up to 2 bullets of your choice.");
	}
	
	@Override
	public int behavior(int num)
	{
		// check deck for active cards
		ArrayList<EventCard> cards = GameHandler.instance.getEventDeck().getActiveCardsForPlayer(getTargetPlayer());
		ArrayList<Object> choices = new ArrayList<Object>();
		choices.add("Bullet tokens");
		choices.addAll(cards);
		Object result = DialogHandler.showListChoice(null, "Do you want to remove bullet tokens or\na discarded event card from the player?",
				getName(), JOptionPane.QUESTION_MESSAGE, choices.toArray());
		
		for (EventCard card : cards)
		{
			if (card.equals(result))
			{
				EventCard item = (EventCard) result;
				GameHandler.instance.getEventDeck().removeActiveCard(item);
				String message = "The card " + item.getName() + " was removed from Player " + (getTargetPlayer().getNumber() + 1) + "'s hand.";
				DialogHandler.showMessage(null, message, getName(), JOptionPane.INFORMATION_MESSAGE);
				return 0;
			}
		}
		
		// bullet token operation and display
		int numTokens = Math.min(2, getTargetPlayer().getBulletTokens());
		getTargetPlayer().loseBulletToken();
		getTargetPlayer().loseBulletToken();
		String message = numTokens + " bullet tokens were removed from Player " + (getTargetPlayer().getNumber() + 1) + "'s hand.";
		DialogHandler.showMessage(null, message, getName(), JOptionPane.INFORMATION_MESSAGE);
		return 0;
	}
}