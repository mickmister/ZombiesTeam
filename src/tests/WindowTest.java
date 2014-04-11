package tests;

import static org.junit.Assert.assertNotNull;
import gui.ImageManager;
import main.Window;

import org.junit.Test;

public class WindowTest
{
	@Test
	public void testConstructor()
	{
		new ImageManager();
		Window test = new Window(3);
		assertNotNull(test);
	}
}