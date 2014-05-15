package main.eventCardTypes;

import main.EventCard;
import main.GameHandler;

public abstract class OneUseCard extends EventCard
{
	
	public OneUseCard(PossibleTarget posTar, String name, String description)
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
