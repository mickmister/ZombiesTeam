package main.eventCardTypes;

import javax.swing.JOptionPane;

import gui.Dialog;
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
		Dialog.showMessage(null, "Your movement got skipped", this.getName(), JOptionPane.WARNING_MESSAGE);
		return 1;
	}

}
