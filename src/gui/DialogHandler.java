package gui;

import java.awt.Component;

import javax.swing.JOptionPane;

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
}