package main;

import java.util.*;

/**
 * A TileCell represents one of the subsections within a MapTile, or one of the nine blocks. A
 * TileCell may or may not be accessible to players and a zombie. It may also have one life token
 * and/or one bullet token.
 * 
 * @author Donnie Waters, Jacob Ryan, and Coach. Created Mar 26, 2014.
 */
public class TileCell
{
	private boolean isAccessible;
	private boolean isBuilding;
	private boolean isDoor;
	private boolean hasZombie;
	private boolean hasLifeToken;
	private boolean hasBulletToken;
	private ArrayList<Player> playersOccupying;
	
	public TileCell(boolean accessible, boolean building, boolean door)
	{
		this.isAccessible = accessible;
		this.isBuilding = building;
		this.isDoor = door;
		this.hasZombie = false;
		this.hasLifeToken = false;
		this.hasBulletToken = false;
		this.playersOccupying = new ArrayList<Player>();
	}
	
	public boolean isRoad()
	{
		return this.isAccessible && !this.isBuilding && !this.isDoor;
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
	
	public boolean isAccessible()
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
	
	public void setAccessible(boolean accessible)
	{
		this.isAccessible = accessible;
	}
	
	public void removePlayer(Player player)
	{
		this.playersOccupying.remove(player);
	}
	
	public void addPlayer(Player player)
	{
		this.playersOccupying.add(player);
	}
	
	public ArrayList<Player> getPlayersOccupying()
	{
		return this.playersOccupying;
	}
}