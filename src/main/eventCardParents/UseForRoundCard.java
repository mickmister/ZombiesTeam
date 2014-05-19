package main.eventCardParents;

import main.GameHandler;

public abstract class UseForRoundCard extends EventCard
{
	private int maxRounds;
	private int roundsUsed;
	
	public UseForRoundCard(PossibleTarget posTar, String name, String description, int rounds)
	{
		super(posTar, name, description);
		this.maxRounds = rounds;
		this.roundsUsed = 0;
	}
	
	@Override
	public int action(int num)
	{
		return behavior(num);
	}
	
	@Override
	public void checkRemove()
	{
		this.roundsUsed += 1;
		if (this.roundsUsed == this.maxRounds)
		{
			if (this instanceof CustomUseDiscardable)
			{
				GameHandler.instance.getEventDeck().removeDiscardedCard(this);
			}
			else
			{
				GameHandler.instance.getEventDeck().removeActiveCard(this);
			}
		}
	}
}