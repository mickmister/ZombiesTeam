package view;

import gui.ImageManager;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;

import main.GameHandler;
import main.Map;
import main.MapTile;
import main.MapTile.Shape;
import main.TileCell;

public class TileCellView
{
	private TileCell tileCell;
	private MapTile mapTile;
	
	public TileCellView(TileCell cell, MapTile tile)
	{
		this.tileCell = cell;
		this.mapTile = tile;
	}
	
	public void draw(Graphics2D g, int x, int y, boolean isTemp, boolean tempZombie, boolean tempBullet, boolean tempLife, boolean tempMove)
	{
		if (this.tileCell.isAccessible())
		{
			if (this.tileCell.isDoor())
			{
				g.drawImage(ImageManager.DOOR_TEXTURE, x, y, null);
			}
			else if (this.tileCell.isBuilding())
			{
				g.drawImage(ImageManager.BUILDING_TEXTURE, x, y, null);
			}
			else
			{
				g.drawImage(ImageManager.ROAD_TEXTURE, x, y, x + 80, y + 80, x, y, x + 80, y + 80, null);
			}
		}
		else
		{
			if (this.mapTile.getShape().equals(Shape.empty))
			{
				// Don't do anything, already have dirt background.
			}
			else
			{
				g.drawImage(ImageManager.GRASS_TEXTURE, x, y, null);
			}
		}
		
		if (this.tileCell.hasZombie() || tempZombie)
		{
			g.drawImage(ImageManager.ZOMBIE_ICON, x, y + 10, null);
		}
		if (this.tileCell.hasBulletToken() || tempBullet)
		{
			g.drawImage(ImageManager.BULLET_ICON, x + 35, y + 10, null);
		}
		if (this.tileCell.hasLifeToken() || tempLife)
		{
			g.drawImage(ImageManager.HEART_ICON, x + 35, y + 50, null);
		}
		for (int i = 0; i < this.tileCell.getPlayersOccupying().size(); i += 1)
		{
			g.setColor(Color.WHITE);
			g.fillRect(x + 60, y + 20 * i, 20, 20);
			g.setColor(Color.BLACK);
			int num = this.tileCell.getPlayersOccupying().get(i).getNumber() + 1;
			g.drawString("P" + num, x + 63, y + 20 * i + 15); //$NON-NLS-1$
		}
		
		if (isTemp || tempZombie || tempBullet || tempLife || tempMove)
		{
			Composite old = g.getComposite();
			double alpha = (Math.sin(System.currentTimeMillis() / 400.0) + 1.0) / 2.0;
			alpha = 0.25 * alpha + 0.25;
			int type = AlphaComposite.SRC_OVER;
			AlphaComposite composite = AlphaComposite.getInstance(type, (float) alpha);
			g.setComposite(composite);
			
			Map map = GameHandler.instance.getMap();
			if (isTemp)
			{
				if (map.checkValidPosition(map.getTempTile(), map.getTempPos().x, map.getTempPos().y))
				{
					g.setColor(Color.GREEN);
				}
				else
				{
					g.setColor(Color.RED);
				}
			}
			if (tempZombie)
			{
				MapTile zombieTile = map.getTempZombieTile();
				Point zombiePos = zombieTile.getTempZombiePos();
				if (zombieTile.getCell(zombiePos.y, zombiePos.x).hasZombie())
				{
					g.setColor(Color.RED);
				}
				else
				{
					g.setColor(Color.GREEN);
				}
			}
			if (tempBullet)
			{
				MapTile bulletTile = map.getTempBulletTile();
				Point bulletPos = bulletTile.getTempBulletPos();
				if (bulletTile.getCell(bulletPos.y, bulletPos.x).hasBulletToken())
				{
					g.setColor(Color.RED);
				}
				else
				{
					g.setColor(Color.GREEN);
				}
			}
			if (tempLife)
			{
				MapTile lifeTile = map.getTempBulletTile();
				Point lifePos = lifeTile.getTempLifePos();
				if (lifeTile.getCell(lifePos.y, lifePos.x).hasLifeToken())
				{
					g.setColor(Color.RED);
				}
				else
				{
					g.setColor(Color.GREEN);
				}
			}
			if (tempMove)
			{
				if (this.tileCell.hasZombieMoved())
				{
					g.setColor(Color.RED);
				}
				else
				{
					g.setColor(Color.GREEN);
				}
			}
			g.fillRect(x, y, 80, 80);
			
			g.setComposite(old);
		}
	}
}