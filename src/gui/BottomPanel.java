package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Player;

public class BottomPanel extends JPanel
{
	private Window window;
	
	public BottomPanel(Window window)
	{
		this.window = window;
		setLayout(new GridLayout(1, 6));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(1, 200));
		
		add(new MapTileDeckButton());
		add(new LifeTokensDisplay(getPlayer()));
		add(new BulletTokensDisplay(getPlayer()));
		add(new ZombiesDisplay(getPlayer()));
		add(new RollDiceButton());
	}
	
	public Player getPlayer()
	{
		return this.window.getPlayer();
	}
}