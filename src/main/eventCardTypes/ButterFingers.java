package main.eventCardTypes;

import javax.swing.JOptionPane;

import gui.DialogHandler;
import main.EventCard;
import main.EventCardDeck;
import main.GameHandler;

import internationalization.*;

public class ButterFingers extends OneUseCard {

	public ButterFingers() {
		super(PossibleTarget.Pick, "Butter Fingers", Messages.getString("EventCard.ButterFingers.desc"));
	}

	@Override
	public int behavior(int num) {
		//check deck for active cards
		EventCardDeck deck = GameHandler.instance.getEventDeck();
		EventCard item = deck.removeByActivator(this.getTargetPlayer());
		if(item != null)
		{
			//event card display
			String message = Messages.getString("EventCard.ButterFingers.card_rem_1") + item.getName() + Messages.getString("EventCard.ButterFingers.card_rem_2") + (this.getTargetPlayer().getNumber() + 1) + Messages.getString("EventCard.ButterFingers.card_rem_3");
			DialogHandler.showMessage(null, message, this.getName(), JOptionPane.INFORMATION_MESSAGE);			
		}
		else
		{
			//bullet token operation and display
			int numTokens;
			if(this.getTargetPlayer().getBulletTokens() < 2)
			{
				numTokens = this.getTargetPlayer().getBulletTokens();
			}
			else
			{
				numTokens = 2;
			}
			this.getTargetPlayer().loseBulletToken();
			this.getTargetPlayer().loseBulletToken();
			String message = numTokens + Messages.getString("EventCard.ButterFingers.token_rem") + (this.getTargetPlayer().getNumber() + 1) + Messages.getString("EventCard.ButterFingers.card_rem_3");
			DialogHandler.showMessage(null, message, this.getName(), JOptionPane.INFORMATION_MESSAGE);
		}
		return 0;
	}
	

}
