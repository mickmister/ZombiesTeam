package main;

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

	public void setActivator(Player player) {
		this.activator = player;		
	}
	
	public Player getActivator()
	{
		return this.activator;
	}
}