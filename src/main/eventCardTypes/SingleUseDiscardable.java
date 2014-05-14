package main.eventCardTypes;

import main.MapTileDeck.SpecialNames;

public abstract class SingleUseDiscardable extends OneUseCard
{
	private SpecialNames buildingName;

	public SingleUseDiscardable(PossibleTarget posTar, String name,
			String description, SpecialNames buildingName)
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

	

}
