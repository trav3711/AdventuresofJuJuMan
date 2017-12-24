/** your player, keeps track of its strength*/
public class Player {
	
	private int strength;
	private int xLocation;
	private int yLocation;
	private JuJuTreasure weapon;
	
	/** constructor, player starts off with a strength of 1*/
	public Player(int x, int y) {
		weapon = new JuJuTreasure("nothing", "nothing", 1);
		strength = 1;
		this.xLocation = x;
		this.yLocation = y;
	}

	/**
	 * @return the weapon
	 */
	public JuJuTreasure getWeapon() {
		return weapon;
	}

	/**
	 * @param weapon the weapon to set
	 */
	public void setWeapon(JuJuTreasure weapon) {
		this.weapon = weapon;
	}

	/**
	 * @return the xLocation
	 */
	public int getxLocation() {
		return xLocation;
	}

	/**
	 * @param xLocation the xLocation to set
	 */
	public void setxLocation(int xLocation) {
		this.xLocation = xLocation;
	}

	/**
	 * @return the yLocation
	 */
	public int getyLocation() {
		return yLocation;
	}

	/**
	 * @param yLocation the yLocation to set
	 */
	public void setyLocation(int yLocation) {
		this.yLocation = yLocation;
	}

	/** returns strength*/
	public int getStrength() {
		return strength;
	}

	/** sets strength
	 * 
	 * 	when the player encounters a treasure with greater strength than its
	 * current strength, then strength will be set to that of the new treasure
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}
	
}