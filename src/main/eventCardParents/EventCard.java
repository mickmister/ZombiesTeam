package main.eventCardParents;

import main.GameHandler;
import main.MapTile;
import main.MapTileDeck.SpecialNames;
import main.Player;
import main.TileCell;

public abstract class EventCard
{
	public enum PossibleTarget
	{
		None, Self, Pick
	}
	
	private String name;
	private String description;
	private Player targetPlayer;
	private PossibleTarget posTar;
	private Player activator;
	
	public EventCard(PossibleTarget posTar, String name, String description)
	{
		this.posTar = posTar;
		this.name = name;
		this.description = description;
	}
	
	public PossibleTarget getPossibleTarget()
	{
		return this.posTar;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public Player getTargetPlayer()
	{
		return this.targetPlayer;
	}
	
	public void setTargetPlayer(Player target)
	{
		this.targetPlayer = target;
	}
	
	public int action(int num)
	{
		int result = behavior(num);
		checkRemove();
		return result;
	}
	
	public abstract int behavior(int num);
	
	public abstract void checkRemove();
	
	public void setActivator(Player player)
	{
		this.activator = player;
	}
	
	public Player getActivator()
	{
		return this.activator;
	}
	
	public boolean checkCorrectBuilding(Player player)
	{
		SpecialNames buildingName = null;
		if (this instanceof PlayUntilRevokedCard)
		{
			buildingName = ((PlayUntilRevokedCard) this).getBuildingName();
		}
		else if (this instanceof SingleUseDiscardable)
		{
			buildingName = ((SingleUseDiscardable) this).getBuildingName();
		}
		else if (this instanceof CustomUseDiscardable)
		{
			buildingName = ((CustomUseDiscardable) this).getBuildingName();
		}
		else
		{
			throw new IllegalArgumentException("Cannot check correct building on EventCard of type: " + this.getClass()); //$NON-NLS-1$
		}
		MapTile tile = GameHandler.instance.getMap().getMapTile(player.getTileLocation().y, player.getTileLocation().x);
		SpecialNames currentBuilding = tile.getSpecialName();
		TileCell cell = tile.getCell(player.getCellLocation().y, player.getCellLocation().x);
		boolean inBuilding = cell.isDoor() || cell.isBuilding();
		
		return buildingName.equals(currentBuilding) && inBuilding;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
}