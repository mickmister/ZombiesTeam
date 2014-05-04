package gui;

import java.awt.*;

import javax.swing.*;

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
}