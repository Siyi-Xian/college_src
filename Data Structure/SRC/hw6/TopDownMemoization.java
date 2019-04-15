import java.util.Map;
import java.util.HashMap;

/**
 * [hw5] Problem 2:
 * 
 * Most of the work that you are to do is in the implementation of the
 * lisHelper() function. However, there are two small, but important, items in
 * the Problem class below that require your attention.
 * 
 * We implement a top-down memoized solution to the Longest Increasing
 * Subsequence problem, following the recursive algorithm described in lecture.
 * See the notes from lec9a and lec9b.
 */

public class TopDownMemoization {

	private static Map<SubProblem, Integer> cache = new HashMap<>();

	/**
	 * Returns the length of the longest increasing subsequence in the array a.
	 */
	public static int lis(int[] a) {
		cache.clear();
		// Initially, we consider the element at position 0 and our cap,
		// so far, is 0.
		SubProblem p = new SubProblem(0, 0);
		return lisHelper(a, p);
	}

	/**
	 * TODO
	 * 
	 * Returns the result of solving the SubProblem described by p.
	 */
	public static int lisHelper(int[] a, SubProblem p) {
		// TODO: Check to see if this problem has been previously
		// solved. If so, return the cached value.

		if (cache.containsKey(p))
			return cache.get(p);

		int ans = 0;
		if (p.pos < a.length) {
			int with = 0, without = 0;
			// TODO: Recur to find the "with" and "without" results, so
			// we can use those results to build the answer ans.
			without = lisHelper(a, new SubProblem(p.pos + 1, p.cap));
			if (a[p.pos] > p.cap)
				with = lisHelper(a, new SubProblem(p.pos + 1, a[p.pos]));

			ans = Math.max(1 + with, without);
		}
		// TODO: Store this problem/answer association in the cache
		// for future reference.

		cache.put(p, ans);

		return ans;
	}

	/**
	 * TODO: Write a comprehensive battery of test cases.
	 */
	@org.junit.Test
	public void test() {
		int[] a;
		a = new int[] { 5, 6, 1, 2, 9, 3, 4, 7, 4, 3 };
		assert 6 == lis(a);
		System.out.println(cache);
		a = new int[] { 2, 1, 5, 3, 6, 4, 2, 7, 9, 11, 8, 10 };
		assert 7 == lis(a);
		a = new int[100];
		for (int i = 0; i < a.length; i++)
			a[i] = i + 1;
		assert a.length == lis(a);
		System.out.println("All tests passed...");
	}
}

/**
 * A SubProblem corresponds to one node in the decision tree. It is described by
 * two pieces of information: the index in the array of the element under
 * consideration (for inclusion in the sequence being constructed), and the
 * largest element in the sequence so far.
 */
class SubProblem {
	int pos; // the index in the array of the element under consideration
	int cap; // the largest value in the sequence already selected

	/**
	 * Constructs a problem from the given position and cap.
	 */
	SubProblem(int pos, int cap) {
		this.pos = pos;
		this.cap = cap;
	}

	/**
	 * TODO
	 * 
	 * Returns true iff the given object equals this object, field for field. If
	 * we don't override this method, then the hash map will not be able to find
	 * previously stored problems.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this.getClass().equals(obj.getClass()))
			return pos == ((SubProblem) obj).pos && cap == ((SubProblem) obj).cap; // uses
																					// ==
																					// to
																					// determine
																					// equality
		else
			return false;
	}

	/**
	 * TODO
	 * 
	 * Returns a nicely packed version of this SubProblem. This promotes good
	 * behavior of a hash map that uses SubProblems as keys.
	 */
	@Override
	public int hashCode() {
		return pos;
	}

	/**
	 * Returns a sensible textual version of this SubProblem.
	 */
	public String toString() {
		return String.format("\n(%d, %d)", pos, cap);
	}
}
