package main.eventCardTypes;

import main.GameHandler;
import main.Player;

public class KeysAreStillIn extends OneUseCard {

	public KeysAreStillIn() {
		super(PossibleTarget.Self, "The Keys Are Still In It", "Move up to 10 spaces in place of making a movement roll. You must fight zombies as normal.");
	}

	@Override
	public int behavior(int num) {
		Player player = GameHandler.instance.getPlayer(GameHandler.instance.getTurn());
		player.setMovesRemaining(10000);
		GameHandler.instance.nextGameState();
		return 1;
	}

}
