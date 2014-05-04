package internationalization;

import java.util.*;

public class Messages
{
	private static String BUNDLE_NAME;
	private static Locale LOCALE;
	private static ResourceBundle RESOURCE_BUNDLE;
	
	public Messages(Locale locale)
	{
		Messages.BUNDLE_NAME = "internationalization.messages"; //$NON-NLS-1$
		Messages.LOCALE = locale;
		Messages.RESOURCE_BUNDLE = ResourceBundle.getBundle(Messages.BUNDLE_NAME, Messages.LOCALE);
	}
	
	public static String getString(String key)
	{
		try
		{
			return Messages.RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e)
		{
			return '!' + key + '!';
		}
	}
}
