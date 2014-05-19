package gui;

import internationalization.RB;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.DataListener;
import main.GameHandler;
import main.Player;
import main.TileCell;
import main.eventCardParents.CustomUseDiscardable;
import main.eventCardParents.EventCard;
import main.eventCardParents.SingleUseDiscardable;
import main.eventCardTypes.Grenade;

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
			setText("<html><center>DISCARD:<hr><b>" + card.getName() + "</b><hr>" + card.getDescription()); //$NON-NLS-1$ //$NON-NLS-2$
			boolean canDiscard = card instanceof SingleUseDiscardable || card instanceof CustomUseDiscardable;
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
		GameHandler.instance.fireDataChangedEvent(null);
	}
	
	private boolean checkGrenadeCard(EventCard card, Player player)
	{
		if (card instanceof Grenade)
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
				DialogHandler.showMessage(getTopLevelAncestor(),
						RB.get("ActiveCardButton.grenade_error_message"), RB.get("ActiveCardButton.grenade_error_title"), //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		else
		{
			return true;
		}
	}
}