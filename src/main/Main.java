package main;

import gui.DialogHandler;
import internationalization.ECRB;
import internationalization.RB;

import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;
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
		new RB(locale);
		new ECRB(locale);
		
		int numPlayers = askUserForNumPlayers();
		
		new GameHandler(numPlayers);
		GameHandler.instance.initWindows();
	}
	
	private static Locale askUserForLanguage()
	{
		String[] options = new String[] { "     English     ", "     Spanish     ", "     French     " }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
		String[] options = { RB.get("Main.2_players"), RB.get("Main.3_players"), RB.get("Main.4_players") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Object response = JOptionPane.showInputDialog(null,
				RB.get("Main.numPlayers_message"), RB.get("Main.numPlayers_title"), JOptionPane.QUESTION_MESSAGE, null, //$NON-NLS-1$ //$NON-NLS-2$
				options, RB.get("Main.2_players")); //$NON-NLS-1$
		
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
			
			Font font = new Font("Segoe UI", Font.PLAIN, 13); //$NON-NLS-1$
			Enumeration<Object> keys = UIManager.getDefaults().keys();
			while (keys.hasMoreElements())
			{
				Object key = keys.nextElement();
				Object value = UIManager.get(key);
				if (value != null)
				{
					if (value instanceof javax.swing.plaf.FontUIResource)
					{
						UIManager.put(key, font);
					}
				}
			}
			
			UIManager.getDefaults().put("Label.font", new Font("Segoe UI", Font.PLAIN, 40)); //$NON-NLS-1$ //$NON-NLS-2$
			UIManager.put("Button.focus", new Color(0, 0, 0, 0)); //$NON-NLS-1$
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
	}
}