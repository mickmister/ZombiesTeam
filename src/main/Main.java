package main;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main
{
	public static void main(String[] args)
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
		
		String[] choices = {"2 players", "3 players", "4 players"};
		Object result = JOptionPane.showInputDialog(null, "Select the number of players.", "Choice", JOptionPane.PLAIN_MESSAGE, null, choices, "2 players");
		
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
		
		new GameHandler(numberOfPlayers);
	}
}