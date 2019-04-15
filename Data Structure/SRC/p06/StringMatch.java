import java.util.HashSet;
import java.util.Set;

/**
 * TODO #1
 */

public class StringMatch {

	/**
	 * TODO
	 * 
	 * Returns the result of running the naive algorithm to match pattern in
	 * text.
	 */
	public static Result matchNaive(String pattern, String text) {
		int comps = 0, i = 0, j = 0;
		while (i < text.length() && j < pattern.length()) {
			if (text.charAt(i) == pattern.charAt(j)) {
				i++;
				j++;
				comps++;
			} else {
				i = i - j + 1;
				j = 0;
				comps++;
			}
		}
		if (j == pattern.length())
			return new Result(i - j, comps);

		return new Result(-1, comps);
	}

	/**
	 * TODO
	 * 
	 * Populates flink with the failure links for the KMP machine associated
	 * with the given pattern, and returns the cost in terms of the number of
	 * character comparisons.
	 */
	public static int buildKMP(String pattern, int[] flink) {
		int comps = 0, k = -1, j = 0;
		int pLen = pattern.length();
		flink[0] = -1;
		while (j < pLen - 1) {
			comps++;
			if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
				k++;
				j++;
				flink[j] = k;
			} else {
				k = flink[k];
			}
		}

		return comps;
	}

	/**
	 * TODO
	 * 
	 * Returns the result of running the KMP machine specified by flink (built
	 * for the given pattern) on the text.
	 */
	public static Result runKMP(String pattern, String text, int[] flink) {
		int comps = 0, i = 0, j = 0;
		while (i < text.length() && j < pattern.length()) {
			if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
				i++;
				j++;
				comps++;
			} else {
				j = flink[j];
				comps++;
			}
		}
		if (j == pattern.length())
			return new Result(i - j, comps);

		return new Result(-1, comps);
	}

	/**
	 * Returns the result of running the KMP algorithm to match pattern in text.
	 * The number of comparisons includes the cost of building the machine from
	 * the pattern.
	 */
	public static Result matchKMP(String pattern, String text) {
		int m = pattern.length();
		int[] flink = new int[m + 1];
		int comps = buildKMP(pattern, flink);
		Result ans = runKMP(pattern, text, flink);
		return new Result(ans.pos, comps + ans.comps);
	}

	/**
	 * TODO
	 * 
	 * Populates delta1 with the shift values associated with each character in
	 * the alphabet. Assume delta1 is large enough to hold any ASCII value.
	 */
	public static void buildDelta1(String pattern, int[] delta1) {
		int len = pattern.length();
		for (int i = 0; i < delta1.length; i++)
			delta1[i] = len;
		for (int i = 0; i < len; i++)
			if (delta1[pattern.charAt(len - i - 1)] == len)
				delta1[pattern.charAt(len - i - 1)] = i;
	}

	/**
	 * TODO
	 * 
	 * Returns the result of running the simplified Boyer-Moore algorithm using
	 * the delta1 table from the pre-processing phase.
	 */
	public static Result runBoyerMoore(String pattern, String text, int[] delta1) {
		int comps = 1, i = pattern.length() - 1, j = pattern.length() - 1;
		if (i < 0 || j < 0)
			return new Result(0, comps);
		while (i < text.length() && i >= 0) {
			while (j >= 0) {
				comps++;
				if (text.charAt(i - (pattern.length() - j - 1)) != pattern.charAt(j)) {
					i += Math.max(1, delta1[text.charAt(i - (pattern.length() - j - 1))] - (pattern.length() - j - 1));
					j = pattern.length() - 1;
					break;
				} else if (j == 0)
					return new Result(i - pattern.length() + 1, comps);
				else
					j--;
			}
		}

		return new Result(-1, comps);
	}

	/**
	 * Returns the result of running the simplified Boyer-Moore algorithm to
	 * match pattern in text.
	 */
	public static Result matchBoyerMoore(String pattern, String text) {
		int[] delta1 = new int[Integer.MAX_VALUE/10000];
		buildDelta1(pattern, delta1);
		return runBoyerMoore(pattern, text, delta1);
	}

}
