import java.util.ArrayList;

/** Room holds information pertaining to that specific room such as:
 *    Type: 1 = empty, 2 = treasure, 3 = monster
 * 		If it has treasure: which treasure, including name, description, and strength
 * 		If it has a monster: which monster, including name, description, and strength
 *    The general description of the room (before any action is taken - that is in dungeon)
 *    
 *    In room, types and monsters/treasures/descriptions are automatically set in the constructor
 *    you can also externally set type and treasure 
 *    
 *    You can get type, treasure, monster, description, as well as x and y locations
 */
public class Room {
	/** type 1 = empty type 2 = treasure type 3 = monster
	 */
	private int type;

	// x and y coordinates
	private int locationX;
	private int locationY;

	// monster and treasure of the given room
	private JuJuMonster monster = null;
	private JuJuTreasure treasure = null;

	// arrayLists of all potential monsters and treasures
	private ArrayList<JuJuMonster> monsters = new ArrayList<JuJuMonster>();
	private ArrayList<JuJuTreasure> treasures = new ArrayList<JuJuTreasure>();

	/** constructor, takes in x and y coordinates of the room
	 * 
	 * has a list of potential monsters and a list of potential treasures
	 * 
	 * randomly sets a type to the room (1, 2, or 3)
	 * 
	 * fills the room with a monster or treasure if types 2 or 3
	 * 
	 * sets up the start and end rooms to always be the same
	 */
	public Room(int x, int y) {
		this.locationX = x;
		this.locationY = y;

		// list of potential monsters
		monsters.add(new JuJuMonster("bees?", " better than a windmill full of corpses.", 2));
		monsters.add(new JuJuMonster("a zombie", ", like, one of those scary-fast ones from World War Z.", 2));
		monsters.add(new JuJuMonster("the blob", "-indescribable, indestructible, nothing can stop it!", 2));
		monsters.add(new JuJuMonster("a skeleton", " both spooky AND scary.", 3));
		monsters.add(new JuJuMonster("a ghost", " booooooooooooooooooooooooooooooooo", 3));
		monsters.add(new JuJuMonster("a giant tarantula", " did it have to be spiders?", 3));
		monsters.add(new JuJuMonster("Godzilla", " how did he fit down here?!", 4));
		monsters.add(new JuJuMonster("Chtulhu", "There is no language for such abysms  of shrieking and immemorial lunacy.", 4));
		monsters.add(new JuJuMonster("Orgalorg", " do you want to be a demon or a human.", 4));
		monsters.add(new JuJuMonster("SOMETHING", " nothing scarier than the unknown.", 4));
		monsters.add(new JuJuMonster("your inner demons", " you can only bury them for so long.", 10));// boss monster


		// list of potential treasures
		treasures.add(new JuJuTreasure("a sword", " long, metal, sharp, everything you'd want in a sword.", 3));
		treasures.add(new JuJuTreasure("a really big gem", " nice, heavy, and shiny.", 3));
		treasures.add(new JuJuTreasure("a bloody axe", " not used for chopping trees, let's just leave it at that.", 3));
		treasures.add(new JuJuTreasure("a flail", " big spiky metal ball on the end of a chain. Medievallll.", 3));
		treasures.add(new JuJuTreasure("the death ray 2000", " so *this* is what our taxpayer money is going towards.", 4));
		treasures.add(new JuJuTreasure("the death ray 2001", " so *this* is what our taxpayer money is going towards.", 4));
		treasures.add(new JuJuTreasure("the death ray 2002", " so *this* is what our taxpayer money is going towards.", 4));
		treasures.add(new JuJuTreasure("the death ray 3000", " so *this* is what our taxpayer money is going towards.", 5));
		treasures.add(new JuJuTreasure("the death ray 3001", " so *this* is what our taxpayer money is going towards.", 5));
		treasures.add(new JuJuTreasure("Violet", " the goodest dog.", 5));
		treasures.add(new JuJuTreasure("a ruby-encrusted flamethrower", " what more can I say?  (except 30-foot-long flames!)", 5));

		// set type randomly for each room
		// if the rooms are close to the start (rows 0 or 1) they are randomly empty or treasure (no monsters early in the map)
		this.type = StdRandom.uniform(3) + 1; //random type between 1 and 3
		if(locationY <= 1) {
			this.type = StdRandom.uniform(2) + 1; //no monsters on first two rows
		}
		

		// if room is type 2, sets a treasure from the arrayList of treasures
		// strength is based on y coordinate (which would correspond to the distance
		// from the start)
		if (type == 2) {
			int number;
			// treasures near the end are later in the array, so will be 'stronger'
			if (locationY >= 6) {
				number = StdRandom.uniform(1,5); //to randomly choose a strong treasure from the end of the treasures ArrayList
				treasure = treasures.get(treasures.size()-number); 
			} else if (locationY > 2 && locationY < 6) {
				number = StdRandom.uniform(5, 8);
				treasure = treasures.get(treasures.size()-number);
			} else if (locationY <= 2) { // monsters near start are earlier in the array, so will be 'easier'
				number = StdRandom.uniform(8,11); 
				treasure = treasures.get(treasures.size()-number);
			}
		}

		// if room is type 3, sets a monster from the arrayList of monsters
		// difficulty is based on y coordinate (which would correspond to the distance
		// from the start)
		if (type == 3) {
			int number;
			// monsters near the end are later in the array, so will be 'harder' (have
			// higher strength)
			if (locationY >= 7) {
				number = StdRandom.uniform(2,6);
				monster = monsters.get(monsters.size() - number);
			} else if (locationY > 3 && locationY < 7) {
				number = StdRandom.uniform(6, 9);
				monster = monsters.get(monsters.size() - number);
			} else if (locationY > 1 && locationY <= 3) { // monsters near start are earlier in the array, so will be 'easier'
				number = StdRandom.uniform(9,12);
				monster = monsters.get(monsters.size() - number);
			}
		}

		//setting up the first room: type 1
		if (locationX == 5 && locationY == 0) { //start room
			type = 1;
		}
		//setting up the last room: type 3, boss monster
		if (locationX == 5 && locationY == 9) { //end room
			type = 3;
			monster = monsters.get(monsters.size() - 1);
		}
	}

