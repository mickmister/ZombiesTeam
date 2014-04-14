package view;

import gui.*;
import gui.Window;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import main.*;
import main.GameHandler.GameState;

public class MapView extends JPanel implements Runnable, KeyListener
{
	private final int SIZE = 11;
	private BufferedImage image;
	private Graphics2D graphics;
	private boolean hasBeenCentered;
	
	public MapView()
	{
		this.image = new BufferedImage(240 * 11, 240 * 11, BufferedImage.TYPE_INT_ARGB);
		this.graphics = (Graphics2D) this.image.getGraphics();
		this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		setPreferredSize(new Dimension(240 * 11, 240 * 11));
		setFocusable(true);
		addKeyListener(this);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		requestFocusInWindow();
		checkScrollPane();
		if (!this.hasBeenCentered)
		{
			this.hasBeenCentered = true;
			centerScrollPane();
		}
		
		this.graphics.setFont(new Font("Segoe UI", Font.PLAIN, 13));
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
		
		String message = "";
		if (GameHandler.instance.getCurrentState().equals(GameState.tilePlacement))
		{
			message = "Draw and place a map tile";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.playerMovementDieRoll))
		{
			message = "Roll the dice to move";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.playerMovement))
		{
			Player player = ((Window) getTopLevelAncestor()).getPlayer();
			message = player.getMovesRemaining() + " move(s) remaining";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombiePlacement))
		{
			MapTile tile = GameHandler.instance.getMap().getTempZombieTile();
			message = "Place " + tile.getZombiesToPlace() + " more zombie(s)";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieCombat))
		{
			message = "Roll the dice to fight";
		}
		if (GameHandler.instance.getCurrentState().equals(GameState.zombieMovementDieRoll))
		{
			message = "Roll the dice to move zombies";
		}
		if (!((Window) getTopLevelAncestor()).getPlayer().isPlayersTurn())
		{
			message = "Not your turn";
		}
		
		JViewport viewPort = (JViewport) getParent();
		Point view = viewPort.getViewPosition();
		this.graphics.setFont(new Font("Segoe UI", Font.PLAIN, 35));
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
		JOptionPane.showMessageDialog(null, "The selected location is not valid for the given map tile.\n"
				+ "The tile must be touching at least one other tile,\n" + "all touching roads must connect (and not become blocked),\n"
				+ "and it may not be placed on top of an existing tile.\n\nTry again.", "Invalid Placement", JOptionPane.WARNING_MESSAGE);
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
		Window window = (Window) getTopLevelAncestor();
		Player player = window.getPlayer();
		if (!player.isPlayersTurn())
		{
			return;
		}
		
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
	
	private void handleLifeTokenPlacement(KeyEvent e)
	{
		MapTile tile = GameHandler.instance.getMap().getTempZombieTile();
		Point current = tile.getTempZombiePos();
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			tile.setTempZombiePos(new Point(current.x - 1, current.y));
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			tile.setTempZombiePos(new Point(current.x + 1, current.y));
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			tile.setTempZombiePos(new Point(current.x, current.y - 1));
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			tile.setTempZombiePos(new Point(current.x, current.y + 1));
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			tile.placeTempZombie();
		}
		
	}

	private void handleBulletTokenPlacement(KeyEvent e)
	{
		MapTile tile = GameHandler.instance.getMap().getTempBulletTile();
		Point current = tile.getTempBulletPos();
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			tile.setTempBulletPos(new Point(current.x - 1, current.y));
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			tile.setTempBulletPos(new Point(current.x + 1, current.y));
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			tile.setTempBulletPos(new Point(current.x, current.y - 1));
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			tile.setTempBulletPos(new Point(current.x, current.y + 1));
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			tile.placeTempBullet();
		}
		
	}

	private void handleZombieMovement(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			// TODO
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			// TODO
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			// TODO
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			// TODO
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			// TODO
		}
	}
	
	private void handleZombiePlacement(KeyEvent e)
	{
		
		MapTile tile = GameHandler.instance.getMap().getTempZombieTile();
		Point current = tile.getTempZombiePos();
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			tile.setTempZombiePos(new Point(current.x - 1, current.y));
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			tile.setTempZombiePos(new Point(current.x + 1, current.y));
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			tile.setTempZombiePos(new Point(current.x, current.y - 1));
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			tile.setTempZombiePos(new Point(current.x, current.y + 1));
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			tile.placeTempZombie();
		}
	}
	
	private void handlePlayerMovement(KeyEvent e)
	{
		Window window = (Window) getTopLevelAncestor();
		Player player = window.getPlayer();
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
			GameHandler.instance.nextGameState();
		}
	}
	
	private void handleTilePlacement(KeyEvent e)
	{
		if (GameHandler.instance.getMap().getTempTile() != null)
		{
			Point old = GameHandler.instance.getMap().getTempPos();
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				Point next = new Point(old.x - 1, old.y);
				GameHandler.instance.getMap().setTempPos(next);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				Point next = new Point(old.x + 1, old.y);
				GameHandler.instance.getMap().setTempPos(next);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				Point next = new Point(old.x, old.y - 1);
				GameHandler.instance.getMap().setTempPos(next);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				Point next = new Point(old.x, old.y + 1);
				GameHandler.instance.getMap().setTempPos(next);
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				GameHandler.instance.getMap().getTempTile().rotateTile();
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
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
			}
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
}