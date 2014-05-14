package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class RightPanel extends JPanel
{
	public RightPanel()
	{
		setLayout(new GridLayout(0, 3, 10, 0));
		setBorder(new EmptyBorder(10, 10, 0, 10));
		setPreferredSize(new Dimension(800, 1));
		
		JPanel inHandPanel = new JPanel();
		inHandPanel.setLayout(new BorderLayout());
		inHandPanel.setBorder(new TitledBorder("Cards in your hand"));
		
		JPanel inHandButtons = new JPanel();
		inHandButtons.setLayout(new GridLayout(3, 1, 0, 10));
		for (int i = 0; i < 3; i += 1)
		{
			inHandButtons.add(new EventCardButton(i));
		}
		
		JPanel inHandSwaps = new JPanel();
		inHandSwaps.setLayout(new GridLayout(3, 1, 0, 10));
		for (int i = 0; i < 3; i += 1)
		{
			inHandSwaps.add(new SwapCardButton(i));
		}
		
		inHandPanel.add(inHandButtons, BorderLayout.CENTER);
		inHandPanel.add(inHandSwaps, BorderLayout.LINE_END);
		
		JPanel activePanel = new JPanel();
		activePanel.setLayout(new BorderLayout());
		activePanel.setBorder(new TitledBorder("Active cards"));
		
		JPanel discardedPanel = new JPanel();
		discardedPanel.setLayout(new BorderLayout());
		discardedPanel.setBorder(new TitledBorder("Discarded cards in effect"));
		
		add(inHandPanel);
		add(activePanel);
		add(discardedPanel);
	}
}