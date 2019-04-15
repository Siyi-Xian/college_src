package N_Puzzle;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {
	
	@Test
	public void test() {
		// initial board
		Board b = new Board(new int[][]{{8, 1, 3},
										{4, 0, 2},
										{7, 6, 5}});
		// goal board
		Board goal = new Board(new int[][]{{1, 2, 3},
										   {4, 5, 6},
										   {7, 8, 0}});
		
		// unit test of Board.hamming()
		assertEquals(5, b.hamming());
		
		// unit test of Board.manhattan()
		assertEquals(10, b.manhattan());
		
		// unit test of Board.isGoal()
		assertEquals(true, goal.isGoal());
		assertEquals(false, b.isGoal());
		
		// unit test of Board.twin()
		assertEquals(new Board(new int[][]{{1, 8, 3},
										   {4, 0, 2},
										   {7, 6, 5}}), b.twin());
		
		// unit test of Board.toString()
		assertEquals("3\n 8 1 3\n 4 0 2\n 7 6 5\n", b.toString());
		
		// unit test of Board.equals()
		assertEquals(true, b.equals(b));
		assertEquals(false, b.equals(goal));
		
		// unit test of Board.neighbors()
		Stack<Board> s = new Stack<Board>();
		s.push(new Board(new int[][]{{8, 0, 3},{4, 1, 2},{7, 6, 5}})); // result board of swap blank block with the block above it
		s.push(new Board(new int[][]{{8, 1, 3},{4, 6, 2},{7, 0, 5}})); // result board of swap blank block with the block below it
		s.push(new Board(new int[][]{{8, 1, 3},{0, 4, 2},{7, 6, 5}})); // result board of swap blank block with the block left to it
		s.push(new Board(new int[][]{{8, 1, 3},{4, 2, 0},{7, 6, 5}})); // result board of swap blank block with the block right to it
		assertEquals(s.toString(), b.neighbors().toString()); // compare those two value using String returning
	}

}
