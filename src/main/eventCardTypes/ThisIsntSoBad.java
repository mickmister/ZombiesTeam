package main.eventCardTypes;

import main.GameHandler;
import main.GameHandler.GameState;
import main.eventCardParents.StateChangeCard;

public class ThisIsntSoBad extends StateChangeCard
{

	public ThisIsntSoBad()
	{
		super(PossibleTarget.None, "This Isn't So Bad", "Move any 2 zombies in play to a random location on the map.");
	}

	@Override
	public int behavior(int num)
	{
		GameHandler game = GameHandler.instance;
		//Set up
		if (num == 0)
		{
			game.setGameState(GameState.zombieMovement);
			this.setTurn(game.getTurn());
			game.setTurn(getActivator().getNumber());
			getActivator().setMovesRemaining(2);
			return 1;
		}
		else if (num == 1)
		{
			return 1;
		}
		else
		{
			game.setGameState(GameState.tilePlacement);
			game.setTurn(this.getTurn());
			return 1;
		}
	}
	
}
