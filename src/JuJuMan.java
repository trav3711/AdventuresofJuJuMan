import java.awt.event.KeyEvent;
import java.util.ArrayList;

/*
 * Thanks to the artists who we ripped off:
 * dungeon: https://wall.alphacoders.com/big.php?i=217432
 * our hero: https://retrospriteresources.deviantart.com/art/Thief-PSP-Battler-RPG-Maker-MV-661038342
 * bees?: https://www.spriters-resource.com/psp/lunarsilverstarharmony/sheet/58128/
 * the exclamation point: https://commons.wikimedia.org/wiki/File:Yellow_exclamation_mark.svg
 */

public class JuJuMan {

	private static final double width = 1;
	private static final double textoffset = 0.20; // space for text bar
	private int mapsize; // number of rooms across width and height
	private double height; // width - textoffset
	private double side; // side length of each room
	private double playerX;
	private double playerY;
	private int directionX;
	private String text;

	private int directionY;
	private JuJuDungeon dungeon = new JuJuDungeon();

	private ArrayList<Room> rooms;
	private ArrayList<Room> visitedRooms;

	public static void main(String[] args) {
		new JuJuMan().play();
	}

	public JuJuMan() {
		mapsize = dungeon.getMapSize();
		// visitedRooms = dungeon.getVisitedRooms();
		height = width - textoffset;
		side = height / mapsize;
		playerX = (width / 2);
		playerY = (side / 2) + (textoffset);
		directionX = 0;
		directionY = 0;
	}

