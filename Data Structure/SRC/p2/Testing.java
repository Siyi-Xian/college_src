import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit tests for all TODO methods.
 */

public class Testing {

	@Test
	public void testOnBoard() {
		assertFalse(new Coord(3, 4).onBoard(4));
		assertTrue(new Coord(3, 4).onBoard(5));
	}
	
	@Test
	public void testNeighbors() {
		Coord someCoord = new Coord(2, 1);
		assertEquals (someCoord.neighbors(3).toString(), "[(1, 1), (2, 2), (2, 0)]");
		assertEquals (someCoord.neighbors(4).toString(), "[(3, 1), (1, 1), (2, 2), (2, 0)]");

		someCoord = Coord.ORIGIN;
		assertEquals (someCoord.neighbors(3).toString(), "[(1, 0), (0, 1)]");
		assertEquals (someCoord.neighbors(1).toString(), "[]");

		someCoord = new Coord(5, 5);
		assertEquals (someCoord.neighbors(3).toString(), "[]");
		assertEquals (someCoord.neighbors(6).toString(), "[(4, 5), (5, 4)]");
	}
	
	@Test
	public void testHashCode() {
		Coord someCoord = new Coord(2, 1);
		assertEquals (someCoord.hashCode(), 8191);

		someCoord = Coord.ORIGIN;
		assertEquals (someCoord.hashCode(), 0);

		someCoord = new Coord(5, 5);
		assertEquals (someCoord.hashCode(), 20480);
	}
	
	@Test
	public void testFullyFlood() {
		Board someBoard = new Board(1);
		assertTrue(someBoard.fullyFlooded());
		
		someBoard = new Board(14);
		assertFalse(someBoard.fullyFlooded());
	}
	
	@Test
	public void testFlood() {
		Game.main(null);
	}

}