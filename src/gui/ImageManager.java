package gui;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public class ImageManager
{
	public static BufferedImage ROAD_TEXTURE;
	public static BufferedImage GRASS_TEXTURE;
	public static BufferedImage DIRT_TEXTURE;
	public static BufferedImage ZOMBIE_PICTURE;
	public static BufferedImage HEART_PICTURE;
	public static BufferedImage BULLET_PICTURE;
	public static BufferedImage ZOMBIE_ICON;
	public static BufferedImage HEART_ICON;
	public static BufferedImage BULLET_ICON;
	
	public ImageManager()
	{
		try
		{
			ImageManager.ROAD_TEXTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Road Texture.png"));
			ImageManager.GRASS_TEXTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Grass Texture.png"));
			ImageManager.DIRT_TEXTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Dirt Texture.png"));
			ImageManager.ZOMBIE_PICTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Zombie Picture.png"));
			ImageManager.HEART_PICTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Heart Picture.png"));
			ImageManager.BULLET_PICTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Bullet Picture.png"));
			ImageManager.ZOMBIE_ICON = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Zombie Icon.png"));
			ImageManager.HEART_ICON = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Heart Icon.png"));
			ImageManager.BULLET_ICON = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Bullet Icon.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}