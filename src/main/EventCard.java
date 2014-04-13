package main;

public class EventCard
{
	private String name;
	private String description;
	private int count;
	public EventCard(String name, String description)
	{
		this.name = name;
		this.description = description;
		this.count = 0;
		initializeCount();
	}
	private void initializeCount() {
		//Massive code with name checking
		
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDescription()
	{
		return this.description;
	}

}