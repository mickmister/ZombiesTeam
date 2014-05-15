package gui;

import internationalization.RB;

import javax.swing.JOptionPane;

import main.Player;

public class GameWin
{
	public GameWin(Player player, boolean capAll)
	{
		int num = player.getNumber() + 1;
		String message = RB.get("GameWin.congrats_you_win"); //$NON-NLS-1$
		if (capAll)
		{
			message += RB.get("GameWin.captured_25_zombies"); //$NON-NLS-1$
		}
		else
		{
			message += RB.get("GameWin.cleared_helipad"); //$NON-NLS-1$
		}
		DialogHandler.showMessage(null, message, RB.get("GameWin.player") + num + RB.get("GameWin.wins"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		if (!DialogHandler.isTesting)
		{
			System.exit(0);
		}
	}
}