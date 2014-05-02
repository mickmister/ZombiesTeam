package gui;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

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
		ImageManager.ROAD_TEXTURE = loadImage("Road Texture.png");
		ImageManager.GRASS_TEXTURE = loadImage("Grass Texture.png");
		ImageManager.DIRT_TEXTURE = loadImage("Dirt Texture.png");
		ImageManager.BUILDING_TEXTURE = loadImage("Building Texture.png");
		ImageManager.DOOR_TEXTURE = loadImage("Door Texture.png");
		ImageManager.ZOMBIE_PICTURE = loadImage("Zombie Picture.png");
		ImageManager.HEART_PICTURE = loadImage("Heart Picture.png");
		ImageManager.BULLET_PICTURE = loadImage("Bullet Picture.png");
		ImageManager.ZOMBIE_ICON = loadImage("Zombie Icon.png");
		ImageManager.HEART_ICON = loadImage("Heart Icon.png");
		ImageManager.BULLET_ICON = loadImage("Bullet Icon.png");
	}
	
	private BufferedImage loadImage(String name)
	{
		try
		{
			String fileName = "../Resources/" + name;
			return ImageIO.read(this.getClass().getResourceAsStream(fileName));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}