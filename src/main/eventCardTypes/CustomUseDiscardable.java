package main.eventCardTypes;

import main.EventCard;
import main.GameHandler;
import main.MapTileDeck.SpecialNames;

public abstract class CustomUseDiscardable extends EventCard
{
	
	private SpecialNames buildingName;
	
	public CustomUseDiscardable(PossibleTarget posTar, String name, String description, SpecialNames buildingName)
	{
		super(posTar, name, description);
		this.buildingName = buildingName;
	}
	
	public SpecialNames getBuildingName()
	{
		return this.buildingName;
	}
	
	@Override
	public abstract int behavior(int num);
	
	@Override
	public void checkRemove()
	{} // Should do nothing.
	
	public void customRemove()
	{
		GameHandler.instance.getEventDeck().removeDiscardedCard(this);
	}
}