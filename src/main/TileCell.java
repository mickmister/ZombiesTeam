package main;

import gui.ImageManager;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
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
	private boolean isBuilding;
	private boolean isDoor;
	private boolean hasZombie;
	private boolean hasLifeToken;
	private boolean hasBulletToken;
	
	public TileCell(MapTile mapTile, boolean accessible, boolean building, boolean door)
	{
		this.mapTile = mapTile;
		this.isAccessible = accessible;
		this.isBuilding = building;
		this.isDoor = door;
		this.hasZombie = false;
		this.hasLifeToken = false;
		this.hasBulletToken = false;
	}
	
	public void draw(Graphics2D g, int x, int y, boolean isTemp)
	{
		if (this.isAccessible)
		{
			if (this.isDoor)
			{
				g.setColor(Color.BLUE);
				g.fillRect(x, y, 80, 80);
			}
			else if (this.isBuilding)
			{
				g.setColor(Color.YELLOW);
				g.fillRect(x, y, 80, 80);
			}
			else
			{
				g.drawImage(ImageManager.ROAD_TEXTURE, x, y, x + 80, y + 80, x, y, x + 80, y + 80, null);
			}
		}
		else
		{
			if (this.mapTile.getShape().equals(Shape.empty))
			{
				// Don't do anything, already have dirt background.
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
	
	public boolean isAcessible()
	{
		return this.isAccessible;
	}
	
	public boolean isBuilding()
	{
		return this.isBuilding;
	}
	
	public boolean isDoor()
	{
		return this.isDoor;
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
	
	public void setAcessible(boolean accessible)
	{
		this.isAccessible = accessible;
	}
}