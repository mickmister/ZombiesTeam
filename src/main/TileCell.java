package main;

import gui.ImageManager;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.MapTile.Shape;

/**
 * A TileCell represents one of the subsections within a MapTile, or one of the nine blocks. A
 * TileCell may or may not be accessible to players and a zombie. It may also have one life token
 * and/or one bullet token.
 * 
 * @author Donnie Waters, Jacob Ryan, and Coach. Created Mar 26, 2014.
 */
public class TileCell
{
	private MapTile mapTile;
	private boolean isAccessible;
	private boolean hasZombie;
	private boolean specialBuilding;
	private boolean hasLifeToken;
	private boolean hasBulletToken;
	private ArrayList<Player> playersOccupying;
	
	public TileCell(MapTile mapTile, boolean accessible, boolean specialBuilding)
	{
		this.mapTile = mapTile;
		this.isAccessible = accessible;
		this.specialBuilding = specialBuilding;
		this.playersOccupying = new ArrayList<Player>();
		this.playersOccupying.add(new Player());
		this.hasZombie = true;
		this.hasLifeToken = true;
		this.hasBulletToken = true;
	}
	
	public void draw(Graphics2D g, int x, int y, boolean isTemp)
	{
		if (this.isAccessible)
		{
			if (this.specialBuilding)
			{
				g.setColor(Color.RED);
				g.fillRect(x, y, 80, 80);
			}
			else
			{
				g.drawImage(ImageManager.ROAD_TEXTURE, x, y, null);
			}
		}
		else
		{
			if (this.mapTile.getShape().equals(Shape.empty))
			{
				g.drawImage(ImageManager.DIRT_TEXTURE, x, y, null);
			}
			else
			{
				g.drawImage(ImageManager.GRASS_TEXTURE, x, y, null);
			}
		}
		
		if (this.hasZombie)
		{
			g.setColor(Color.BLACK);
			g.fillOval(x, y + 10, 20, 80 - 20);
		}
		if (this.hasLifeToken)
		{
			g.setColor(new Color(255, 100, 100));
			g.fillOval(x + 30, y + 10, 20, 20);
		}
		if (this.hasBulletToken)
		{
			g.setColor(new Color(255, 200, 0));
			g.fillOval(x + 30, y + 50, 20, 20);
		}
		for (Player player : this.playersOccupying)
		{
			int i = 1;
			g.setColor(Color.WHITE);
			g.fillRect(x + 58, y - 4 + 10 * i, 18, 18);
			g.setColor(Color.BLACK);
			g.drawString("P" + i, x + 60, y + 10 + 10 * i);
		}
		
		if (isTemp)
		{
			Composite old = g.getComposite();
			double alpha = (Math.sin(System.currentTimeMillis() / 400.0) + 1.0) / 2.0;
			alpha = 0.25 * alpha + 0.25;
			int type = AlphaComposite.SRC_OVER;
			AlphaComposite composite = AlphaComposite.getInstance(type, (float) alpha);
			g.setComposite(composite);
			
			Map map = GameHandler.instance.getMap();
			if (map.checkValidPosition(map.getTempTile(), map.getTempPos().x, map.getTempPos().y))
			{
				g.setColor(Color.GREEN);
			}
			else
			{
				g.setColor(Color.RED);
			}
			g.fillRect(x, y, 80, 80);
			
			g.setComposite(old);
		}
	}
	
	public boolean hasLifeToken()
	{
		return this.hasLifeToken;
	}
	
	public boolean hasBulletToken()
	{
		return this.hasBulletToken;
	}
	
	public boolean hasZombie()
	{
		return this.hasZombie;
	}
	
	public void setLifeToken(boolean life)
	{
		this.hasLifeToken = life;
	}
	
	public void setBulletToken(boolean bullet)
	{
		this.hasBulletToken = bullet;
	}
	
	public void setZombie(boolean zombie)
	{
		this.hasZombie = zombie;
	}
	
	public boolean getAcessible()
	{
		return this.isAccessible;
	}
	
	public void setAcessible(boolean accessible)
	{
		this.isAccessible = accessible;
	}
	
	public void playerEntered(Player player)
	{
		this.playersOccupying.add(player);
	}
	
	public void playerLeft(Player player)
	{
		this.playersOccupying.remove(player);
	}
	
	public ArrayList<Player> getOccupyingPlayers()
	{
		return this.playersOccupying;
	}
}