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
	private TileCell[][] createQuad() {
		
		return null;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	private TileCell[][] createL() {
		// TODO Auto-generated method stub.
		return null;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	private TileCell[][] createStraight() {
	//	TileCell[][] 
		return null;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	private TileCell[][] createTetris() {
		// TODO Auto-generated method stub.
		return null;
	}
	
	public static TileCell[][] createBlankGrid() {
		TileCell[][] toReturn = new TileCell[3][3];
		
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				toReturn[i][j] = new TileCell(false, false);
			}
		}
		return toReturn;
	}
	
}
