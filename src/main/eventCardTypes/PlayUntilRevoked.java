package main.eventCardTypes;

import main.EventCard;
import main.MapTileDeck.SpecialNames;

public abstract class PlayUntilRevoked extends EventCard {
	private SpecialNames buildingName;

	public PlayUntilRevoked(PossibleTarget posTar, String name,
			String description, SpecialNames buildingName) {
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
	public void checkRemove() {
		//stays in play
	}

}
