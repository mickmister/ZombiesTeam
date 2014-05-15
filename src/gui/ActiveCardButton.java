package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import main.*;
import main.eventCardTypes.*;

public class ActiveCardButton extends JButton implements DataListener, ActionListener
{
	private int index;
	
	public ActiveCardButton(int index)
	{
		this.index = index;
		GameHandler.instance.addDataListener(this);
		addActionListener(this);
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		ArrayList<EventCard> cards = GameHandler.instance.getEventDeck().getActiveCardsForPlayer(player);
		if (cards.size() > this.index)
		{
			EventCard card = cards.get(this.index);
			setVisible(true);
			setText("<html><center>DISCARD<br>" + card.getName() + "<br>-----------------------<br>" + card.getDescription()); //$NON-NLS-1$ //$NON-NLS-2$
			boolean canDiscard = (card instanceof SingleUseDiscardable) || (card instanceof CustomUseDiscardable);
			setEnabled(canDiscard);
		}
		else
		{
			setVisible(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		eventCardClicked();
	}
	
	public void eventCardClicked()
	{
		Player player = ((Window) getTopLevelAncestor()).getPlayer();
		EventCard card = GameHandler.instance.getEventDeck().getActiveCardsForPlayer(player).get(this.index);
		if (checkGrenadeCard(card, player))
		{
			GameHandler.instance.getEventDeck().discard(card);
		}
	}
	
	private boolean checkGrenadeCard(EventCard card, Player player)
	{
		if (card instanceof GrenadeCard)
		{
			Point t = player.getTileLocation();
			Point c = player.getCellLocation();
			TileCell cell = GameHandler.instance.getMap().getMapTile(t.y, t.x).getCell(c.y, c.x);
			if (cell.isBuilding() || cell.isDoor())
			{
				return true;
			}
			else
			{
				DialogHandler.showMessage(getTopLevelAncestor(), "You are not inside of a building!", "Cannot Use Card", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		else
		{
			return true;
		}
	}
}