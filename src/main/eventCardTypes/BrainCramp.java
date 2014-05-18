package main.eventCardTypes;

import javax.swing.JOptionPane;

import main.eventCardParents.SingleUseCard;
import gui.DialogHandler;

public class BrainCramp extends SingleUseCard
{
	public BrainCramp()
	{
		super(PossibleTarget.Pick, "Brain Cramp", "Play when another player begins to move.  You may decide where or if that player moves.");
	}

	@Override
	public int behavior(int num)
	{
		Integer[] choices = new Integer[num + 1];
		for (int i = 0; i <= num; i += 1)
		{
			choices[i] = i;
		}
		int result = (int) DialogHandler.showListChoice(null, "Choose how many spaces player " + (getTargetPlayer().getNumber() + 1) + " can move.\nCan be between 0 and " + num + " (inclusive).", "Brain Cramp", JOptionPane.QUESTION_MESSAGE, choices);
		return result;
	}
}