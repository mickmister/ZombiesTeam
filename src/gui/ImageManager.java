package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager
{
	public static BufferedImage ROAD_TEXTURE;
	public static BufferedImage GRASS_TEXTURE;
	public static BufferedImage DIRT_TEXTURE;
	public static BufferedImage BUILDING_TEXTURE;
	public static BufferedImage DOOR_TEXTURE;
	public static BufferedImage ZOMBIE_PICTURE;
	public static BufferedImage HEART_PICTURE;
	public static BufferedImage BULLET_PICTURE;
	public static BufferedImage ZOMBIE_ICON;
	public static BufferedImage HEART_ICON;
	public static BufferedImage BULLET_ICON;
	
	public ImageManager()
	{
		ImageManager.ROAD_TEXTURE = loadImage("Road Texture.png"); //$NON-NLS-1$
		ImageManager.GRASS_TEXTURE = loadImage("Grass Texture.png"); //$NON-NLS-1$
		ImageManager.DIRT_TEXTURE = loadImage("Dirt Texture.png"); //$NON-NLS-1$
		ImageManager.BUILDING_TEXTURE = loadImage("Building Texture.png"); //$NON-NLS-1$
		ImageManager.DOOR_TEXTURE = loadImage("Door Texture.png"); //$NON-NLS-1$
		ImageManager.ZOMBIE_PICTURE = loadImage("Zombie Picture.png"); //$NON-NLS-1$
		ImageManager.HEART_PICTURE = loadImage("Heart Picture.png"); //$NON-NLS-1$
		ImageManager.BULLET_PICTURE = loadImage("Bullet Picture.png"); //$NON-NLS-1$
		ImageManager.ZOMBIE_ICON = loadImage("Zombie Icon.png"); //$NON-NLS-1$
		ImageManager.HEART_ICON = loadImage("Heart Icon.png"); //$NON-NLS-1$
		ImageManager.BULLET_ICON = loadImage("Bullet Icon.png"); //$NON-NLS-1$
	}
	
	private BufferedImage loadImage(String name)
	{
		try
		{
			String fileName = "../Resources/" + name; //$NON-NLS-1$
			return ImageIO.read(this.getClass().getResourceAsStream(fileName));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}