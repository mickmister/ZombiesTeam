package tests;

import static org.junit.Assert.*;
import internationalization.RB;

import java.util.Locale;

import org.junit.Test;

public class RBTest
{
	@Test
	public void testDefaultLocale()
	{
		assertEquals("Number of Players", RB.get("Main.numPlayers_title"));
		assertEquals("!My_Fake_Key!", RB.get("My_Fake_Key"));
	}
	
	@Test
	public void testEnglishLocale()
	{
		new RB(new Locale("en", "US"));
		assertEquals("Number of Players", RB.get("Main.numPlayers_title"));
		assertEquals("!My_Fake_Key!", RB.get("My_Fake_Key"));
	}
	
	@Test
	public void testSpanishLocale()
	{
		new RB(new Locale("es", "MX"));
		assertEquals("Número de jugadores", RB.get("Main.numPlayers_title"));
		assertEquals("!My_Fake_Key!", RB.get("My_Fake_Key"));
	}
	
	@Test
	public void testFrenchLocale()
	{
		new RB(new Locale("fr", "FR"));
		assertEquals("Nombre de joueurs", RB.get("Main.numPlayers_title"));
		assertEquals("!My_Fake_Key!", RB.get("My_Fake_Key"));
	}
	
	@Test
	public void testFakeLocale()
	{
		new RB(new Locale("xy", "ABC"));
		assertEquals("Number of Players", RB.get("Main.numPlayers_title"));
		assertEquals("!My_Fake_Key!", RB.get("My_Fake_Key"));
	}
}