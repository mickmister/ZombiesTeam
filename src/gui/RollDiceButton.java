package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.DataListener;
import main.GameHandler;
import main.GameHandler.GameState;
import main.Player;
import main.Window;

public class RollDiceButton extends JButton implements ActionListener, DataListener
{
	public RollDiceButton()
	{
		setText("Roll Dice");
		addActionListener(this);
		GameHandler.instance.addDataListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		rollDiceClicked();
	}
	
	public void rollDiceClicked()
	{
		GameHandler game = GameHandler.instance;
		if (game.getCurrentState() == GameState.playerMovementDieRoll || game.getCurrentState() == GameState.zombieMovementDieRoll)
		{
			int rollNum = (int) (Math.random() * 6 + 1);
			Player player = game.getPlayer(game.getTurn());
			player.setMovesRemaining(rollNum);
			JOptionPane.showMessageDialog(null, "You rolled a " + rollNum + "!");
			game.nextGameState();
		}
		
		if (game.getCurrentState() == GameState.zombieCombat)
		{
			int combatRoll = (int) (Math.random() * 6 + 1);
			JOptionPane.showMessageDialog(null, "Your combat roll was a " + combatRoll + "!");
			Player player = game.getPlayer(game.getTurn());
			player.setZombieCombatRoll(combatRoll);
			boolean win = player.fightZombie(game.getMap().getMapTile(player.getTileLocation().y, player.getTileLocation().x)
					.getCell(player.getCellLocation().y, player.getCellLocation().x));
			if (win)
			{
				game.nextGameState();
			}
		}
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		boolean canEnable = GameHandler.instance.getGuiStateData().rollDiceButtonEnabled;
		boolean myTurn = ((Window) getTopLevelAncestor()).getPlayer().isPlayersTurn();
		setEnabled(canEnable && myTurn);
	}
}