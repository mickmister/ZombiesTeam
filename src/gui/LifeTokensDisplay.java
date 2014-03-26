package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.Player;

public class LifeTokensDisplay extends JLabel implements Runnable
{
	private Player player;
	
	public LifeTokensDisplay(Player player)
	{
		try
		{
			this.player = player;
			BufferedImage heartPicture = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Heart Picture.png"));
			setIcon(new ImageIcon(heartPicture));
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