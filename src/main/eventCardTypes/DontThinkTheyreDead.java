package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.eventCardParents.SingleUseCard;

public class DontThinkTheyreDead extends SingleUseCard
{
	
	public DontThinkTheyreDead()
	{
		super(PossibleTarget.Pick, "I Don't Think They're Dead",
				"Play this card to make target opponent roll two dice. If either die result is 3 or lower,"
						+ " opponent must return two zombies to the zombie pool. Heart and bullet tokens may be used to modify these rolls.");
	}
	
	@Override
	public int behavior(int num)
	{
		int roll1 = (int) (Math.random() * 6 + 1);
		int roll2 = (int) (Math.random() * 6 + 1);
		String message = "Player " + (getTargetPlayer().getNumber() + 1) + ", you have rolled two dice to protect your zombies. You first roll: "
				+ roll1 + ". Your second roll: " + roll2 + ".";
		DialogHandler.showMessage(null, message, getName(), JOptionPane.INFORMATION_MESSAGE);
		
		if (roll1 > 3 && roll2 > 3)
		{
			message = "Your dice rolling saved from giving up any zombies!";
			DialogHandler.showMessage(null, message, "Saved by the dice!", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			String choiceMessage = "Do you want to use bullet or life tokens?";
			boolean choice = DialogHandler.showChoice(null, choiceMessage, "Use Tokens?", JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION;
			
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
				message = "Your tokens saved your zombies!";
				DialogHandler.showMessage(null, message, "Yay Tokens!", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				message = "You chose to give up your zombies!";
				DialogHandler.showMessage(null, message, "You lost zombies", JOptionPane.WARNING_MESSAGE);
				getTargetPlayer().decrementZombiesCaptured();
				getTargetPlayer().decrementZombiesCaptured();
			}
		}
		return roll1 + (roll2 << 16);
	}
	
}
