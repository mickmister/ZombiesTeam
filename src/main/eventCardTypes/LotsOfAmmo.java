package main.eventCardTypes;

import main.MapTileDeck.SpecialNames;
import main.*;

public class LotsOfAmmo extends SingleUseDiscardable
{
	
	public LotsOfAmmo()
	{
		super(PossibleTarget.Self, "Lots of Ammo", "Play this card in front of you when you are in the Sporting Goods Store."
				+ "Discard this item to gain three additional bullets.", SpecialNames.SportingGoods);
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
