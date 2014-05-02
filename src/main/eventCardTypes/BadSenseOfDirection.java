package main.eventCardTypes;

import javax.swing.JOptionPane;

import gui.DialogHandler;
import main.GameHandler;
import main.Player;

public class BadSenseOfDirection extends OneUseCard {

	public BadSenseOfDirection() {
		super(PossibleTarget.Pick, "Bad Sense of Direction", "Discard one life token, target player will be moved to town sqaure.");
	}

	@Override
	public int behavior(int num) {
		DialogHandler.showMessage(null, "You discarded one life token, target is now at the town square.", this.getName(), JOptionPane.INFORMATION_MESSAGE);
		GameHandler.instance.getPlayer(num).loseLifeToken();
		Player target = this.getTargetPlayer();
		target.resetPlayerLocation();
		return 1;
	}

}
