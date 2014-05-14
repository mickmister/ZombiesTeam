package main.eventCardTypes;

import main.*;
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
	{
		//
	}
	
	public void discard()
	{
		GameHandler.instance.getEventDeck().removeActiveCard(this);
		GameHandler.instance.getEventDeck().addDiscardedActiveCard(this);
	}
	
	public void customRemove()
	{
		GameHandler.instance.getEventDeck().removeDiscardedActiveCard(this);
	}
	
}
