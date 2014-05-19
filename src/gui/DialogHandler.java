package gui;

import internationalization.RB;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.MapTile;

public class DialogHandler
{
	public static int defaultReturn;
	public static boolean isTesting = true;
	
	public static void showMessage(Component comp, String message, String title, int messageType)
	{
		if (!DialogHandler.isTesting)
		{
			JOptionPane.showMessageDialog(comp, message, title, messageType);
		}
		
	}
	
	public static int showChoice(Component comp, String message, String title, int messageType)
	{
		if (!DialogHandler.isTesting)
		{
			return JOptionPane.showConfirmDialog(comp, message, title, JOptionPane.YES_NO_OPTION, messageType);
		}
		else
		{
			return DialogHandler.defaultReturn;
		}
	}
	
	public static Object showListChoice(Component comp, String message, String title, int messageType, Object[] options)
	{
		if (!DialogHandler.isTesting)
		{
			return JOptionPane.showInputDialog(comp, message, title, messageType, null, options, options[0]);
		}
		else
		{
			return options[DialogHandler.defaultReturn];
		}
	}
	
	public static MapTile showBulidingChoice(Component comp, String title)
	{
		ArrayList<MapTile> tiles = GameHandler.instance.getMap().getCurrentSpecialBuildings();
		ArrayList<String> names = new ArrayList<String>();
		for (MapTile tile : tiles)
		{
			names.add(tile.getSpecialName().toString());
		}
		Object[] options = names.toArray();
		String choice = (String) DialogHandler.showListChoice(comp, RB.get("DialogHandler.show_building_message"), title, //$NON-NLS-1$
				JOptionPane.INFORMATION_MESSAGE, options);
		
		MapTile tileChoice = null;
		for (MapTile tile : tiles)
		{
			if (choice.equals(tile.getSpecialName().toString()))
			{
				tileChoice = tile;
				break;
			}
		}
		return tileChoice;
	}
}