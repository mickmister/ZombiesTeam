package main.eventCardTypes;

import main.GameHandler;
import main.Player;

public class Fear extends OneUseCard {

	public Fear() {
		super(PossibleTarget.Pick, "Fear", "Target player may not move by any means during their next turn.");
		
	}

	@Override
	public int behavior(int num) {
		Player player = this.getTargetPlayer();
		player.setMovesRemaining(0);
		GameHandler.instance.nextGameState();
		return 1;
	}

}
