package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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
			ROAD_TEXTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Road Texture.png"));
			GRASS_TEXTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Grass Texture.png"));
			DIRT_TEXTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Dirt Texture.png"));
			ZOMBIE_PICTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Zombie Picture.png"));
			HEART_PICTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Heart Picture.png"));
			BULLET_PICTURE = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Bullet Picture.png"));
			ZOMBIE_ICON = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Zombie Icon.png"));
			HEART_ICON = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Heart Icon.png"));
			BULLET_ICON = ImageIO.read(this.getClass().getResourceAsStream("../Resources/Bullet Icon.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}