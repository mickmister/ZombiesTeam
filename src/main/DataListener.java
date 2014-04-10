package main;

public interface DataListener
{
	public enum DataChangedEvent
	{
	}
	
	public void dataChanged(DataChangedEvent e);
}