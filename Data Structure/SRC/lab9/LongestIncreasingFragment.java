import org.junit.Test;

/**
 * TODO: Report the results of your time trials here. O(n)
 * 
 * @author Siyi Xian
 */

public class LongestIncreasingFragment {

	/**
	 * Takes an array of positive integers and returns the length of the longest
	 * strictly increasing fragment (of consecutive values).
	 */

	public static int lif(int[] a) {
		// TODO
		int[] p = new int[a.length];

		if (a.length == 0)
			return 0;

		p[a.length - 1] = 1;
		for (int i = a.length - 2; i >= 0; i--)
			if (a[i] < a[i + 1])
				p[i] = p[i + 1] + 1;
			else
				p[i] = 1;

		int max = -1;
		for (int i = 0; i < p.length; i++)
			if (p[i] > max)
				max = p[i];

		return max;
	}

	@Test
	public void test() {
		int[] a;
		a = new int[] {};
		assert lif(a) == 0;
		a = new int[] { 5 };
		assert lif(a) == 1;
		a = new int[] { 1, 2, 3, 4, 5, 6 };
		assert lif(a) == 6;
		a = new int[] { 6, 5, 4, 3, 2, 1 };
		assert lif(a) == 1;
		a = new int[] { 5, 1, 5, 2, 3, 4 };
		assert lif(a) == 3;
		a = new int[] { 3, 2, 4, 6, 7, 2, 9, 1 };
		assert lif(a) == 4;
	}
}
