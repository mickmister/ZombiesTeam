package main;

public class GuiStateData
{
	public boolean mapTileDeckButtonEnabled;
	public boolean rollDiceButtonEnabled;
	public boolean eventCardDeckButtonEnabled;
	
	public GuiStateData()
	{
		this.mapTileDeckButtonEnabled = true;
		this.rollDiceButtonEnabled = false;
		this.eventCardDeckButtonEnabled = false;
	}
}