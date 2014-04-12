package gui;

import javax.swing.*;

import main.*;

public class BulletTokensDisplay extends JLabel implements Runnable
{
	private Player player;
	
	public BulletTokensDisplay(Player player)
	{
		this.player = player;
		setIcon(new ImageIcon(ImageManager.BULLET_PICTURE));
		setHorizontalAlignment(SwingConstants.CENTER);
		setText("");
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run()
	{
		try
		{
			while (true)
			{
				setText(" " + this.player.getBulletTokens());
				Thread.sleep(1000);
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}