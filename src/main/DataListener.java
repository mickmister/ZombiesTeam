package main;

public interface DataListener
{
	public enum DataChangedEvent {a, b, c}
	
	public void dataChanged(DataChangedEvent e);
}