/*************************************************************************
 * Name:         
 * 
 * Homework 5:  N-puzzle problem  
 * 
 * Released:  10/21/16
 * 
 * Add as many private helper functions as you want
 *
 *************************************************************************/
package N_Puzzle;

import java.util.Random;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Board {
    
    // 2-d array to represent puzzle
    private final int[][] board;
    
    private final int N; // Depth of array
    
    /* I, J are indices in the board where the blank square is located.  
     * Saving this will be helpful for neighbors method 
     * blank square is represented with the value 0
     */
    private final int I;
    private final int J;
    
    /**
     * initialize NxN Puzzle Board 
     * @param blocks : initial blocks
     */
    public Board(int[][] blocks) {
        N = blocks.length;
        board = blocks;
        
        for (int i = 0; i < N; i++)
        	for (int j = 0; j < N; j++)
        		if (board[i][j] == 0) {
        			I = i; 
        			J = j;
        			return;
        		}
        
        I = 0xfffffff;
        J = 0xfffffff;
        /* TO-DO
         * Copy blocks into board instance field 
         * Save i,j index where the blank square is
         */
    }
    
    /** 
     * return board dimension
     * @return Dimension
     */
    public int dimension() {
        return N;
    }
    
    /**
     * count # of blocks out of position
     * @return # of blocks out of position
     */
    public int hamming() {
    	int sum = 0;
    	
    	for (int i = 0; i < N; i++)
    		for (int j = 0; j < N; j++)
    			if (board[i][j] != i * N + j + 1 && board[i][j] != 0)
    				sum++;
    	
        return sum;
    }
    
    /**
     * count Manhattan distances between blocks and goal
     * @return Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int sum = 0;
        
        for (int i = 0; i < N; i++)
        	for (int j = 0; j < N; j++)
        		if (board[i][j] != 0)
        			sum += Math.abs((board[i][j] - 1) / N - i) + Math.abs((board[i][j] - 1) % N - j);
        
        return sum;
    }
    
    /**
     * is this Board the goal board?
     * @return true for goal, false for not
     */
    public boolean isGoal() {
    	for (int i = 0; i < N; i++)
    		for (int j = 0; j < N; j++)
    			if (board[i][j] != (i * N + j + 1) % (N * N))
    				return false;	
        return true;
    }
    
    /**
     * Get a copy of initial board
     * @return copy of board
     */
    private int[][] copyBoard() {
    	int[][] t = new int[N][N];
    	
    	for (int i = 0; i < N; i++)
    		for (int j = 0; j < N; j++)
    			t[i][j] = board[i][j];
    	
    	return t;
    }
    
    /**
     * swap two blocks
     * @param i : x position of block one
     * @param j : y position of block one
     * @param it : x position of block two
     * @param jt : y position of block two
     * @param tw : the board that need to be swap
     * @return board after swap
     */
    private int[][] swap(int i, int j, int it, int jt, int[][] tw) {
    	int temp = tw[i][j];
    	tw[i][j] = tw[it][jt];
    	tw[it][jt] = temp;
    	return tw;
    }
    
    /** 
     * swaps any two numbers next to each other 
     * - iterate through the board, and swap first two numbers next to each other 
     *   (not including the blank square) 
     * @return twin board
     */ 
    public Board twin() {
    	int[][] twin = copyBoard();
    	
    	for (int i = 0; i < N; i++)
    		for (int j = 0; j < N - 1; j++) {
    			if (twin[i][j] != 0 && twin[i][j + 1] != 0 && checkBoarder(i, j + 1)) {
    				twin = swap(i, j, i, j + 1, twin);
    				return new Board(twin);
    			}
    		}
    	return new Board(twin);
    }
    
    /**
     * @return board as a string
     */
    public String toString() {
        String result = N + "" + "\n";
        
        for (int i = 0; i < N; i++) {
        	for (int j = 0; j < N; j++)
        		result += " " + board[i][j] + "";
        	result += "\n";
        }
        
        return result;
    }
    
    /**
     * if two boards have equal strings then they are equal
     * @return true for equal, false for not
     */
    public boolean equals(Object other) {  
    	return this.toString().equals(other.toString());
    }
    
    /**
     * Swap a given block with blank block
     * @param x : x position of given block
     * @param y : y position of given block
     * @return a Board that have been swapped
     */
    private Board swapNeighbors(int x, int y) {
    	Board b;
    	if (checkBoarder(x, y)) {
        	b = new Board(swap(x, y, I, J, copyBoard()));
        	return b;
    	}
    	return null;
    }
    
    /**
     * Check if it is out of boarder
     * @param x : x position
     * @param y : y position
     * @return true for in boarder, false for out of boarder
     */
    private boolean checkBoarder(int x, int y) {
    	return x >= 0 && y >= 0 && x < N && y < N;
    }
    
    /** 
     * all neighboring boards – all the possible moves that can be made from the current board
     * Can only swap numbers (blocks) with the blank square
     *  - From the index of the blank square, swap with all blocks above, below, left, and right 
     *    (that are in bounds)
     *  - Create a new board for each of these and add them all to the stack 
     * @return a Stack of Board that has done all the swaps
     */
    public Stack<Board> neighbors() {
        Stack<Board> stack = new Stack<>();
        
        Board temp;
        
        temp = swapNeighbors(I - 1, J);
        if (temp != null)
        	stack.push(temp);
        
        temp = swapNeighbors(I + 1, J);
        if (temp != null)
        	stack.push(temp);
        
        temp = swapNeighbors(I, J - 1);
        if (temp != null)
        	stack.push(temp);
        
        temp = swapNeighbors(I, J + 1);
        if (temp != null)
        	stack.push(temp);
        
        return stack;
    }
    
    // unit test client from BoardTest
    public static void main(String[] args) {
    	Result result = JUnitCore.runClasses(BoardTest.class);
        for (Failure failure : result.getFailures()) {
           System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
   }
}