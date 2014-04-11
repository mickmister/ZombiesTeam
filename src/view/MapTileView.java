package view;

import java.awt.Graphics2D;
import java.awt.Point;

import main.MapTile;

public class MapTileView
{
	private MapTile mapTile;
	
	public MapTileView(MapTile tile)
	{
		this.mapTile = tile;
	}
	
	public void draw(Graphics2D graphics, int xPos, int yPos, boolean isTemp)
	{
		for (int y = 0; y < 3; y += 1)
		{
			for (int x = 0; x < 3; x += 1)
			{
				int cellX = xPos * 240 + 80 * x;
				int cellY = yPos * 240 + 80 * y;
				boolean tempZombie = new Point(x, y).equals(this.mapTile.getTempZombiePos());
				TileCellView view = new TileCellView(this.mapTile.getCell(y, x), this.mapTile);
				view.draw(graphics, cellX, cellY, isTemp, tempZombie);
			}
		}
	}
}