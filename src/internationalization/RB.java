package internationalization;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class RB
{
	private static String BUNDLE_NAME;
	private static Locale LOCALE;
	private static ResourceBundle RESOURCE_BUNDLE;
	
	static
	{
		new RB(new Locale("en", "US"));
	}
	
	public RB(Locale locale)
	{
		RB.BUNDLE_NAME = "internationalization.messages"; //$NON-NLS-1$
		RB.LOCALE = locale;
		RB.RESOURCE_BUNDLE = ResourceBundle.getBundle(RB.BUNDLE_NAME, RB.LOCALE);
	}
	
	public static String get(String key)
	{
		try
		{
			return RB.RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e)
		{
			return '!' + key + '!';
		}
	}
}