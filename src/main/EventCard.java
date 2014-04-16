package main;

public class EventCard
{
	
	public enum EventType
	{
		movement, zombieCombat, zombiePlacement, zombieMovement, whenCardPlayed, startOfTurn
	}
		
	private String name;
	private String description;
	private EventType type;
	public EventCard(String name, String description, EventType type)
	{
		this.name = name;
		this.description = description;
		this.type = type;
	}
	
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public EventType getType()
	{
		return this.type;
	}

}