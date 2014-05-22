package main.eventCardTypes;

import gui.DialogHandler;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.GameHandler.GameState;
import main.eventCardParents.StateChangeCard;

public class WhereDidEverybodyGo extends StateChangeCard
{
	private boolean isSetUp;
	
	public WhereDidEverybodyGo()
	{
		super(PossibleTarget.Pick, "Where Did Everybody Go?", "Play this card at any time to move target opponent 5 spaces.  All zombies encountered must be fought as normal.");
		this.isSetUp = false;
	}

	@Override
	public int behavior(int num)
	{
		if (num == 0)
		{
			return setUp();
		}
		else if (num == 1)
		{
			return isActive();
		}
		else
		{
			return restore();
		}
	}
	
	private int setUp()
	{
		DialogHandler.showMessage(null, "It is now player " + (getActivator().getNumber() + 1) + "'s turn to move player " + (getTargetPlayer().getNumber() + 1) + " five spaces.", getName(), JOptionPane.INFORMATION_MESSAGE);
		setTurn(GameHandler.instance.getTurn());
		getTargetPlayer().setMovesRemaining(5);
		GameHandler.instance.getGuiStateData().mapTileDeckButtonEnabled = false;
		GameHandler.instance.getGuiStateData().rollDiceButtonEnabled = false;
		GameHandler.instance.setTurn(getTargetPlayer().getNumber());
		GameHandler.instance.gotoCombatOrMoveState();
		GameHandler.instance.fireDataChangedEvent(null);
		this.isSetUp = true;
		return 42;
	}
	
	private int isActive()
	{
		if (this.isSetUp)
		{
			return 42;
		}
		else
		{
			return 0;
		}
	}
	
	private int restore()
	{
		DialogHandler.showMessage(null, "Returning to the normal player's turn.", getName(), JOptionPane.INFORMATION_MESSAGE);
		GameHandler.instance.getGuiStateData().mapTileDeckButtonEnabled = true;
		GameHandler.instance.getGuiStateData().rollDiceButtonEnabled = false;
		GameHandler.instance.setGameState(GameState.tilePlacement);
		GameHandler.instance.setTurn(getTurn());
		checkRemove();
		GameHandler.instance.fireDataChangedEvent(null);
		return 42;
	}
}