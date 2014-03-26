package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.Player;

public class BulletTokensDisplay extends JLabel implements Runnable
{
	private Player player;
	
	public BulletTokensDisplay(Player player)
	{
		try
		{
			this.player = player;
			BufferedImage bulletPicture = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Bullet Picture.png"));
			setIcon(new ImageIcon(bulletPicture));
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