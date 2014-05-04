package main;

import gui.*;
import internationalization.*;

import java.awt.*;

import javax.swing.*;

import main.TileCell.CellType;

/**
 * A MapTile represents one tile that is placed on the Map. It is a square with 9 sub-blocks
 * (TileCells), and each has different patterns of sub-blocks that are accessible (roads) and
 * buildings. Each turn, a player will place a new MapTile down and can rotate it so that the roads
 * match up to adjacent MapTiles. Special building tiles can contain a bullet token, life token, and
 * zombies.
 * 
 * @author Donnie Waters, Jacob Ryan, and Coach. Created Mar 26, 2014.
 */
public class MapTile
{
	private Shape shape;
	private int zombiesToPlace;
	private int lifeTokens;
	private int bulletTokens;
	private Point tempZombiePos;
	private Point tempBulletPos;
	private Point tempLifePos;
	
	public enum Shape
	{
		straight, T, L, quad, empty, special
	}
	
	// 2D array organized as [row, column].
	private TileCell[][] grid;
	
	public MapTile(Shape shape, String special)
	{
		this.shape = shape;
		this.tempZombiePos = null;
		this.tempBulletPos = null;
		this.tempLifePos = null;
		switch (shape)
		{
			case T:
				this.grid = createTetris();
				this.zombiesToPlace = 3;
				break;
			case straight:
				this.grid = createStraight();
				this.zombiesToPlace = 2;
				break;
			case L:
				this.grid = createL();
				this.zombiesToPlace = 2;
				break;
			case quad:
				this.grid = createQuad();
				this.zombiesToPlace = 4;
				break;
			case empty:
				this.grid = createBlankGrid();
				this.zombiesToPlace = 0;
				break;
			case special:
				processSpecialString(special);
				break;
			default:
				throw new IllegalArgumentException(Messages.getString("MapTile.invalid_shape_type") + shape); //$NON-NLS-1$
		}
	}
	
