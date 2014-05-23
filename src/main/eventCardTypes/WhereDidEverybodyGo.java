package main.eventCardTypes;

import gui.DialogHandler;
import internationalization.ECRB;

import javax.swing.JOptionPane;

import main.GameHandler;
import main.GameHandler.GameState;
import main.eventCardParents.StateChangeCard;

public class WhereDidEverybodyGo extends StateChangeCard
{
	private boolean isSetUp;
	
	public WhereDidEverybodyGo()
	{
		super(PossibleTarget.Pick, ECRB.get("WhereDidEverybodyGo.name"), ECRB.get("WhereDidEverybodyGo.description")); //$NON-NLS-1$ //$NON-NLS-2$
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
		DialogHandler
				.showMessage(
						null,
						ECRB.get("WhereDidEverybodyGo.message_1") + (getActivator().getNumber() + 1) + ECRB.get("WhereDidEverybodyGo.message_2") + (getTargetPlayer().getNumber() + 1) + ECRB.get("WhereDidEverybodyGo.message_3"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
		DialogHandler.showMessage(null, ECRB.get("WhereDidEverybodyGo.message_4"), getName(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		GameHandler.instance.getGuiStateData().mapTileDeckButtonEnabled = true;
		GameHandler.instance.getGuiStateData().rollDiceButtonEnabled = false;
		GameHandler.instance.setGameState(GameState.tilePlacement);
		GameHandler.instance.setTurn(getTurn());
		checkRemove();
		GameHandler.instance.fireDataChangedEvent(null);
		return 42;
	}
}