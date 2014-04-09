package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Player;
import main.Window;

public class BottomPanel extends JPanel
{
	private Window window;
	
	public MapTileDeckButton mapTileDeckButton;
	public RollDiceButton rollDiceButton;
	
	public BottomPanel(Window window)
	{
		this.window = window;
		setLayout(new GridLayout(1, 6));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(1, 200));
		
		this.mapTileDeckButton = new MapTileDeckButton();
		this.rollDiceButton = new RollDiceButton();
		add(this.mapTileDeckButton);
		add(new LifeTokensDisplay(getPlayer()));
		add(new BulletTokensDisplay(getPlayer()));
		add(new ZombiesDisplay(getPlayer()));
		add(this.rollDiceButton);
		add(new EventCardDeckButton());
	}
	
	public Player getPlayer()
	{
		return this.window.getPlayer();
	}
}