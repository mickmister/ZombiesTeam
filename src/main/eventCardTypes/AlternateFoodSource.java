package main.eventCardTypes;

import internationalization.ECRB;
import main.eventCardParents.UseForRoundCard;

public class AlternateFoodSource extends UseForRoundCard
{
	public AlternateFoodSource()
	{
		super(PossibleTarget.None, ECRB.get("AlternateFoodSource.name"), //$NON-NLS-1$
				ECRB.get("AlternateFoodSource.description"), 2); //$NON-NLS-1$
	}
	
	@Override
	public int behavior(int num)
	{
		return 1;
	}
}