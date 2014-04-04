package main;

import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JOptionPane;

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
		}
	}
	
	private void processSpecialString(String string)
	{
		try
		{
			this.grid = createBlankGrid();
			String[] words = string.split(" ");
			for (int y = 0; y < 3; y += 1)
			{
				for (int x = 0; x < 3; x += 1)
				{
					int index = y * 3 + x;
					int number = Integer.parseInt(words[index]);
					boolean accessible = number > 0;
					boolean building = number > 1;
					boolean door = number > 2;
					this.grid[y][x] = new TileCell(this, accessible, building, door);
				}
			}
			this.zombiesToPlace = Integer.parseInt(words[9]);
			this.lifeTokens = Integer.parseInt(words[10]);
			this.bulletTokens = Integer.parseInt(words[11]);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The special string could not be parsed for this MapTile:\n\"" + string + "\"", "Error Parsing",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Shape getShape()
	{
		return this.shape;
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
	
	public void draw(Graphics2D graphics, int xPos, int yPos, boolean isTemp)
	{
		for (int y = 0; y < 3; y += 1)
		{
			for (int x = 0; x < 3; x += 1)
			{
				int cellX = xPos * 240 + 80 * x;
				int cellY = yPos * 240 + 80 * y;
				boolean tempZombie = new Point(x, y).equals(this.tempZombiePos);
				this.grid[y][x].draw(graphics, cellX, cellY, isTemp, tempZombie);
			}
		}
	}
	
	/**
	 * Creates a grid of TileCells where all normal sub-blocks are accessible (north, south, east,
	 * west, and center).
	 * 
	 * @return 2-D array of TileCells
	 */
	public TileCell[][] createQuad()
	{
		TileCell[][] quadGrid = createBlankGrid();
		
		quadGrid[0][1].setAccessible(true); // Top Middle
		quadGrid[1][0].setAccessible(true); // Middle Left
		quadGrid[1][1].setAccessible(true); // Middle Middle
		quadGrid[1][2].setAccessible(true); // Middle Right
		quadGrid[2][1].setAccessible(true); // Bottom Middle
		
		return quadGrid;
	}
	
	/**
	 * Creates a grid of TileCells where the L shape of sub-blocks are accessible (south, center,
	 * and east).
	 * 
	 * @return 2-D array of TileCells
	 */
	public TileCell[][] createL()
	{
		TileCell[][] LGrid = createBlankGrid();
		
		LGrid[1][1].setAccessible(true); // Middle Middle
		LGrid[1][2].setAccessible(true); // Middle Right
		LGrid[2][1].setAccessible(true); // Bottom Middle
		
		return LGrid;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @return
	 */
	public TileCell[][] createStraight()
	{
		TileCell[][] straightGrid = createBlankGrid();
		
		straightGrid[1][0].setAccessible(true); // Middle Left
		straightGrid[1][1].setAccessible(true); // Middle Middle
		straightGrid[1][2].setAccessible(true); // Middle Right
		
		return straightGrid;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @return
	 */
	public TileCell[][] createTetris()
	{
		TileCell[][] tetrisGrid = createBlankGrid();
		
		tetrisGrid[1][0].setAccessible(true); // Middle Left
		tetrisGrid[1][1].setAccessible(true); // Middle Middle
		tetrisGrid[1][2].setAccessible(true); // Middle Right
		tetrisGrid[2][1].setAccessible(true); // Bottom Middle
		
		return tetrisGrid;
	}
	
	/**
	 * @return
	 */
	public TileCell[][] createBlankGrid()
	{
		TileCell[][] toReturn = new TileCell[3][3];
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				toReturn[i][j] = new TileCell(this, false, false, false);
			}
		}
		return toReturn;
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
	
	@Override
	public String toString()
	{
		String result = "";
		
		result += this.grid[0][0].isAccessible() + " ";
		result += this.grid[0][1].isAccessible() + " ";
		result += this.grid[0][2].isAccessible() + "\n";
		
		result += this.grid[1][0].isAccessible() + " ";
		result += this.grid[1][1].isAccessible() + " ";
		result += this.grid[1][2].isAccessible() + "\n";
		
		result += this.grid[2][0].isAccessible() + " ";
		result += this.grid[2][1].isAccessible() + " ";
		result += this.grid[2][2].isAccessible() + "\n";
		
		return result;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param point
	 */
	public void setTempZombiePos(Point point)
	{
		if (point.x >= 0 && point.y >= 0 && point.x <= 2 && point.y <= 2)
		{
			if (this.grid[point.y][point.x].isAccessible())
			{
				this.tempZombiePos = point;
			}
		}
	}
	
	public Point getTempZombiePos()
	{
		return this.tempZombiePos;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 */
	public void placeTempZombie()
	{
		TileCell cell = this.grid[this.tempZombiePos.y][this.tempZombiePos.x];
		if (!cell.hasZombie())
		{
			cell.setZombie(true);
			this.zombiesToPlace--;
			if (this.zombiesToPlace == 0)
			{
				GameHandler.instance.nextGameState();
			}
			else
			{
				this.tempZombiePos = new Point(1, 1);
			}
		}
	}
}