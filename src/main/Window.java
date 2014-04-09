package main;

import gui.BottomPanel;
import gui.MapView;
import gui.RightPanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class Window extends JFrame
{
	private int number;
	
	public BottomPanel bottomPanel;
	public RightPanel rightPanel;
	
	public Window(int number)
	{
		this.number = number;
		
		setSize(1600, 900);
		setLocation(this.number * 50, this.number * 50);
		setTitle("Zombies!!! (Player " + (this.number + 1) + ")");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		this.bottomPanel = new BottomPanel(this);
		this.rightPanel = new RightPanel(this);
		add(this.bottomPanel, BorderLayout.PAGE_END);
		add(this.rightPanel, BorderLayout.LINE_END);
		JScrollPane scrollPane = new JScrollPane(new MapView());
		add(scrollPane, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public Player getPlayer()
	{
		return GameHandler.instance.getPlayer(this.number);
	}
}