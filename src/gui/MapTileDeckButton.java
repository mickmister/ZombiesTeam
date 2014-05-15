package gui;

import internationalization.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.DataListener;
import main.GameHandler;
import main.GameHandler.GameState;
import main.MapTile;

public class MapTileDeckButton extends JButton implements ActionListener, DataListener
{
	public MapTileDeckButton()
	{
		setText(Messages.getString("MapTileDeckButton.map_tile_deck")); //$NON-NLS-1$
		addActionListener(this);
		GameHandler.instance.addDataListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		mapTileDeckClicked();
	}
	
	public void mapTileDeckClicked()
	{
		if (((Window) getTopLevelAncestor()).getPlayer().isPlayersTurn())
		{
			if (GameHandler.instance.getCurrentState() == GameState.tilePlacement)
			{
				if (GameHandler.instance.getMap().getTempTile() == null)
				{
					MapTile tile = GameHandler.instance.getTileDeck().getNextCard();
					if (tile != null)
					{
						boolean possible = false;
						for (int x = 0; x < 11; x += 1)
						{
							for (int y = 0; y < 11; y += 1)
							{
								for (int r = 0; r < 4; r += 1)
								{
									if (GameHandler.instance.getMap().checkValidPosition(tile, x, y))
									{
										possible = true;
									}
									tile.rotateTile();
								}
							}
						}
						if (possible)
						{
							GameHandler.instance.getMap().addTempTile(tile);
						}
						else
						{
							DialogHandler
									.showMessage(
											null,
											Messages.getString("MapTileDeckButton.no_available_places_message"), Messages.getString("MapTileDeckButton.map_tile"), //$NON-NLS-1$ //$NON-NLS-2$
											JOptionPane.INFORMATION_MESSAGE);
							GameHandler.instance.nextGameState();
							GameHandler.instance.nextGameState();
						}
					}
					else
					{
						DialogHandler
								.showMessage(
										null,
										Messages.getString("MapTileDeckButton.no_more_cards_message"), Messages.getString("MapTileDeckButton.map_tile"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
						GameHandler.instance.nextGameState();
						GameHandler.instance.nextGameState();
					}
				}
			}
		}
	}
	
	@Override
	public void dataChanged(DataChangedEvent e)
	{
		boolean canEnable = GameHandler.instance.getGuiStateData().mapTileDeckButtonEnabled;
		boolean myTurn = ((Window) getTopLevelAncestor()).getPlayer().isPlayersTurn();
		setEnabled(canEnable && myTurn);
	}
}