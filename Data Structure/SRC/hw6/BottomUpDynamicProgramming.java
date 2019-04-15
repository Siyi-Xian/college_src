/**
 * [hw5] Problem 3:
 * 
 * We implement a bottom-up solution to the Longest Increasing Subsequence
 * problem, following the algorithm described in lec9b.
 */

public class BottomUpDynamicProgramming {

	/**
	 * TODO
	 * 
	 * Returns an array consisting of the longest increasing subsequence in a.
	 * Your solution must follow the code sketch given in lecture.
	 */
	public static int[] lis(int[] a) {
		int n = a.length;
		Result[] cache = new Result[n];

		for (int i = 0 ; i < n; i++) {
			cache[i] = new Result(0, i);
			for (int j = 0; j < i; j++) {
				if (a[i] > a[j] && cache[i].score < cache[j].score + 1)
					cache[i] = new Result(cache[j].score + 1, j);
			}
		}
		
		
		int max = 0, maxValue = 0;
		for (int i = 0; i < n; i++) {
			if (cache[i].score > maxValue) {
				max = i; 
				maxValue = cache[i].score;
			}
		}
		int[] ans = new int[maxValue + 1];
		for (int j = maxValue; j >= 0; j--) {
			ans[j] = a[max];
			max = cache[max].parent;
		}
			
		return ans;
	}

	/**
	 * TODO: Write a comprehensive battery of test cases.
	 */
	@org.junit.Test
	public void test() {
		int[] a, b;
		a = new int[] { 5, 6, 1, 2, 9, 3, 4, 7, 4, 3 };
		b = lis(a);
		assert 5 == b.length;
		assert 1 == b[0];
		assert 2 == b[1];
		assert 3 == b[2];
		assert 4 == b[3];
		assert 7 == b[4];
		a = new int[] { 2, 1, 5, 3, 6, 4, 2, 7, 9, 11, 8, 10 };
		b = lis(a);
		assert 6 == b.length;
		assert 2 == b[0];
		assert 5 == b[1];
		assert 6 == b[2];
		assert 7 == b[3];
		assert 9 == b[4];
		assert 11 == b[5];
		System.out.println("All tests passed...");
	}
}

/**
 * A subproblem is to compute the length of the longest increasing subsequence
 * that ends with the i-th element in the array. The result of solving such a
 * subproblem is the score (i.e., the length of the sequence) and the parent
 * (i.e., the index of element in the array that precedes the i-th one in the
 * identified sequence).
 */
class Result {
	int score;
	int parent;

	Result(int score, int parent) {
		this.score = score;
		this.parent = parent;
	}

	public String toString() {
		return String.format("(%d,%d)", score, parent);
	}
}
