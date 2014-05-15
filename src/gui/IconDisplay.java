package gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class IconDisplay extends JLabel
{
	public IconDisplay(Image image)
	{
		setIcon(new ImageIcon(image));
		setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public void setNumber(int number)
	{
		setText(" " + number); //$NON-NLS-1$
	}
}