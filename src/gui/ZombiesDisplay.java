package gui;

import javax.swing.*;

import main.*;

public class ZombiesDisplay extends JLabel implements Runnable
{
	private Player player;
	
	public ZombiesDisplay(Player player)
	{
		this.player = player;
		setIcon(new ImageIcon(ImageManager.ZOMBIE_PICTURE));
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
				setText(" " + this.player.getZombiesCaptured());
				Thread.sleep(1000);
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}