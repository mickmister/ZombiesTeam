package main.eventCardTypes;

import gui.*;

import javax.swing.*;

import main.*;

public class ButterFingers extends OneUseCard
{
	
	public ButterFingers()
	{
		super(PossibleTarget.Pick, "Butter Fingers", Messages.getString("EventCard.ButterFingers.desc"));
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
			String message = Messages.getString("EventCard.ButterFingers.card_rem_1") + item.getName()
					+ Messages.getString("EventCard.ButterFingers.card_rem_2") + (getTargetPlayer().getNumber() + 1)
					+ Messages.getString("EventCard.ButterFingers.card_rem_3");
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
			String message = numTokens + Messages.getString("EventCard.ButterFingers.token_rem") + (getTargetPlayer().getNumber() + 1)
					+ Messages.getString("EventCard.ButterFingers.card_rem_3");
			DialogHandler.showMessage(null, message, getName(), JOptionPane.INFORMATION_MESSAGE);
		}
		return 0;
	}
	
}
