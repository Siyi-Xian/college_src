package hw2;

import org.junit.Test;

public class TestCase {

	@Test
	public void testMatrixSearch() {
		int[][] a;
		a = new int[][] { new int[] { 2, 14, 26, 37, 43, 51, }, new int[] { 4, 16, 28, 38, 44, 54, },
				new int[] { 6, 18, 30, 39, 45, 57, }, new int[] { 8, 20, 32, 40, 46, 60, },
				new int[] { 10, 22, 34, 41, 47, 63, }, new int[] { 12, 24, 36, 42, 48, 66, }, };
		for (int[] row : a)
			for (int x : row)
				assert MatrixSearch.contains(x, a);
		for (int x = 15; x <= 35; x += 2)
			assert !MatrixSearch.contains(x, a);
	}
	
	@Test
	public void testMajority() {
		Majority.checkExpect(4, new int[] { 3, 3, 4, 2, 4, 4, 2, 4, 4 });
		Majority.checkException(new int[] { 3, 3, 4, 2, 4, 4, 2, 4 });
		Majority.checkExpect(1, new int[] { 1, 2, 1, 2, 1, 2, 1, 2, 1 });
		Majority.checkExpect(2, new int[] { 1, 2, 1, 2, 1, 2, 1, 2, 2 });
		Majority.checkException(new int[] { 1, 2, 1, 2, 1, 2, 1, 2, 3 });
		Majority.checkExpect(1, new int[] { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, });
		Majority.checkException(new int[] {});
		Majority.checkExpect(5, new int[] { 5 });
		Majority.checkException(new int[] { 1, 2 });
		Majority.checkExpect(1, new int[] { 1, 1 });
		Majority.checkException(new int[] { 1, 2, 3 });
		Majority.checkExpect(1, new int[] { 1, 1, 2 });
		Majority.checkExpect(1, new int[] { 1, 2, 1 });
		Majority.checkExpect(1, new int[] { 2, 1, 1 });
		Majority.checkExpect(1, new int[] { 1, 1, 1 });
	}

}