	/** sets the type
	 * this can be used when a treasure is picked up or a monster is defeated, making the room empty */
	public void setType(int type) {
		this.type = type;
	}

	/** sets treasure
	 *  can use this to place a specific treasure (such as "confidence")
	 */
	public void setTreasure(JuJuTreasure treasure) {
		this.treasure = treasure;
	}

	/** gets description
	 * returns what will be said for that room */
	public String getDescription() {
		if (getLocationX() == 5 && getLocationY() == 0) {
			return "You've got baaaaaaad juju. Go reclaim what's rightfully yours!";
		}

		if (getLocationX() == 5 && getLocationY() == 9) {
			return "You have come face to face with " + monster.getName();
		}

		else if (type == 1) {
			return "Yippee! This room is empty!";
		} else if (type == 2) {
			if (getLocationY() < 3) {
				return "Ooh look- a treasure chest! Want to find out what's inside? "
						+ "  (Press space to pick up treasure)";
			} else {
				return "Ooh look- a treasure chest! Want to find out what's inside?";
			}
		} else if (type == 3) {
			if (getLocationY() < 4) {
				return "Oh no! You've found " + monster.getName() + "!" + "  (Press space to fight monster)";
			} else {
				return "Oh no! You've found " + monster.getName() + "!";
			}
		}
		return null;
	}

	/** gets type (1, 2, or 3) of room; 1 = empty, 2 = treasure, 3 = monster
	 */
	public int getType() {
		return this.type;
	}

	// gets coordinates
	/** gets x coordinate (column) */
	public int getLocationX() {
		return locationX;
	}
	/** gets y coordinate (row) */
	public int getLocationY() {
		return locationY;
	}


	/** gets treasure (will only return a treasure if type 2, otherwise will be null)
	 */
	public JuJuTreasure getTreasure() {
		return treasure;
	}

	/** gets monster (will only return a monster if type 3, otherwise will be null)
	 */
	public JuJuMonster getMonster() {
		return monster;
	}

}