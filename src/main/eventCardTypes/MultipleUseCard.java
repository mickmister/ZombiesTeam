package main.eventCardTypes;

import main.EventCard;
import main.GameHandler;

public abstract class MultipleUseCard extends EventCard
{
	
	private int maxUses;
	private int usesExhausted;
	
	public MultipleUseCard(PossibleTarget posTar, String name, String description, int totalUses)
	{
		super(posTar, name, description);
		this.maxUses = totalUses;
		this.usesExhausted = 0;
	}
	
	@Override
	public abstract int behavior(int num);
	
	@Override
	public void checkRemove()
	{
		this.usesExhausted++;
		if (this.maxUses == this.usesExhausted)
		{
			// remove
			GameHandler.instance.getEventDeck().removeActiveCard(this);
		}
		
	}
	
}
