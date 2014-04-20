package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class RightPanel extends JPanel
{
	public RightPanel()
	{
		setLayout(new GridLayout(3, 1));
		setBorder(new EmptyBorder(10, 10, 0, 10));
		setPreferredSize(new Dimension(300, 1));
		
		for (int i = 0; i < 3; i += 1)
		{
			add(new EventCardButton(i));
		}
	}
}