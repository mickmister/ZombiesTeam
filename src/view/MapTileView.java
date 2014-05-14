package view;

import java.awt.*;

import main.*;
import main.MapTile.Shape;

public class MapTileView
{
	private MapTile mapTile;
	private String specialName;
	
	public MapTileView(MapTile tile)
	{
		this.mapTile = tile;
		this.specialName = "";
		if (Shape.special.equals(this.mapTile.getShape()))
		{
			this.specialName = hackFromEnum();
		}
	}
	
	public void draw(Graphics2D graphics, int xPos, int yPos, boolean isTemp)
	{
		for (int y = 0; y < 3; y += 1)
		{
			for (int x = 0; x < 3; x += 1)
			{
				int index = (yPos * 3 + y) * 10 * 3 + xPos * 3 + x;
				int cellX = xPos * 240 + 80 * x;
				int cellY = yPos * 240 + 80 * y;
				boolean tempZombie = new Point(x, y).equals(this.mapTile.getTempZombiePos());
				boolean tempBullet = new Point(x, y).equals(this.mapTile.getTempBulletPos());
				boolean tempLife = new Point(x, y).equals(this.mapTile.getTempLifePos());
				boolean tempMove = index == GameHandler.instance.getMap().getZombieMovementIndex();
				TileCellView view = new TileCellView(this.mapTile.getCell(y, x), this.mapTile);
				view.draw(graphics, cellX, cellY, isTemp, tempZombie, tempBullet, tempLife, tempMove);
			}
		}
		graphics.setColor(Color.BLACK);
		graphics.drawString(this.specialName, xPos * 240 + 11, yPos * 240 + 21);
		graphics.setColor(Color.WHITE);
		graphics.drawString(this.specialName, xPos * 240 + 10, yPos * 240 + 20);
		
		graphics.drawRect(xPos * 240, yPos * 240, 240, 240);
	}
	
	private String hackFromEnum()
	{
		char[] name = this.mapTile.getSpecialName().name().toCharArray();
		String output = "";
		output += Character.toUpperCase(name[0]);
		for (int i = 1; i < name.length; i += 1)
		{
			if (Character.isUpperCase(name[i]))
			{
				output += " ";
			}
			output += name[i];
		}
		return output;
	}
}