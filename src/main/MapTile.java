package main;

/**
 * TODO Put here a description of what this class does.
 *
 * @author watersdr.
 *         Created Mar 26, 2014.
 */
public class MapTile {
	
	private TileCell[][] grid;
	
	public MapTile(String shape, String special) {
		switch(shape) {
		case "tetris":
			this.grid = createTetris();
			break;
		case "straight":
			this.grid = createStraight();
			break;
		case "L":
			this.grid = createL();
			break;
		case "quad":
			this.grid = createQuad();
		}
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public TileCell[][] createQuad() {
		TileCell[][] quadGrid = createBlankGrid();
		
		quadGrid[0][1].setAcessible(true);	//Top Middle
		quadGrid[1][0].setAcessible(true);	//Middle Left
		quadGrid[1][1].setAcessible(true);	//Middle Middle
		quadGrid[1][2].setAcessible(true);	//Middle Right
		quadGrid[2][1].setAcessible(true);	//Bottom Middle
		
		return quadGrid;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public TileCell[][] createL() {
		TileCell[][] LGrid = createBlankGrid();
		
		LGrid[1][1].setAcessible(true);	//Middle Middle
		LGrid[1][2].setAcessible(true);	//Middle Right
		LGrid[2][1].setAcessible(true);	//Bottom Middle
		
		return LGrid;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public TileCell[][] createStraight() {
		TileCell[][] straightGrid = createBlankGrid();
		
		straightGrid[1][0].setAcessible(true);	//Middle Left
		straightGrid[1][1].setAcessible(true);	//Middle Middle
		straightGrid[1][2].setAcessible(true);	//Middle Right
		
		return straightGrid;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public TileCell[][] createTetris() {
		TileCell[][] tetrisGrid = createBlankGrid();
		
		tetrisGrid[1][0].setAcessible(true);	//Middle Left
		tetrisGrid[1][1].setAcessible(true);	//Middle Middle
		tetrisGrid[1][2].setAcessible(true);	//Middle Right
		tetrisGrid[2][1].setAcessible(true);	//Bottom Middle
		
		return tetrisGrid;
	}
	
	/**
	 * 
	 * @return
	 */
	public static TileCell[][] createBlankGrid() {
		TileCell[][] toReturn = new TileCell[3][3];
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				toReturn[i][j] = new TileCell(false, false);
			}
		}
		return toReturn;
	}
	
	/**
	 * Rotates a tile counter-clockwise 90 degrees
	 */
	public void rotateTile(){
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
	public String toString(){
		String result = "";
		
		result += this.grid[0][0].getAcessible() + " ";
		result += this.grid[0][1].getAcessible() + " ";
		result += this.grid[0][2].getAcessible() + "\n";
		
		result += this.grid[1][0].getAcessible() + " ";
		result += this.grid[1][1].getAcessible() + " ";
		result += this.grid[1][2].getAcessible() + "\n";
		
		result += this.grid[2][0].getAcessible() + " ";
		result += this.grid[2][1].getAcessible() + " ";
		result += this.grid[2][2].getAcessible() + "\n";
		
		return result;
	}
	
}
