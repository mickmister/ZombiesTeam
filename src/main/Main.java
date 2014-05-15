package main;

import gui.DialogHandler;
import internationalization.Messages;

import java.awt.Font;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main
{
	/**
	 * Initiates the Zombies program and asks the user how many players there will be. Does not take
	 * any command-line arguments.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		DialogHandler.isTesting = false;
		setWindowsLaF();
		
		Locale locale = askUserForLanguage();
		new Messages(locale);
		
		int numPlayers = askUserForNumPlayers();
		
		new GameHandler(numPlayers);
		GameHandler.instance.initWindows();
	}
	
	private static Locale askUserForLanguage()
	{
		String[] options = new String[] { "English (default)", "Spanish", "French" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		int response = JOptionPane.showOptionDialog(null, "Choose a language:", "Language", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
				null, options, options[0]);
		if (response == 0)
		{
			// Locale for English - United States
			return new Locale("en", "US"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (response == 1)
		{
			// Locale for Spanish - Mexico
			return new Locale("es", "MX"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (response == 2)
		{
			// Locale for French - France
			return new Locale("fr", "FR"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		System.exit(1);
		return null;
	}
	
	public static int askUserForNumPlayers()
	{
		String[] options = { Messages.getString("Main.2_players"), Messages.getString("Main.3_players"), Messages.getString("Main.4_players") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Object response = JOptionPane.showInputDialog(null,
				Messages.getString("Main.numPlayers_message"), Messages.getString("Main.numPlayers_title"), JOptionPane.QUESTION_MESSAGE, null, //$NON-NLS-1$ //$NON-NLS-2$
				options, Messages.getString("Main.2_players")); //$NON-NLS-1$
		
		int numPlayers = -1;
		for (int i = 0; i < options.length; i += 1)
		{
			if (options[i].equals(response))
			{
				numPlayers = i + 2;
			}
		}
		if (numPlayers == -1)
		{
			System.exit(1);
		}
		return numPlayers;
	}
	
	private static void setWindowsLaF()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.getDefaults().put("Label.font", new Font("Segoe UI", Font.PLAIN, 50)); //$NON-NLS-1$ //$NON-NLS-2$
			UIManager.getDefaults().put("Button.font", new Font("Segoe UI", Font.PLAIN, 16)); //$NON-NLS-1$ //$NON-NLS-2$
			UIManager.getDefaults().put("TitledBorder.font", new Font("Segoe UI", Font.PLAIN, 16)); //$NON-NLS-1$ //$NON-NLS-2$
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
	}
}