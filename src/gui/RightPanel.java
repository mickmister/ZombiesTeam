package gui;

import internationalization.RB;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class RightPanel extends JPanel
{
	public RightPanel()
	{
		setLayout(new GridLayout(0, 3, 10, 0));
		setBorder(new EmptyBorder(10, 10, 0, 10));
		setPreferredSize(new Dimension(750, 1));
		
		JPanel inHandPanel = new JPanel();
		inHandPanel.setLayout(new GridLayout(1, 1));
		inHandPanel.setBorder(new TitledBorder(RB.get("RightPanel.cards_in_your_hand"))); //$NON-NLS-1$
		inHandPanel.add(createInHandPanel());
		
		JPanel activePanel = new JPanel();
		activePanel.setLayout(new BorderLayout());
		activePanel.setBorder(new TitledBorder(RB.get("RightPanel.active_cards"))); //$NON-NLS-1$
		activePanel.add(createActivePanel());
		
		JPanel discardedPanel = new JPanel();
		discardedPanel.setLayout(new BorderLayout());
		discardedPanel.setBorder(new TitledBorder(RB.get("RightPanel.discarded_cards"))); //$NON-NLS-1$
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
			panel.add(new DiscardedCardButton(i));
		}
		return panel;
	}
}