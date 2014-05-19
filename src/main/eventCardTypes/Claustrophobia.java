package main.eventCardTypes;

import main.eventCardParents.UseForRoundCard;

public class Claustrophobia extends UseForRoundCard
{
	public Claustrophobia()
	{
		super(PossibleTarget.Pick, "Claustrophobia", "Target player may not enter any building during their next turn.  If in a building, target player must move out of the building.", 1);
	}

	@Override
	public int behavior(int num)
	{
		return 1;
	}
}