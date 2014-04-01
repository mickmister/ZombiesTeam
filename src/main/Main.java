package main;

import gui.ImageManager;

import java.awt.Font;

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
		setWindowsLaF();
		
		String[] choices = { "2 players", "3 players", "4 players" };
		Object result = JOptionPane.showInputDialog(null, "Select the number of players.", "Number of Players", JOptionPane.PLAIN_MESSAGE, null,
				choices, "2 players");
		
		int numberOfPlayers = -1;
		for (int i = 0; i < choices.length; i += 1)
		{
			if (choices[i].equals(result))
			{
				numberOfPlayers = i + 2;
			}
		}
		if (numberOfPlayers == -1)
		{
			System.exit(1);
		}
		
		new ImageManager();
		new GameHandler(numberOfPlayers);
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