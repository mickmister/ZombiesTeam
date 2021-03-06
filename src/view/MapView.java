package view;

import gui.DialogHandler;
import gui.ImageManager;
import gui.Window;
import internationalization.RB;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JViewport;

import main.GameHandler;
import main.MapTile;
import main.Player;
import main.eventCardTypes.ThisIsntSoBad;
import main.eventCardTypes.WhereDidEverybodyGo;

public class MapView extends JPanel implements Runnable, KeyListener, ComponentListener
{
	private final int SIZE = 11;
	private BufferedImage image;
	private Graphics2D graphics;
	
	public MapView()
	{
		this.image = new BufferedImage(240 * 11, 240 * 11, BufferedImage.TYPE_INT_ARGB);
		this.graphics = (Graphics2D) this.image.getGraphics();
		this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		setPreferredSize(new Dimension(240 * 11, 240 * 11));
		setFocusable(true);
		addKeyListener(this);
		addComponentListener(this);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		requestFocusInWindow();
		checkScrollPane();
		
		this.graphics.setFont(new Font("Segoe UI", Font.PLAIN, 13)); //$NON-NLS-1$
		this.graphics.drawImage(ImageManager.DIRT_TEXTURE, 0, 0, null);
		for (int y = 0; y < this.SIZE; y += 1)
		{
			for (int x = 0; x < this.SIZE; x += 1)
			{
				MapTileView view = new MapTileView(GameHandler.instance.getMap().getMapTile(y, x));
				view.draw(this.graphics, x, y, false);
			}
		}
		MapTile temp = GameHandler.instance.getMap().getTempTile();
		if (temp != null)
		{
			Point point = GameHandler.instance.getMap().getTempPos();
			MapTileView view = new MapTileView(temp);
			view.draw(this.graphics, point.x, point.y, true);
		}
		
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		String message = GameHandler.instance.getMap().getCurrentMessage(player);
		JViewport viewPort = (JViewport) getParent();
		Point view = viewPort.getViewPosition();
		
		this.graphics.setFont(new Font("Segoe UI", Font.PLAIN, 35)); //$NON-NLS-1$
		this.graphics.setColor(Color.BLACK);
		this.graphics.drawString(message, view.x + 22, view.y + 52);
		this.graphics.setColor(Color.WHITE);
		this.graphics.drawString(message, view.x + 20, view.y + 50);
		
		g.drawImage(this.image, 0, 0, null);
	}
	
	private void checkScrollPane()
	{
		if (GameHandler.instance.getMap().getTempTile() != null)
		{
			JViewport viewPort = (JViewport) getParent();
			Rectangle bounds = viewPort.getViewRect();
			Dimension size = viewPort.getViewSize();
			Point current = GameHandler.instance.getMap().getTempPos();
			
			int xCurrent = bounds.x;
			int yCurrent = bounds.y;
			int xMiddle = current.x * 240 + 120 - bounds.width / 2;
			int yMiddle = current.y * 240 + 120 - bounds.height / 2;
			int xNew = (int) (xCurrent * 0.75 + xMiddle * 0.25);
			int yNew = (int) (yCurrent * 0.75 + yMiddle * 0.25);
			xNew = Math.max(xNew, 0);
			yNew = Math.max(yNew, 0);
			xNew = Math.min(xNew, size.width - bounds.width);
			yNew = Math.min(yNew, size.height - bounds.height);
			viewPort.setViewPosition(new Point(xNew, yNew));
		}
	}
	
	private void centerScrollPane()
	{
		JViewport viewPort = (JViewport) getParent();
		Rectangle bounds = viewPort.getViewRect();
		Dimension size = viewPort.getViewSize();
		int xView = size.width / 2 - bounds.width / 2;
		int yView = size.height / 2 - bounds.height / 2;
		viewPort.setViewPosition(new Point(xView, yView));
	}
	
