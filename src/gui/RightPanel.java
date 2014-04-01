package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Window;

public class RightPanel extends JPanel
{
	private Window window;
	
	public RightPanel(Window window)
	{
		this.window = window;
		setLayout(new GridLayout(3, 1));
		setBorder(new EmptyBorder(10, 10, 0, 10));
		setPreferredSize(new Dimension(300, 1));
		
		for (int i = 0; i < 3; i += 1)
		{
			add(new JButton("Event card"));
		}
	}
}