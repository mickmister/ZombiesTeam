package main.eventCardParents;

import main.GameHandler;

public abstract class SingleUseCard extends EventCard
{
	
	public SingleUseCard(PossibleTarget posTar, String name, String description)
	{
		super(posTar, name, description);
	}
	
	@Override
	public abstract int behavior(int num);
	
	@Override
	public void checkRemove()
	{
		GameHandler.instance.getEventDeck().removeActiveCard(this);
	}
	
}
