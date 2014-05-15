package main.eventCardTypes;

import java.util.*;

public class Messages
{
	private static final String BUNDLE_NAME = "internationalization.messages"; //$NON-NLS-1$
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Messages.BUNDLE_NAME);
	
	private Messages()
	{
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