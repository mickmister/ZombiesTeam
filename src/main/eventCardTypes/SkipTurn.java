package main.eventCardTypes;

import main.*;

public class SkipTurn extends OneUseCard
{
	
	public SkipTurn()
	{
		super(PossibleTarget.Pick, "Skip Turn", "Target player's next turn will be skipped.");
	}
	
	@Override
	public int behavior(int num)
	{
		GameHandler.instance.nextTurn();
		return 1;
	}
	
}
