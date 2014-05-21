package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.eventCardParents.InstantUseCard;
import main.eventCardParents.SingleUseCard;

public class DontThinkTheyreDead extends SingleUseCard implements InstantUseCard
{
	public DontThinkTheyreDead()
	{
		super(PossibleTarget.Pick, ECRB.get("DontThinkTheyreDead.name"), //$NON-NLS-1$
				ECRB.get("DontThinkTheyreDead.description")); //$NON-NLS-1$
	}
	
	@Override
	public int behavior(int num)
	{
		int roll1 = (int) (Math.random() * 6 + 1);
		int roll2 = (int) (Math.random() * 6 + 1);
		String message = ECRB.get("DontThinkTheyreDead.player") + (getTargetPlayer().getNumber() + 1) + ECRB.get("DontThinkTheyreDead.message_1") //$NON-NLS-1$ //$NON-NLS-2$
				+ roll1 + ECRB.get("DontThinkTheyreDead.message_2") + roll2 + ECRB.get("DontThinkTheyreDead.message_3"); //$NON-NLS-1$ //$NON-NLS-2$
		DialogHandler.showMessage(null, message, getName(), JOptionPane.INFORMATION_MESSAGE);
		
		if (roll1 > 3 && roll2 > 3)
		{
			message = ECRB.get("DontThinkTheyreDead.message_4"); //$NON-NLS-1$
			DialogHandler.showMessage(null, message, ECRB.get("DontThinkTheyreDead.title_1"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		}
		else
		{
			String choiceMessage = ECRB.get("DontThinkTheyreDead.message_5"); //$NON-NLS-1$
			boolean choice = DialogHandler.showChoice(null, choiceMessage, ECRB.get("DontThinkTheyreDead.title_2"), JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION; //$NON-NLS-1$
			
			if (choice)
			{
				if (roll1 <= 3)
				{
					int diff = 4 - roll1;
					
					if (getTargetPlayer().getBulletTokens() >= diff)
					{
						for (int i = 0; i < diff; i++)
						{
							getTargetPlayer().loseBulletToken();
						}
					}
					else
					{
						getTargetPlayer().loseLifeToken();
					}
				}
				if (roll2 <= 3)
				{
					int diff = 4 - roll2;
					
					if (getTargetPlayer().getBulletTokens() >= diff)
					{
						for (int i = 0; i < diff; i++)
						{
							getTargetPlayer().loseBulletToken();
						}
					}
					else
					{
						getTargetPlayer().loseLifeToken();
					}
				}
				message = ECRB.get("DontThinkTheyreDead.message_6"); //$NON-NLS-1$
				DialogHandler.showMessage(null, message, getName(), JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				message = ECRB.get("DontThinkTheyreDead.message_7"); //$NON-NLS-1$
				DialogHandler.showMessage(null, message, getName(), JOptionPane.WARNING_MESSAGE);
				getTargetPlayer().decrementZombiesCaptured();
				getTargetPlayer().decrementZombiesCaptured();
			}
		}
		return roll1 + (roll2 << 16);
	}
}