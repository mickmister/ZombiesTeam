package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import main.*;

public class BottomPanel extends JPanel implements DataListener
{
	private Window window;
	private IconDisplay lifeTokenDisplay;
	private IconDisplay bulletTokenDisplay;
	private IconDisplay zombiesDisplay;
	
	public BottomPanel(Window window)
	{
		this.window = window;
		this.lifeTokenDisplay = new IconDisplay(ImageManager.HEART_PICTURE);
		this.bulletTokenDisplay = new IconDisplay(ImageManager.BULLET_PICTURE);
		this.zombiesDisplay = new IconDisplay(ImageManager.ZOMBIE_PICTURE);
		setLayout(new GridLayout(1, 6, 10, 0));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(1, 200));
		
		add(new MapTileDeckButton());
		add(this.lifeTokenDisplay);
		add(this.bulletTokenDisplay);
		add(this.zombiesDisplay);
		add(new RollDiceButton());
		
		GameHandler.instance.addDataListener(this);
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		this.lifeTokenDisplay.setNumber(this.window.getPlayer().getLifeTokens());
		this.bulletTokenDisplay.setNumber(this.window.getPlayer().getBulletTokens());
		this.zombiesDisplay.setNumber(this.window.getPlayer().getZombiesCaptured());
	}
}