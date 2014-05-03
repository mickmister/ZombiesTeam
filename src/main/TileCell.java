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
	public enum CellType
	{
		grass, road, building, door
	}
	private CellType type;
	private boolean hasLifeToken;
	private boolean hasBulletToken;
	private boolean hasZombie;
	private boolean hasZombieMoved;
	private ArrayList<Player> playersOccupying;
	
	public TileCell(CellType type)
	{
		this.type = type;
		this.hasLifeToken = false;
		this.hasBulletToken = false;
		this.hasZombie = false;
		this.hasZombieMoved = false;
		this.playersOccupying = new ArrayList<Player>();
	}
	
	public boolean isRoad()
	{
		return CellType.road.equals(this.type);
	}
	
	public boolean isBuilding()
	{
		return CellType.building.equals(this.type);
	}
	
	public boolean isDoor()
	{
		return CellType.door.equals(this.type);
	}
	
	public boolean isAccessible()
	{
		return !CellType.grass.equals(this.type);
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
	
	public boolean hasZombieMoved()
	{
		return this.hasZombieMoved;
	}
	
	public void setCellType(CellType type)
	{
		this.type = type;
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
	
	public void setZombieMoved(boolean moved)
	{
		this.hasZombieMoved = moved;
	}
	
	public void addPlayer(Player player)
	{
		this.playersOccupying.add(player);
	}
	
	public void removePlayer(Player player)
	{
		this.playersOccupying.remove(player);
	}
	
	public ArrayList<Player> getPlayersOccupying()
	{
		return this.playersOccupying;
	}
	
	@Override
	public String toString()
	{
		return this.type.toString();
	}
}