	private void processSpecialString(String string)
	{
		try
		{
			this.grid = createBlankGrid();
			String[] words = string.split(" "); //$NON-NLS-1$
			for (int y = 0; y < 3; y += 1)
			{
				for (int x = 0; x < 3; x += 1)
				{
					int index = y * 3 + x;
					int number = Integer.parseInt(words[index]);
					CellType type;
					switch (number)
					{
						case 0:
							type = CellType.grass;
							break;
						case 1:
							type = CellType.road;
							break;
						case 2:
							type = CellType.building;
							break;
						case 3:
							type = CellType.door;
							break;
						default:
							throw new NumberFormatException();
					}
					this.grid[y][x] = new TileCell(type);
				}
			}
			this.zombiesToPlace = Integer.parseInt(words[9]);
			this.lifeTokens = Integer.parseInt(words[10]);
			this.bulletTokens = Integer.parseInt(words[11]);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			DialogHandler.showMessage(null, Messages.getString("MapTile.could_not_parse_special_string") + string + "\"", Messages.getString("MapTile.error_parsing"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Shape getShape()
	{
		return this.shape;
	}
	
	public int getZombiesToPlace()
	{
		return this.zombiesToPlace;
	}
	
	public int getBulletsToPlace()
	{
		return this.bulletTokens;
	}
	
	public int getLifeToPlace()
	{
		return this.lifeTokens;
	}
	
	public TileCell getLeftCell()
	{
		return this.grid[1][0];
	}
	
	public TileCell getRightCell()
	{
		return this.grid[1][2];
	}
	
	public TileCell getTopCell()
	{
		return this.grid[0][1];
	}
	
	public TileCell getBottomCell()
	{
		return this.grid[2][1];
	}
	
	public TileCell getCell(int row, int col)
	{
		return this.grid[row][col];
	}
	
	/**
	 * Creates a grid of TileCells where all normal sub-blocks are accessible (north, south, east,
	 * west, and center).
	 * 
	 * @return 2-D array of TileCells
	 */
	public TileCell[][] createQuad()
	{
		TileCell[][] grid = createBlankGrid();
		
		grid[0][1].setCellType(CellType.road); // Top Middle
		grid[1][0].setCellType(CellType.road); // Middle Left
		grid[1][1].setCellType(CellType.road); // Middle Middle
		grid[1][2].setCellType(CellType.road); // Middle Right
		grid[2][1].setCellType(CellType.road); // Bottom Middle
		
		return grid;
	}
	
	public TileCell[][] createL()
	{
		TileCell[][] grid = createBlankGrid();
		
		grid[1][1].setCellType(CellType.road); // Middle Middle
		grid[1][2].setCellType(CellType.road); // Middle Right
		grid[2][1].setCellType(CellType.road); // Bottom Middle
		
		return grid;
	}
	
	public TileCell[][] createStraight()
	{
		TileCell[][] grid = createBlankGrid();
		
		grid[1][0].setCellType(CellType.road); // Middle Left
		grid[1][1].setCellType(CellType.road); // Middle Middle
		grid[1][2].setCellType(CellType.road); // Middle Right
		
		return grid;
	}
	
	public TileCell[][] createTetris()
	{
		TileCell[][] grid = createBlankGrid();
		
		grid[1][0].setCellType(CellType.road); // Middle Left
		grid[1][1].setCellType(CellType.road); // Middle Middle
		grid[1][2].setCellType(CellType.road); // Middle Right
		grid[2][1].setCellType(CellType.road); // Bottom Middle
		
		return grid;
	}
	
	public TileCell[][] createBlankGrid()
	{
		TileCell[][] grid = new TileCell[3][3];
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				grid[i][j] = new TileCell(CellType.grass);
			}
		}
		return grid;
	}
	
	/**
	 * Rotates a tile counter-clockwise 90 degrees
	 */
	public void rotateTile()
	{
		TileCell bottomLeftCorner = this.grid[2][0];
		
		this.grid[2][0] = this.grid[0][0];
		this.grid[0][0] = this.grid[0][2];
		this.grid[0][2] = this.grid[2][2];
		this.grid[2][2] = bottomLeftCorner;
		
		TileCell middleLeftEdge = this.grid[1][0];
		
		this.grid[1][0] = this.grid[0][1];
		this.grid[0][1] = this.grid[1][2];
		this.grid[1][2] = this.grid[2][1];
		this.grid[2][1] = middleLeftEdge;
	}
	
	public Point getTempZombiePos()
	{
		return this.tempZombiePos;
	}
	
	public Point getTempBulletPos()
	{
		return this.tempBulletPos;
	}
	
	public Point getTempLifePos()
	{
		return this.tempLifePos;
	}
	
	public void setTempZombiePos(Point point)
	{
		if (point.x >= 0 && point.y >= 0 && point.x < 3 && point.y < 3)
		{
			if (this.grid[point.y][point.x].isAccessible())
			{
				this.tempZombiePos = point;
			}
		}
	}
	
	public void setTempBulletPos(Point point)
	{
		if (point.x >= 0 && point.y >= 0 && point.x < 3 && point.y < 3)
		{
			if (this.grid[point.y][point.x].isAccessible())
			{
				this.tempBulletPos = point;
			}
		}
	}
	
	public void setTempLifePos(Point point)
	{
		if (point.x >= 0 && point.y >= 0 && point.x < 3 && point.y < 3)
		{
			if (this.grid[point.y][point.x].isAccessible())
			{
				this.tempLifePos = point;
			}
		}
	}
	
	public void placeTempZombie()
	{
		TileCell cell = this.grid[this.tempZombiePos.y][this.tempZombiePos.x];
		if (!cell.hasZombie())
		{
			cell.setZombie(true);
			this.zombiesToPlace--;
			if (this.zombiesToPlace == 0)
			{
				this.tempZombiePos = null;
				GameHandler.instance.nextGameState();
			}
			else
			{
				this.tempZombiePos = new Point(1, 1);
			}
		}
	}
	
	public void placeTempBullet()
	{
		TileCell cell = this.grid[this.tempBulletPos.y][this.tempBulletPos.x];
		if (!cell.hasBulletToken())
		{
			cell.setBulletToken(true);
			this.bulletTokens--;
			if (this.bulletTokens == 0)
			{
				this.tempBulletPos = null;
				GameHandler.instance.nextGameState();
			}
			else
			{
				this.tempBulletPos = new Point(1, 1);
			}
		}
	}
	
	public void placeTempLife()
	{
		TileCell cell = this.grid[this.tempLifePos.y][this.tempLifePos.x];
		if (!cell.hasLifeToken())
		{
			cell.setLifeToken(true);
			this.lifeTokens--;
			if (this.lifeTokens == 0)
			{
				this.tempLifePos = null;
				GameHandler.instance.nextGameState();
			}
			else
			{
				this.tempLifePos = new Point(1, 1);
			}
		}
	}
	
	@Override
	public String toString()
	{
		String result = Messages.getString("MapTile.map_tile") + this.shape.toString() + Messages.getString("MapTile.shape"); //$NON-NLS-1$ //$NON-NLS-2$
		
		for (int y = 0; y < 3; y += 1)
		{
			result += "[ "; //$NON-NLS-1$
			for (int x = 0; x < 3; x += 1)
			{
				result += this.grid[y][x].toString() + "\t"; //$NON-NLS-1$
			}
			result += "]\n"; //$NON-NLS-1$
		}
		
		return result;
	}
}