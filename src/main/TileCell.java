package main;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * TODO Put here a description of what this class does.
 *
 * @author watersdr.
 *         Created Mar 26, 2014.
 */
public class TileCell
{
	private MapTile mapTile;
	private boolean isAccessible;
	private boolean hasZombie;
	private boolean specialBuilding;
	private boolean lifeToken;
	private boolean bulletToken;
	private ArrayList<Player> playersOccupying;
	
	public TileCell(MapTile mapTile, boolean accessible, boolean specialBuilding) 
	{
		this.mapTile = mapTile;
		this.isAccessible = accessible;
		this.specialBuilding = specialBuilding;
		this.playersOccupying = new ArrayList<Player>();
		this.hasZombie = false;
		this.lifeToken = false;
		this.bulletToken = false;
	}
	
	public void draw(Graphics2D g, int x, int y, boolean isTemp)
	{
		if (this.isAccessible)
		{
			if (this.specialBuilding)
			{
				g.setColor(Color.RED);
			}
			else
			{
				g.setColor(Color.GRAY);
			}
		}
		else
		{
			g.setColor(new Color(0, 200, 0));
		}
		g.fillRect(x, y, 80, 80);
		
		if (this.hasZombie)
		{
			g.setColor(Color.BLACK);
			g.fillOval(0, 10, 20, 80 - 20);
		}
		
		if (isTemp)
		{
			Composite old = g.getComposite();
			float alpha = 0.25f;
			int type = AlphaComposite.SRC_OVER;
			AlphaComposite composite = AlphaComposite.getInstance(type, alpha);
			g.setComposite(composite);
			
			g.setColor(Color.RED);
			g.fillRect(x, y, 80, 80);
			
			g.setComposite(old);
		}
	}
	
	public boolean hasLifeToken() 
	{
		return this.lifeToken;
	}
	
	public boolean hasBulletToken() 
	{
		return this.bulletToken;
	}
	
	public boolean hasZombie() 
	{
		return this.hasZombie;
	}
	
	public void setLifeToken(boolean life)
	{
		this.lifeToken = life;
	}
	
	public void setBulletToken(boolean bullet)
	{
		this.bulletToken = bullet;
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
