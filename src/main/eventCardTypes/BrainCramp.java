package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.eventCardParents.SingleUseCard;

public class BrainCramp extends SingleUseCard
{
	public BrainCramp()
	{
		super(PossibleTarget.Pick, ECRB.get("BrainCramp.name"), ECRB.get("BrainCramp.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public int behavior(int num)
	{
		Integer[] choices = new Integer[num + 1];
		for (int i = 0; i <= num; i += 1)
		{
			choices[i] = i;
		}
		int result = (int) DialogHandler.showListChoice(null, ECRB.get("BrainCramp.message_1") + (getTargetPlayer().getNumber() + 1) //$NON-NLS-1$
				+ ECRB.get("BrainCramp.message_2") + num + ECRB.get("BrainCramp.message_3"), getName(), JOptionPane.QUESTION_MESSAGE, choices); //$NON-NLS-1$ //$NON-NLS-2$ 
		return result;
	}
}