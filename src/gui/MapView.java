package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import main.GameHandler;

public class MapView extends JPanel
{
	private final int SIZE = 11;
	
	public MapView()
	{
		setLayout(new GridLayout(SIZE, SIZE));
		setPreferredSize(new Dimension(2400, 2400));
		
		for (int y = 0; y < SIZE; y += 1)
		{
			for (int x = 0; x < SIZE; x += 1)
			{
				add(new JPanel());
			}
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		for (int y = 0; y < SIZE; y += 1)
		{
			for (int x = 0; x < SIZE; x += 1)
			{
				int n = y * SIZE + x;
				if (getComponentCount() > n)
				{
					if (getComponent(n) != GameHandler.instance.getMap().getMapTile(y, x))
					{
						remove(n);
						add(GameHandler.instance.getMap().getMapTile(y, x), n);
						repaint();
					}
				}
			}
		}
	}
}