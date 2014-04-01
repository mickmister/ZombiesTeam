package main.tests;

import static org.junit.Assert.assertNotNull;
import main.Window;

import org.junit.Test;

public class WindowTest
{
	@Test
	public void testConstructor()
	{
		Window test = new Window(3);
		assertNotNull(test);
	}
}