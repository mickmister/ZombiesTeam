package main.eventCardTypes;

import main.eventCardParents.UseForRoundCard;

public class AlternateFoodSource extends UseForRoundCard
{
	public AlternateFoodSource()
	{
		super(PossibleTarget.None, "Alternate Food Source",
				"No zombies attack and players may not attack any zombies until the end of your next turn.", 2);
	}
	
	@Override
	public int behavior(int num)
	{
		return 1;
	}
}