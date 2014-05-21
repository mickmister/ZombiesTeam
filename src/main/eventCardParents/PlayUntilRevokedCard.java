package main.eventCardParents;

import main.MapTileDeck.SpecialNames;

public abstract class PlayUntilRevokedCard extends EventCard
{
	private SpecialNames buildingName;
	
	public PlayUntilRevokedCard(PossibleTarget posTar, String name, String description, SpecialNames buildingName)
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
		// Stays in play, do nothing.
	}
}