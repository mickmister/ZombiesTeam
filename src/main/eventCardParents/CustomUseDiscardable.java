package main.eventCardParents;

import main.MapTileDeck.SpecialNames;

public abstract class CustomUseDiscardable extends UseForRoundCard
{
	private SpecialNames buildingName;
	
	public CustomUseDiscardable(PossibleTarget posTar, String name, String description, SpecialNames buildingName, int rounds)
	{
		super(posTar, name, description, rounds);
		this.buildingName = buildingName;
	}
	
	public SpecialNames getBuildingName()
	{
		return this.buildingName;
	}
}