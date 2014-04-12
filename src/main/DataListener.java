package main;

public interface DataListener
{
	public enum DataChangedEvent
	{
		test
	}
	
	public void dataChanged(DataChangedEvent e);
}