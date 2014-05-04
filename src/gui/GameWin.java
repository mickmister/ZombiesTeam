package gui;

import internationalization.*;

import javax.swing.*;

import main.*;

public class GameWin
{
	public GameWin(Player player, boolean capAll)
	{
		int num = player.getNumber() + 1;
		String message = Messages.getString("GameWin.congrats_you_win"); //$NON-NLS-1$
		if (capAll)
		{
			message += Messages.getString("GameWin.captured_25_zombies"); //$NON-NLS-1$
		}
		else
		{
			message += Messages.getString("GameWin.cleared_helipad"); //$NON-NLS-1$
		}
		DialogHandler.showMessage(null, message,
				Messages.getString("GameWin.player") + num + Messages.getString("GameWin.wins"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		if (!DialogHandler.isTesting)
		{
			System.exit(0);
		}
	}
}