package main;
import gui.BottomPanel;
import gui.RightPanel;

import java.awt.BorderLayout;

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
		
		setSize(1024, 768);
		setLocation(this.number * 50, this.number * 50);
		setTitle("Zombies!!! (Player " + (this.number + 1) + ")");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(new BottomPanel(this), BorderLayout.PAGE_END);
		add(new RightPanel(this), BorderLayout.LINE_END);
		add(new JScrollPane(new Map()), BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
}