package main;

import gui.*;
import internationalization.*;

import java.awt.*;
import java.util.*;

import javax.swing.*;

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
		String[] options = new String[] { "English (default)", "Spanish", "French" };
		int response = JOptionPane.showOptionDialog(null, "Choose a language:", "Language", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);
		if (response == 0)
		{
			// Locale for English - United States
			return new Locale("en", "US");
		}
		if (response == 1)
		{
			// Locale for Spanish - Mexico
			return new Locale("es", "MX");
		}
		if (response == 2)
		{
			// Locale for French - France
			return new Locale("fr", "FR");
		}
		System.exit(1);
		return null;
	}
	
	public static int askUserForNumPlayers()
	{
		String[] options = { "2 players", "3 players", "4 players" };
		Object response = JOptionPane.showInputDialog(null, "Select the number of players:", "Number of Players", JOptionPane.QUESTION_MESSAGE, null,
				options, "2 players");
		
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
			UIManager.getDefaults().put("Label.font", new Font("Segoe UI", Font.PLAIN, 50));
			UIManager.getDefaults().put("Button.font", new Font("Segoe UI", Font.PLAIN, 16));
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
	}
}