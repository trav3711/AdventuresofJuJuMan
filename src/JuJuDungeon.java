import java.util.*;

public class JuJuDungeon {

	private int[][] map;
	private int startX;
	private int startY;
	private int mapSize;
	private Room startRoom;
	private Room playerRoom;
	private String roomText;
	private int gameOver;
	private boolean dead;
	private ArrayList<Room> rooms;
	private ArrayList<Room> visitedRooms;
	private Player player;
	
	public JuJuDungeon() {
		gameOver = 0; //0 = alive, 1 = dead, 2 = game won
		dead = false; //knows whether the player has died or not
		mapSize = 10;
		map = new int[mapSize][mapSize];
		startX = 5;
		startY = 0;
		startRoom = new Room(startX, startY); //the starting room on the map
		roomText = "";
		rooms = new ArrayList<Room>(0); //rooms on board
		visitedRooms = new ArrayList<Room>(0); //rooms player has visited
		visitedRooms.add(startRoom); //add the fist room which 
		player = new Player(startX, startY);
		playerRoom = startRoom;
		setDungeonMap();
		setRooms();
		placeWinningTreasure();
		
	}
	
	/**
	 * moves a player from one room to another and sets the players position to the new room
	 * @param room = the room that is being moved to
	 * @return true if the move is to a valid room
	 */
	public void move(Room room) {
		player.setxLocation(room.getLocationX());
		player.setyLocation(room.getLocationY());
		playerRoom = room;
		visitedRooms.add(playerRoom);
	}
	
	/**
	 * plays the scenario of the current room object
	 * @return a string of the result of the room scenario
	 */
	public String playRoomScene() {
		roomText = "";
		//if room is empty
		if(playerRoom.getType() == 1) {
			roomText += "This place is boring. You twiddle your thumbs and contemplate  the consequences of your actions in this dark empty room.";
			return roomText;
		}
		//if room has a treasure in it
		else if(playerRoom.getType() == 2) {
			pickUpTreasure();
			roomText += "You picked up: " + playerRoom.getTreasure().getName() + " " + playerRoom.getTreasure().getDescription();
			player.setWeapon(playerRoom.getTreasure());
			playerRoom.setType(1);
			return roomText;
		}
		//if room has a monster in it
		else if(playerRoom.getType() == 3) {
			battle(playerRoom.getMonster());
			roomText += "You attack " + playerRoom.getMonster().getName() + ".";
			if(!dead){
				roomText += "You defeated " + playerRoom.getMonster().getName() + "  " + playerRoom.getMonster().getDescription();
			}
			else {
				roomText += " You were not strong enough.  You DEAD son.";
				gameOver = 1;
			}
			playerRoom.setType(1);
			return roomText;
		}
		return roomText;
		
	}
	
	/**
	 * decides whether monster or player wins in a battle
	 * @param monster that is being battled
	 * @return true if player strength is greater than or equal to monster strength
	 */
	public void battle(JuJuMonster monster) {
		if(player.getStrength() >= monster.getStrength()) {
			dead = false;
		}
		else dead = true;
	}
	
	/**
	 * will eventually be a method for picking up a treasure and setting it as the players weapon
	 */
	public void pickUpTreasure() {
		if(playerRoom.getTreasure() != null) {
			player.setStrength(playerRoom.getTreasure().getStrength());
		}
	}
	
	/**Determines if the game is over
	 * 
	 * @return boolean if player dies or wins the game
	 */
	public int gameOver(){
		if(playerRoom.getLocationY() == mapSize - 1 && playerRoom.getLocationX() == startX) {
			if(playerRoom.getType() == 1 && !dead) {
				gameOver = 2;
			}
			else if(playerRoom.getType() == 1 && dead){
				gameOver = 1;
			}
		}
		return gameOver;
	}
	
	/*
	 * main method for recursively generating a random map of integers
	 */
	public void setDungeonMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j] = 0;
			}
		}
		map[5][0] = 1;
		// Recursively adds "rooms" (integer 1, not type room) to map
		int x = mapSize/2;
		int y = 1;

		addToPath(x, y);
		
	}

	public void addToPath(int x, int y) {
			map[x][y] = 1;
		if (x==5 && y == mapSize-1) {
			// done
		} else {
			int roll = StdRandom.uniform(3) + 1;
			if (roll == 1) {
				if (isValidCoordinate(x-1, y)){ // if its a valid move 
					x = x - 1; // left
				}
			}
			if (roll == 2) {
				if (isValidCoordinate(x, y+1) ){
					y = y + 1; // up
				}
			}
			if (roll == 3) {
				if (isValidCoordinate(x+1, y) ) {					
					x = x + 1; // right
				}
			} 
				
		addToPath(x,y);
		}

	}
	
	/**
	 * sets rooms based on map of integers
	 */
	public void setRooms() {
		for(int y = mapSize - 1; y >= 0; y--) {
			for(int x = 0; x < mapSize; x++) {
				if(map[x][y] != 0) {
					rooms.add(new Room(x, y));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param x - x position on our map of integers
	 * @param y - y position on our map of integers
	 * @return returns whether that position has a room associated with it
	 */
	public boolean isRoom(int x, int y) {
		for(int i = 0; i < rooms.size(); i++) {
			if(rooms.get(i).getLocationX() == x) {
				if(rooms.get(i).getLocationY() == y) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void placeWinningTreasure() {
		int select = (int)(Math.random()*rooms.size());
		while(rooms.get(select).getLocationX() == 5 && rooms.get(select).getLocationY() == mapSize-1){
			select = (int)(Math.random()*rooms.size());
		}
		String name = "confidence";
		String description = "this is all you need";
		rooms.get(select).setType(2);
		rooms.get(select).setTreasure(new JuJuTreasure(name, description, 10000));
		
	}

	
	/**
	 * 
	 * @param x = an x position in map[][]
	 * @param y = a y position in map[][]
	 * @return true if the coordinate is valid
	 */
	public boolean isValidCoordinate(int x, int y) {
		if(x >= 0 && x < map.length) {
			if(y >= 0 && y < map.length) {
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * @return the string representing a two dimensional array of integers
	 */
	public String toString() {
		String result = "";
		for(int c = map.length-1; c >= 0; c--) {
			for(int r= 0; r < map.length; r++) {
				if(map[r][c] == 0) {
					result += "0 ";
				}
				if(map[r][c] == 1) {
					result += "1 ";
				}
				
			}
			result += "\n";
		}
		return result;
	}
	
	//getters for different private variables
	
	public int getMapSize() {
		return mapSize;
	}
	
	public int[][] getMap(){
		return map;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Room getPlayerRoom() {
		return playerRoom;
	}
	
	public ArrayList<Room> getRoomList(){
		return rooms;
	}
	
	public ArrayList<Room> getVisitedRooms(){
		return visitedRooms;
	}
	
}

