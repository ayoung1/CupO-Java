package trivia;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MazeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSolvable() {
		Room[][] testGrid = {{new Room(2,2,1,2),new Room(2,2,2,2),new Room(2,1,1,2),new Room(2,2,2,1)},
                {new Room(1,1,1,2),new Room(2,1,2,1),new Room(1,2,2,1),new Room(2,2,2,2)},
                {new Room(1,2,1,2),new Room(2,2,2,2),new Room(2,2,2,2),new Room(2,2,2,2)},
                {new Room(1,1,2,2),new Room(2,1,2,1),new Room(2,2,2,2),new Room(2,2,2,2)}};
		Room[][] testGridUnsolvable = {{new Room(2,2,1,2),new Room(2,2,2,2),new Room(2,1,1,2),new Room(2,2,2,2)},
                {new Room(1,1,1,2),new Room(2,1,2,1),new Room(1,2,2,1),new Room(2,2,2,2)},
                {new Room(1,2,1,2),new Room(2,2,2,2),new Room(2,2,2,2),new Room(2,2,2,2)},
                {new Room(1,1,2,2),new Room(2,1,2,1),new Room(2,2,2,2),new Room(2,2,2,2)}};
		Maze m1 = new Maze(testGrid,0,0,3,0);
		Maze m2 = new Maze(testGridUnsolvable,0,0,3,0);
		assertEquals(true, m1.mazeIsSolvable());
		assertEquals(false, m2.mazeIsSolvable());
	}
	@Test
	public void testRoomGets()
	{
		Room r1 = new Room(0,1,2,3);
		assertEquals(0,r1.getNorthDoor());
		assertEquals(1,r1.getEastDoor());
		assertEquals(2,r1.getSouthDoor());
		assertEquals(3,r1.getWestDoor());		
	}
	@Test
	public void testIsPassable()
	{
		Room r1 = new Room(0,1,2,2);
		assertEquals(true,r1.isPassable(0));
		assertEquals(true,r1.isPassable(1));
		assertEquals(false,r1.isPassable(2));
	}
	@Test
	public void testAllLocked()
	{
		Room r1 = new Room(2,2,2,2);
		Room r2 = new Room(2,2,0,2);
		assertEquals(true,r1.allLocked());
		assertEquals(false,r2.allLocked());
	}
	@Test
	public void testAdjustLock()
	{
		Room r1 = new Room(2,2,2,2);
		r1.adjustLock(0,1);
		assertEquals(0,r1.getNorthDoor());
		assertEquals(r1.getEastDoor(), 2);
		assertEquals(r1.getSouthDoor(),2);
		assertEquals(r1.getWestDoor(),2);
	}

}
