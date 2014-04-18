package gui;

import java.awt.*;

import javax.swing.*;

public class IconDisplay extends JLabel
{
	public IconDisplay(Image image)
	{
		setIcon(new ImageIcon(image));
		setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public void setNumber(int number)
	{
		setText(" " + number);
	}
}