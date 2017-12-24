/** a treasure, which can also act as a weapon */
public class JuJuTreasure {
	
	private String name;
	private String description;
	private int strength;
	
	/** constructor, takes name, description, and strength*/
	public JuJuTreasure(String name, String description, int strength) {
		//super();
		this.name = name;
		this.description = description;
		this.strength = strength;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param strength the strength to set
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	
	/** gets the name*/
	public String getName() {
		return name;
	}
	
	/** gets the description*/
	public String getDescription() {
		return description;
	}
	
	/** gets the strength*/
	public int getStrength() {
		return strength;
	}
	
}
