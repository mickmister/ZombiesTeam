package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class RightPanel extends JPanel
{
	public RightPanel()
	{
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 0, 10));
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(3, 1, 0, 10));
		buttons.setPreferredSize(new Dimension(300, 1));
		for (int i = 0; i < 3; i += 1)
		{
			buttons.add(new EventCardButton(i));
		}
		
		JPanel swaps = new JPanel();
		swaps.setLayout(new GridLayout(3, 1, 0, 10));
		for (int i = 0; i < 3; i += 1)
		{
			swaps.add(new SwapCardButton(i));
		}
		
		add(buttons, BorderLayout.CENTER);
		add(swaps, BorderLayout.LINE_END);
	}
}