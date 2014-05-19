package tests;

import static org.junit.Assert.assertEquals;
import internationalization.ECRB;

import java.util.Locale;

import org.junit.Test;

public class ECRBTest
{
	@Test
	public void testDefaultLocale()
	{
		assertEquals("All the Marbles", ECRB.get("AllTheMarbles.name"));
		assertEquals("!My_Fake_Key!", ECRB.get("My_Fake_Key"));
	}
	
	@Test
	public void testEnglishLocale()
	{
		new ECRB(new Locale("en", "US"));
		assertEquals("All the Marbles", ECRB.get("AllTheMarbles.name"));
		assertEquals("!My_Fake_Key!", ECRB.get("My_Fake_Key"));
	}
	
	@Test
	public void testSpanishLocale()
	{
		new ECRB(new Locale("es", "MX"));
		assertEquals("All the Marbles", ECRB.get("AllTheMarbles.name"));
		assertEquals("!My_Fake_Key!", ECRB.get("My_Fake_Key"));
	}
	
	@Test
	public void testFrenchLocale()
	{
		new ECRB(new Locale("fr", "FR"));
		assertEquals("All the Marbles", ECRB.get("AllTheMarbles.name"));
		assertEquals("!My_Fake_Key!", ECRB.get("My_Fake_Key"));
	}
	
	@Test
	public void testFakeLocale()
	{
		new ECRB(new Locale("xy", "ABC"));
		assertEquals("All the Marbles", ECRB.get("AllTheMarbles.name"));
		assertEquals("!My_Fake_Key!", ECRB.get("My_Fake_Key"));
	}
}