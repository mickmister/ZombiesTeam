package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.Player;

public class ZombiesDisplay extends JLabel implements Runnable
{
	private Player player;
	
	public ZombiesDisplay(Player player)
	{
		try
		{
			this.player = player;
			BufferedImage zombiePicture = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Zombie Picture.png"));
			setIcon(new ImageIcon(zombiePicture));
			setHorizontalAlignment(SwingConstants.CENTER);
			setText("");
			
			Thread thread = new Thread(this);
			thread.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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