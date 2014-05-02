package gui;

import javax.swing.*;

import main.*;

public class GameWin
{
	public GameWin(Player player, boolean capAll)
	{
		int num = player.getNumber() + 1;
		String message = "Congratulations, you win!\n";
		if (capAll)
		{
			message += "You captured 25 zombies.";
		}
		else
		{
			message += "You cleared all zombies from the helipad.";
		}
		Dialog.showMessage(null, message, "Player " + num + " Wins!", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
}