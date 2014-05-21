package main.eventCardParents;

public abstract class StateChangeCard extends MultipleUseCard
{
	private int turn;
	
	public StateChangeCard(PossibleTarget posTar, String name, String description)
	{
		super(posTar, name, description, 2);
	}
	
	@Override
	public abstract int behavior(int num);
	
	public int getTurn()
	{
		return this.turn;
	}
	
	public void setTurn(int turn)
	{
		this.turn = turn;
	}
}