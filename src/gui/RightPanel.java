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
		setPreferredSize(new Dimension(700, 1));
		
		JPanel inHandPanel = new JPanel();
		inHandPanel.setLayout(new GridLayout(1, 1));
		inHandPanel.setBorder(new TitledBorder("Cards in your hand"));
		inHandPanel.add(createInHandPanel());
		
		JPanel activePanel = new JPanel();
		activePanel.setLayout(new BorderLayout());
		activePanel.setBorder(new TitledBorder("Active cards"));
		activePanel.add(createActivePanel());
		
		JPanel discardedPanel = new JPanel();
		discardedPanel.setLayout(new BorderLayout());
		discardedPanel.setBorder(new TitledBorder("Discarded cards in effect"));
		discardedPanel.add(createDiscardedPanel());
		
		add(inHandPanel);
		add(activePanel);
		add(discardedPanel);
	}
	
	private JPanel createInHandPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(3, 1, 0, 10));
		for (int i = 0; i < 3; i += 1)
		{
			JPanel subPanel = new JPanel();
			subPanel.setLayout(new BorderLayout());
			subPanel.add(new EventCardButton(i), BorderLayout.CENTER);
			subPanel.add(new SwapCardButton(i), BorderLayout.PAGE_END);
			panel.add(subPanel);
		}
		return panel;
	}
	
	private JPanel createActivePanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(3, 1, 0, 10));
		for (int i = 0; i < 3; i += 1)
		{
			panel.add(new ActiveCardButton(i));
		}
		return panel;
	}
	
	private JPanel createDiscardedPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(3, 1, 0, 10));
		for (int i = 0; i < 3; i += 1)
		{
			panel.add(new ActiveCardButton(i));
		}
		return panel;
	}
}