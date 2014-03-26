import static org.junit.Assert.*;

import org.junit.Test;


/**
 * TODO Put here a description of what this class does.
 *
 * @author watersdr.
 *         Created Mar 26, 2014.
 */
public class MapTileTest {

	@Test
	public void testBlankTileCreation() {
		TileCell[][] array = MapTile.createBlankGrid();
		assertFalse(array[0][0] == null);
	}

}
