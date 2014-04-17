package main.eventCardTypes;

import main.GameHandler;
import main.GameHandler.GameState;

public class AdrenalineRush extends OneUseCard {

	public AdrenalineRush()
	{
		super(PossibleTarget.Self, "Adrenaline Rush", "You can move a lot now!");
	}

	@Override
	public int behavior(int num) {
		GameHandler game = GameHandler.instance;
		if(game.getCurrentState() == GameState.playerMovementDieRoll){
			return num * 2;
		}
		else
		{
			return num + 2;
		}
	}

}