	/*
	 * Updates text to be printed, depending on if an action is complete or if a new
	 * room is entered
	 */
	public void updateText() {
		if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
			text = dungeon.playRoomScene();
		} else {
			text = dungeon.getPlayerRoom().getDescription();
		}
	}

	/*
	 * Draws board
	 */
	public void drawBoard() {

		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setPenColor(StdDraw.WHITE);

		// draws entire map
		visitedRooms = dungeon.getVisitedRooms();
		for (Room room : visitedRooms) {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledSquare(side * room.getLocationX() + (textoffset / 2),
					side * room.getLocationY() + textoffset + (side / 2), side / 2);
		}

		// entrance and exit
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledSquare(width / 2, textoffset + (side / 2), side / 2);
		StdDraw.filledSquare(width / 2, width - (side / 2), side / 2);

		// draws text bar
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0, 0, 1, textoffset);

		//draws player
		drawPlayer();
		//draws grid
		drawGrid();
		//displays inventory
		drawInventory();

		// draws text
		updateText();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(width / 2, textoffset - .04, text.substring(0, (text.lastIndexOf("  ") + 1)));
		StdDraw.text(width / 2, textoffset - .08, text.substring(text.lastIndexOf("  ") + 1, text.length()));

		// draws the grid to make the rooms look nice

		StdDraw.show();
	}

	/*
	 * Updates players coordinates and draws player
	 */
	public void drawPlayer() {

		if (movePlayer()) {
			playerX += directionX * side;
			playerY += directionY * side;
		}
		drawPlayerRoom();
		StdDraw.picture(playerX, playerY, "avatar.png");
	}
	
	/*
	 * Displays players current weapon
	 */
	public void drawInventory() {
		
		String inventory = new String ("Current Weapon: " + dungeon.getPlayer().getWeapon().getName());
		String strength = new String ("Strength: " + dungeon.getPlayer().getStrength());
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(width / 2, textoffset - .135, inventory);
		StdDraw.text(width / 2, textoffset - .17, strength);
	}

	/*
	 * Draws a grid to ensure that rooms have nice looking boarders
	 */
	public void drawGrid() {

		StdDraw.setPenColor(StdDraw.BLACK);
		for (int x = 0; x < dungeon.getMapSize(); x++) {
			StdDraw.line((textoffset / 2) + (x * side) + (side / 2), textoffset,
					(textoffset / 2) + (x * side) + (side / 2), width);
			StdDraw.line(0, textoffset + (x * side), width, textoffset + (x * side));
		}
	}

	/*
	 * If at starting or ending square, draws a red room, otherwise draws a white
	 * room at the location of the player. This method ensures that the room is
	 * drawn upon player entrance.
	 */
	public void drawPlayerRoom() {
		if ((dungeon.getPlayerRoom().getLocationX() == 5 && dungeon.getPlayerRoom().getLocationY() == 0)
				|| (dungeon.getPlayerRoom().getLocationX() == 5 && dungeon.getPlayerRoom().getLocationY() == 9)) {
			StdDraw.setPenColor(StdDraw.RED);
		} else {
			StdDraw.setPenColor(StdDraw.WHITE);
		}
		StdDraw.filledSquare(side * dungeon.getPlayerRoom().getLocationX() + (textoffset / 2),
				side * dungeon.getPlayerRoom().getLocationY() + textoffset + (side / 2), side / 2);
	}

	/*
	 * Checks if the room a player is trying to move to exists and then moves player
	 * to that room
	 */
	public boolean movePlayer() {

		directionX = 0;
		directionY = 0;

		if (dungeon.getPlayerRoom().getType() != 3) {
			if (StdDraw.isKeyPressed(KeyEvent.VK_W)) {
				directionY++;
			} else if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
				directionY--;
			} else if (StdDraw.isKeyPressed(KeyEvent.VK_D)) {
				directionX++;
			} else if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
				directionX--;
			}

		}

		rooms = dungeon.getRoomList();
		for (Room room : rooms) {
			if (dungeon.getPlayer().getxLocation() + directionX == room.getLocationX()) {
				if (dungeon.getPlayer().getyLocation() + directionY == room.getLocationY()) {
					dungeon.move(room);
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Runs the game. Plays the animation, draws start screen and then draws board continually until a
	 * gameOver condition is met.
	 */
	public void play() {
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(0, 1);

		animation("bees");
		startScreen();

		while (dungeon.gameOver() == 0) {
			drawBoard();
			while (!StdDraw.hasNextKeyTyped()) {
				// Wait for keypress
			}
			StdDraw.nextKeyTyped(); // Use up the key typed
		}
		if (dungeon.gameOver() == 2) {
			endScreen();
		} else if (dungeon.gameOver() == 1) {
			deathScreen();
		}
	}

	/*
	 * Draws start screen and instructions
	 */
	public void startScreen() {
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setPenColor(StdDraw.WHITE);

		StdDraw.picture(0.5, 0.55, "dungeon.png");

		StdDraw.text(0.5, 0.85, " Welcome to the dungeon. ");
		StdDraw.text(0.5, 0.7, "Move with:");
		StdDraw.text(0.5, 0.6, "W");
		StdDraw.text(0.5, 0.5, "A     D");
		StdDraw.text(0.5, 0.4, "S");
		StdDraw.text(0.5, 0.3, "Press SPACE to fight and collect treasure.");
		StdDraw.text(0.5, 0.1, "Press ENTER to begin your adventure.");

		StdDraw.show();

		while (!StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
			// Wait for key press
		}

	}
	
	/*
	 * Displays victory screen!
	 */
	public void endScreen() {
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setPenColor(StdDraw.WHITE);

		StdDraw.picture(0.5, 0.5, "dungeon.png");

		StdDraw.text(0.5, 0.85, " Congradulations!  ");
		StdDraw.text(0.5, 0.75, " You got your juju back.  ");
		StdDraw.text(0.5, 0.5, "Press ENTER to play again.  ");

		StdDraw.show();

		while (!StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
			// Wait for key press
		}
		new JuJuMan().play();
	}

	/*
	 * Displays screen after you die
	 */
	public void deathScreen() {
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setPenColor(StdDraw.WHITE);

		StdDraw.picture(0.5, 0.5, "dungeon.png");

		StdDraw.text(0.5, 0.85, " You’re not very good at this, are you. ");
		StdDraw.text(0.5, 0.7, " You died. ");
		StdDraw.text(0.5, 0.2, "Press ENTER to play again.");

		StdDraw.show();

		while (!StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
			// Wait for key press
		}
		new JuJuMan().play();
	}
	
	/*
	 * Animates cute intro
	 * 
	 */
	public void animation(String string) {
		for (double frame = 0; frame < 130; frame++) {
		StdDraw.clear(StdDraw.BLACK);
			
			String hero = new String("heroRight1.png");
			String monster = new String(string + "Right1.png");
			if(frame % 6< 3) {
				hero = "heroRight2.png";
				monster = string + "Right2.png";
			}
			
			StdDraw.picture(frame / 100, 0.5, monster);
			StdDraw.picture(frame / 100 - 0.15, 0.5, hero);
			StdDraw.show();
			StdDraw.pause(15);
		}
		
		StdDraw.pause(100);
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.picture(0.9, 0.5, "ah.png");
		StdDraw.show();
		StdDraw.pause(300);
		
		for (double frame = 130; frame > 0; frame--) {
			StdDraw.clear(StdDraw.BLACK);
		
			String hero = new String("heroLeft1.png");
			String monster = new String(string + "Left1.png");
			if(frame % 6< 3) {
				hero = "heroLeft2.png";
				monster = string + "Left2.png";
			}
			
			StdDraw.picture(frame / 100, 0.5, monster);
			StdDraw.picture(frame / 100 - 0.15, 0.5, hero);
			StdDraw.show();
			StdDraw.pause(15);
		}
	}
}