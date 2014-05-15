package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.EventCard;
import main.EventCardDeck;
import main.GameHandler;

public class ButterFingers extends OneUseCard
{
	
	public ButterFingers()
	{
		super(PossibleTarget.Pick, "Butter Fingers", "Target player must discard a weapon or item in play or up to 2 bullets of your choice.");
	}
	
	@Override
	public int behavior(int num)
	{
		// check deck for active cards
		EventCardDeck deck = GameHandler.instance.getEventDeck();
		EventCard item = deck.removeByActivator(getTargetPlayer());
		if (item != null)
		{
			// event card display
			String message = "The card " + item.getName() + " was removed from Player " + (getTargetPlayer().getNumber() + 1) + "'s hand.";
			DialogHandler.showMessage(null, message, getName(), JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			// bullet token operation and display
			int numTokens;
			if (getTargetPlayer().getBulletTokens() < 2)
			{
				numTokens = getTargetPlayer().getBulletTokens();
			}
			else
			{
				numTokens = 2;
			}
			getTargetPlayer().loseBulletToken();
			getTargetPlayer().loseBulletToken();
			String message = numTokens + " bullet tokens were removed from Player" + (getTargetPlayer().getNumber() + 1) + "'s hand.";
			DialogHandler.showMessage(null, message, getName(), JOptionPane.INFORMATION_MESSAGE);
		}
		return 0;
	}
	
}
