import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RoomTest {

	private Room startRoom;

	@Before
	public void setUp() throws Exception {
		startRoom = new Room(5, 0);
	}

	/** runs through 100 generations of the starting coordinates
	 *  to make sure it is always empty (type 1)*/
	@Test
	public void startRoomAlwaysType1() {
		for (int i = 0; i < 100; i++) {
			startRoom = new Room(5, 0);
			assertEquals(1, startRoom.getType());
		}
	}
	/** runs through 100 generations of the end coordinates
	 *  to make sure it is always type 3 and its monster is "your inner demons"*/
	@Test
	public void endRoomSetUpCorrectly() {
		for (int i = 0; i < 100; i++) {
			Room endRoom = new Room(5, 9);
			assertEquals(3, endRoom.getType());
			assertEquals("your inner demons", endRoom.getMonster().getName());
		}
	}
	/** first two rows never set to type 3 (monster)
	 */
	@Test
	public void noMonstersInFirstTwoRows() {
		for (int i = 0; i < 100; i++) {
			Room earlyRoom = new Room (3, 0);
			Room earlyRoom2 = new Room (3, 1);
			assertTrue(earlyRoom.getType() != 3 && earlyRoom2.getType() != 3);
		}		
	}
	
	/** (other than above exceptions) when a normal room is generated it is
	 * randomly set to type 1, 2, or 3 
	 * 
	 * (in 1000 runs, each number is generated at least 100 times)
	 */
	//TODO fix
	@Test
	public void eachRandomRoomTypeSet() {
		int one = 0;
		int two = 0;
		int three = 0;
		
		for (int i = 0; i < 1000; i++) {
			Room room2 = new Room(2, 5);
			if (room2.getType() == 1) {
				one++;
			}
			if (room2.getType() == 2) {
				two++;
			}
			if (room2.getType() == 3) {
				three++;
			}
		}
		assertTrue(one/100 != 0);
		assertTrue(two/100 != 0);
		assertTrue(three/100 != 0);
	}

	/** room only sets monsters and treasures when when the correct types (2,3)
	 * otherwise monster and treasure are null
	 */
	@Test
	public void setsMonsterAndTreasureOnlyInCorrectTypes() {
		for (int i = 0; i < 100; i++) {
			Room room2 = new Room(3, 5);

			if (room2.getType() == 1) {
				assertNull(room2.getTreasure());
				assertNull(room2.getMonster());
			}
			if (room2.getType() == 2) {
				assertNotNull(room2.getTreasure());
				assertNull(room2.getMonster());
			}
			if (room2.getType() == 3) {
				assertNull(room2.getTreasure());
				assertNotNull(room2.getMonster());
			}
		}
	}
	
	/** strengths of monsters & treasures set in order of */
	@Test
	public void increasingDifficulty() {
		for (int i = 0; i < 100; i++) {
			int y = StdRandom.uniform(2, 10);
			Room room = new Room(3, y);
			
			if(room.getType() == 2) {
				if(room.getLocationY() >= 6 ) {
					assertEquals(5, room.getTreasure().getStrength());
				} else if(room.getLocationY() > 2 && room.getLocationY() < 6) {
					assertEquals(4, room.getTreasure().getStrength());
				} else if(room.getLocationY() <= 2) {
					assertEquals(3, room.getTreasure().getStrength());
				}
			}
			if(room.getType() == 3) {
				if(room.getLocationY() >= 7 ) {
					assertEquals(4, room.getMonster().getStrength());
				} else if(room.getLocationY() > 3 && room.getLocationY() < 7) {
					assertEquals(3, room.getMonster().getStrength());
				} else if(room.getLocationY() > 1 && room.getLocationY() <= 3) {
					assertEquals(2, room.getMonster().getStrength());
				}
			}
		}
	}

	/**
	 * start room and end room have correct descriptions
	 */
	//FIXME correct description for start and end
	@Test
	public void getDescriptionGetsCorrectDescription() {
			Room startRoom = new Room(5, 0);
			Room endRoom = new Room(5, 9);
			assertEquals("You've got baaaaaaad juju. Go reclaim what's rightfully yours!", startRoom.getDescription());
			assertEquals("You have come face to face with your inner demons", endRoom.getDescription());
	}
	
}
