/** holds the information for each of the monsters*/
public class JuJuMonster {

	private String description;
	private String name;
	private int strength;
	
	/** constructor, takes in name, description, number, and strength of the monster*/
	public JuJuMonster (String name, String description, int strength) {
		this.name = name;
		this.description = description;
		this.strength = strength;
	}
	
	/** gets the description*/
	public String getDescription() {
		return description;
	}
	
	/** gets the name*/
	public String getName() {
		return name;
	}
		
	/** gets the strength*/
	public int getStrength() {
		return strength;
	}
}