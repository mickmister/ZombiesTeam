package main.eventCardTypes;

import internationalization.ECRB;
import main.MapTileDeck.SpecialNames;
import main.Player;
import main.eventCardParents.SingleUseDiscardable;

public class LotsOfAmmo extends SingleUseDiscardable
{
	
	public LotsOfAmmo()
	{
		super(PossibleTarget.Self, ECRB.get("LotsOfAmmo.name"), ECRB.get("LotsOfAmmo.description") //$NON-NLS-1$ //$NON-NLS-2$
				+ ECRB.get("LotsOfAmmo.message"), SpecialNames.SportingGoods); //$NON-NLS-1$
	}
	
	@Override
	public int behavior(int num)
	{
		Player player = getTargetPlayer();
		player.addBulletToken();
		player.addBulletToken();
		player.addBulletToken();
		return 1;
	}
	
}