	private void displayInvalidLocation()
	{
		DialogHandler.showMessage(null, RB.get("MapView.invalid_location_message_1") //$NON-NLS-1$
				+ RB.get("MapView.invalid_location_message_2") + RB.get("MapView.invalid_location_message_3") //$NON-NLS-1$ //$NON-NLS-2$
				+ RB.get("MapView.invalid_location_message_4"), RB.get("MapView.invalid_location_title"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public void run()
	{
		try
		{
			while (true)
			{
				Thread.sleep(1000 / 30);
				repaint();
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		boolean skip = false;
		for (int i = 0; i < GameHandler.instance.getNumberOfPlayers(); i += 1)
		{
			if (GameHandler.instance.getEventDeck().doCardAction(GameHandler.instance.getPlayer(i), WhereDidEverybodyGo.class, 1) == 42)
			{
				skip = true;
			}
		}
		
		Window window = (Window) getTopLevelAncestor();
		Player player = window.getPlayer();
		if (player.isPlayersTurn() ^ skip)
		{
			switch (GameHandler.instance.getCurrentState())
			{
				case tilePlacement:
					handleTilePlacement(e);
					break;
				case zombiePlacement:
					handleZombiePlacement(e);
					break;
				case lifeTokenPlacement:
					handleLifeTokenPlacement(e);
					break;
				case bulletTokenPlacement:
					handleBulletTokenPlacement(e);
					break;
				case playerMovement:
					handlePlayerMovement(e);
					break;
				case zombieMovement:
					handleZombieMovement(e);
					break;
				case playerMovementDieRoll:
					break;
				case zombieCombat:
					break;
				case zombieMovementDieRoll:
					break;
			}
		}
	}
	
	private void handleTilePlacement(KeyEvent e)
	{
		if (GameHandler.instance.getMap().getTempTile() != null)
		{
			Point old = GameHandler.instance.getMap().getTempPos();
			Point next = getChangedPoint(old, e);
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_UP:
				case KeyEvent.VK_DOWN:
					GameHandler.instance.getMap().setTempPos(next);
					break;
				case KeyEvent.VK_SPACE:
					GameHandler.instance.getMap().getTempTile().rotateTile();
					break;
				case KeyEvent.VK_ENTER:
					try
					{
						GameHandler.instance.getMap().placeTempTile();
						GameHandler.instance.nextGameState();
						GameHandler.instance.getMap().getTempZombieTile().setTempZombiePos(new Point(1, 1));
					}
					catch (IllegalStateException exception)
					{
						displayInvalidLocation();
					}
					break;
			}
		}
	}
	
	private void handleZombiePlacement(KeyEvent e)
	{
		MapTile tile = GameHandler.instance.getMap().getTempZombieTile();
		Point next = getChangedPoint(tile.getTempZombiePos(), e);
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
				tile.setTempZombiePos(next);
				break;
			case KeyEvent.VK_ENTER:
				tile.placeTempZombie();
				break;
		}
	}
	
	private void handleBulletTokenPlacement(KeyEvent e)
	{
		MapTile tile = GameHandler.instance.getMap().getTempBulletTile();
		Point next = getChangedPoint(tile.getTempBulletPos(), e);
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
				tile.setTempBulletPos(next);
				break;
			case KeyEvent.VK_ENTER:
				tile.placeTempBullet();
				break;
		}
	}
	
	private void handleLifeTokenPlacement(KeyEvent e)
	{
		MapTile tile = GameHandler.instance.getMap().getTempBulletTile();
		Point next = getChangedPoint(tile.getTempLifePos(), e);
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
				tile.setTempLifePos(next);
				break;
			case KeyEvent.VK_ENTER:
				tile.placeTempLife();
				break;
		}
	}
	
	private void handlePlayerMovement(KeyEvent e)
	{
		Window window = (Window) getTopLevelAncestor();
		Player player = window.getPlayer();
		
		for (int i = 0; i < GameHandler.instance.getNumberOfPlayers(); i += 1)
		{
			if (GameHandler.instance.getEventDeck().doCardAction(GameHandler.instance.getPlayer(i), WhereDidEverybodyGo.class, 1) == 42)
			{
				player = GameHandler.instance.getPlayer(GameHandler.instance.getTurn());
				break;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			player.tryMoveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			player.tryMoveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			player.tryMoveUp();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			player.tryMoveDown();
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			// Test for WhereDidEverybodyGo card.
			for (int i = 0; i < GameHandler.instance.getNumberOfPlayers(); i += 1)
			{
				if (GameHandler.instance.getEventDeck().doCardAction(GameHandler.instance.getPlayer(i), WhereDidEverybodyGo.class, 1) == 42)
				{
					if (GameHandler.instance.getEventDeck().doCardAction(GameHandler.instance.getPlayer(i), WhereDidEverybodyGo.class, 2) == 42)
					{
						return;
					}
				}
			}
			GameHandler.instance.nextGameState();
		}
	}
	
	private void handleZombieMovement(KeyEvent e)
	{
		if (GameHandler.instance.getEventDeck().doCardAction(null, ThisIsntSoBad.class, 1) == 42)
		{
			handleThisIsntSoBadMovement(e);
			return;
		}
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
				GameHandler.instance.getMap().placeMovingZombie(e);
				break;
			case KeyEvent.VK_SPACE:
				GameHandler.instance.getMap().selectNextZombie();
				break;
			case KeyEvent.VK_ENTER:
				GameHandler.instance.nextGameState();
				break;
		}
	}
	
	private void handleThisIsntSoBadMovement(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_SPACE:
				GameHandler.instance.getMap().selectNextZombie();
				break;
			case KeyEvent.VK_ENTER:
				GameHandler.instance.getEventDeck().doCardAction(null, ThisIsntSoBad.class, 2);
				Player player = GameHandler.instance.getPlayer(GameHandler.instance.getTurn());
				player.setMovesRemaining(player.getMovesRemaining() - 1);
				if (player.getMovesRemaining() == 0)
				{
					GameHandler.instance.getEventDeck().doCardAction(null, ThisIsntSoBad.class, 3);
				}
				break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	
	private Point getChangedPoint(Point old, KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
				return new Point(old.x - 1, old.y);
			case KeyEvent.VK_RIGHT:
				return new Point(old.x + 1, old.y);
			case KeyEvent.VK_UP:
				return new Point(old.x, old.y - 1);
			case KeyEvent.VK_DOWN:
				return new Point(old.x, old.y + 1);
			default:
				return new Point(old.x, old.y);
		}
	}
	
	@Override
	public void componentResized(ComponentEvent e)
	{
		centerScrollPane();
	}
	
	@Override
	public void componentMoved(ComponentEvent e)
	{
	}
	
	@Override
	public void componentShown(ComponentEvent e)
	{
	}
	
	@Override
	public void componentHidden(ComponentEvent e)
	{
	}
}