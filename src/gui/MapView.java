package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.GameHandler;
import main.MapTile;

public class MapView extends JPanel implements Runnable
{
	private final int SIZE = 11;
	
	public MapView()
	{
		setPreferredSize(new Dimension(2400, 2400));
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		for (int y = 0; y < SIZE; y += 1)
		{
			for (int x = 0; x < SIZE; x += 1)
			{
				GameHandler.instance.getMap().getMapTile(y, x).draw(graphics, x, y, false);
			}
		}
		MapTile temp = GameHandler.instance.getMap().getTempTile();
		if (temp != null)
		{
			Point point = GameHandler.instance.getMap().getTempPos();
			temp.draw(graphics, point.x, point.y, true);
		}
		
		g.drawImage(image, 0, 0, null);
	}
	
	@Override
	public void run()
	{
		try
		{
			while (true)
			{
				Thread.sleep(100);
				repaint();
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}