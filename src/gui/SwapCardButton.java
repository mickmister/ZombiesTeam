package gui;

import internationalization.RB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.DataListener;
import main.EventCard;
import main.GameHandler;
import main.Player;

public class SwapCardButton extends JButton implements DataListener, ActionListener
{
	private int index;
	
	public SwapCardButton(int index)
	{
		this.index = index;
		setText(RB.get("SwapCardButton.swap")); //$NON-NLS-1$
		GameHandler.instance.addDataListener(this);
		addActionListener(this);
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		EventCard card = player.getCardFromHand(this.index);
		if (card == null)
		{
			setEnabled(false);
		}
		else
		{
			setEnabled(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		swapCardClicked();
	}
	
	public void swapCardClicked()
	{
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		if (!player.checkCardPlayed())
		{
			player.removeCardFromHand(this.index);
			GameHandler.instance.fireDataChangedEvent(null);
			DialogHandler
					.showMessage(
							getTopLevelAncestor(),
							RB.get("SwapCardButton.swapped_message_prefix") + (this.index + 1) + RB.get("SwapCardButton.swapped_message_postfix"), RB.get("SwapCardButton.swapped_title"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							JOptionPane.INFORMATION_MESSAGE);
			
			player.drawNewCards();
			GameHandler.instance.fireDataChangedEvent(null);
			player.setCardPlayed(true);
		}
		else
		{
			DialogHandler.showMessage(getTopLevelAncestor(), RB.get("SwapCardButton.error_message"), //$NON-NLS-1$
					RB.get("SwapCardButton.error_title"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$
		}
	}
}