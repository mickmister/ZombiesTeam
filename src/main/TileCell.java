package main;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;


/**
 * TODO Put here a description of what this class does.
 *
 * @author watersdr.
 *         Created Mar 26, 2014.
 */
public class TileCell extends JButton
{
	private boolean isAccessible;
	private boolean hasZombie;
	private boolean specialBuilding;
	private boolean lifeToken;
	private boolean bulletToken;
	private ArrayList<Player> playersOccupying;
	
	public TileCell(boolean accessible, boolean specialBuilding) 
	{
		this.isAccessible = accessible;
		this.specialBuilding = specialBuilding;
		this.playersOccupying = new ArrayList<Player>();
		this.hasZombie = false;
		this.lifeToken = false;
		this.bulletToken = false;
		
		setEnabled(this.isAccessible);
		setLayout(new BorderLayout());
		JLabel zombieLabel = new JLabel("Z");
		zombieLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		JLabel humanLabel = new JLabel("H");
		humanLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(zombieLabel, BorderLayout.LINE_START);
		add(humanLabel, BorderLayout.LINE_END);
	}
	
	public boolean hasLifeToken() 
	{
		return this.lifeToken;
	}
	
	public boolean hasBulletToken() 
	{
		return this.bulletToken;
	}
	
	public boolean hasZombie() 
	{
		return this.hasZombie;
	}
	
	public void setLifeToken(boolean life)
	{
		this.lifeToken = life;
	}
	
	public void setBulletToken(boolean bullet)
	{
		this.bulletToken = bullet;
	}
	
	public void setZombie(boolean zombie)
	{
		this.hasZombie = zombie;
	}
	
	public boolean getAcessible()
	{
		return this.isAccessible;
	}
	
	public void setAcessible(boolean accessible)
	{
		this.isAccessible = accessible;
		
		setEnabled(this.isAccessible);
	}
	
	public void playerEntered(Player player)
	{
		this.playersOccupying.add(player);
	}
	
	public void playerLeft(Player player)
	{
		this.playersOccupying.remove(player);
	}
	
	public ArrayList<Player> getOccupyingPlayers()
	{
		return this.playersOccupying;
	}
	
}
