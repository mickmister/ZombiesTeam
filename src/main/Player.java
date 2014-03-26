package main;

/**
 * TODO Put here a description of what this class does.
 *
 * @author watersdr.
 *         Created Mar 26, 2014.
 */
public class Player {
	private int life;
	private int bullets;
	private int zombiesCaptured;
	
	public Player() {
		this.life = 3;
		this.bullets = 3;
		this.zombiesCaptured = 0;
	}
	
	public int getLifeTokens()
	{
		return this.life;
	}
	
	public int getBulletTokens()
	{
		return this.bullets;
	}
}
