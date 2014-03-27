package main;
import gui.BottomPanel;
import gui.MapView;
import gui.RightPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class Window extends JFrame
{
	private int number;
	private Player player;
	
	public Window(int number)
	{
		this.number = number;
		this.player = new Player();
		
		setSize(1600, 900);
		setLocation(this.number * 50, this.number * 50);
		setTitle("Zombies!!! (Player " + (this.number + 1) + ")");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(new BottomPanel(this), BorderLayout.PAGE_END);
		add(new RightPanel(this), BorderLayout.LINE_END);
		JScrollPane scrollPane = new JScrollPane(new MapView());
		add(scrollPane, BorderLayout.CENTER);
		
		setVisible(true);
		
		Rectangle bounds = scrollPane.getViewport().getViewRect();
		Dimension size = scrollPane.getViewport().getViewSize();
		int x = (size.width - bounds.width) / 2;
		int y = (size.height - bounds.height) / 2;
		scrollPane.getViewport().setViewPosition(new Point(x, y));
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
}