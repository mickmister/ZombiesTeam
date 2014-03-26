import java.util.ArrayList;


/**
 * TODO Put here a description of what this class does.
 *
 * @author watersdr.
 *         Created Mar 26, 2014.
 */
public class TileCell {
	private boolean isAccessible;
	private boolean hasZombie;
	private boolean specialBuilding;
	private boolean lifeToken;
	private boolean bulletToken;
	private ArrayList<Player> playersOccupying;
	
	public TileCell(boolean accessible, boolean specialBuilding) {
		this.isAccessible = accessible;
		this.specialBuilding = specialBuilding;
		this.playersOccupying = new ArrayList<Player>();
		this.hasZombie = false;
		this.lifeToken = false;
		this.bulletToken = false;
	}
	
	public boolean hasLifeToken() {
		return this.lifeToken;
	}
	
	public boolean hasBulletToken() {
		return this.bulletToken;
	}
	
	public boolean hasZombie() {
		return this.hasZombie;
	}
	
	public void setLifeToken(boolean life) {
		this.lifeToken = life;
	}
	
	public void setBulletToken(boolean bullet) {
		this.bulletToken = bullet;
	}
	
	public void setZombie(boolean zombie) {
		this.hasZombie = zombie;
	}
	
}
