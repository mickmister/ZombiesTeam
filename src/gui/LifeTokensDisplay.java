package gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.Player;

public class LifeTokensDisplay extends JLabel implements Runnable
{
	private Player player;
	
	public LifeTokensDisplay(Player player)
	{
		this.player = player;
		setIcon(new ImageIcon(ImageManager.HEART_PICTURE));
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
				setText(" " + this.player.getLifeTokens());
				Thread.sleep(1000);
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}