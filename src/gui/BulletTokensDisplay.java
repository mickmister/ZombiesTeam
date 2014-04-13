package gui;

import javax.swing.*;

import main.*;

public class BulletTokensDisplay extends JLabel implements DataListener
{
	private Player player;
	
	public BulletTokensDisplay(Player player)
	{
		this.player = player;
		setIcon(new ImageIcon(ImageManager.BULLET_PICTURE));
		setHorizontalAlignment(SwingConstants.CENTER);
		setText("");
		
		GameHandler.instance.addDataListener(this);
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		setText(" " + this.player.getBulletTokens());
	}
}