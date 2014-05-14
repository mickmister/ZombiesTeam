package gui;

import internationalization.*;

import java.awt.*;

import javax.swing.*;

import main.*;
import view.*;

public class Window extends JFrame
{
	private int number;
	
	public Window(int number)
	{
		this.number = number;
		
		setSize(1600, 900);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setLocation(this.number * 50, this.number * 50);
		setTitle(Messages.getString("Window.title_prefix") + (this.number + 1) + Messages.getString("Window.title_postfix")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(new BottomPanel(this), BorderLayout.PAGE_END);
		add(new RightPanel(), BorderLayout.LINE_END);
		JScrollPane scrollPane = new JScrollPane(new MapView());
		add(scrollPane, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public Player getPlayer()
	{
		return GameHandler.instance.getPlayer(this.number);
	}
}