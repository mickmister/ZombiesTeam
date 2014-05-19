package main.eventCardParents;

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
	public void checkRemove()
	{
		this.usesExhausted++;
		if (this.maxUses == this.usesExhausted)
		{
			GameHandler.instance.getEventDeck().removeActiveCard(this);
		}
	}
}