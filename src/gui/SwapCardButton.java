package gui;

import internationalization.*;

import java.awt.event.*;

import javax.swing.*;

import main.*;

public class SwapCardButton extends JButton implements DataListener, ActionListener
{
	private int index;
	
	public SwapCardButton(int index)
	{
		this.index = index;
		setText(Messages.getString("SwapCardButton.swap")); //$NON-NLS-1$
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
							Messages.getString("SwapCardButton.swapped_message_prefix") + (this.index + 1) + Messages.getString("SwapCardButton.swapped_message_postfix"), Messages.getString("SwapCardButton.swapped_title"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							JOptionPane.INFORMATION_MESSAGE);
			
			player.drawNewCards();
			GameHandler.instance.fireDataChangedEvent(null);
			player.setCardPlayed(true);
		}
		else
		{
			DialogHandler.showMessage(getTopLevelAncestor(), Messages.getString("SwapCardButton.error_message"), //$NON-NLS-1$
					Messages.getString("SwapCardButton.error_title"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$
		}
	}
}