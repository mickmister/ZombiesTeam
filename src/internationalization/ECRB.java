package internationalization;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ECRB
{
	private static String BUNDLE_NAME;
	private static Locale LOCALE;
	private static ResourceBundle RESOURCE_BUNDLE;
	
	static
	{
		new ECRB(new Locale("en", "US"));
	}
	
	public ECRB(Locale locale)
	{
		ECRB.BUNDLE_NAME = "internationalization.eventCardMessages"; //$NON-NLS-1$
		ECRB.LOCALE = locale;
		ECRB.RESOURCE_BUNDLE = ResourceBundle.getBundle(ECRB.BUNDLE_NAME, ECRB.LOCALE);
	}
	
	public static String get(String key)
	{
		try
		{
			return ECRB.RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e)
		{
			return '!' + key + '!';
		}
	}
}