import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * TODO #2
 */

public class MatchBot extends TwitterBot {
	/**
	 * Constructs a MatchBot to operate on the last numTweets of the given user.
	 */
	public MatchBot(String user, int numTweets) {
		super(user, numTweets);
	}

	/**
	 * TODO
	 * 
	 * Employs the KMP string matching algorithm to add all tweets containing
	 * the given pattern to the provided list. Returns the total number of
	 * character comparisons performed.
	 */
	public int searchTweetsKMP(String pattern, List<String> ans) {
		int comps = 0;
		for (String tweet : super.tweets) {
			Result result = StringMatch.matchKMP(pattern, tweet);
			comps += result.comps;
			if (result.pos >= 0)
				ans.add(tweet);
		}
		return comps;
	}

	/**
	 * TODO
	 * 
	 * Employs the naive string matching algorithm to find all tweets containing
	 * the given pattern to the provided list. Returns the total number of
	 * character comparisons performed.
	 */
	public int searchTweetsNaive(String pattern, List<String> ans) {
		int comps = 0;
		for (String tweet : super.tweets) {
			Result result = StringMatch.matchNaive(pattern, tweet);
			comps += result.comps;
			if (result.pos >= 0)
				ans.add(tweet);
		}
		return comps;
	}

	/**
	 * TODO
	 * 
	 * Employs the Boyer Moore string matching algorithm to find all tweets
	 * containing the given pattern to the provided list. Returns the total
	 * number of character comparisons performed.
	 */
	public int searchTweetsBoyerMoore(String pattern, List<String> ans) {
		int comps = 0;
		for (String tweet : super.tweets) {
			Result result = StringMatch.matchBoyerMoore(pattern, tweet);
			comps += result.comps;
			if (result.pos >= 0)
				ans.add(tweet);
		}
		return comps;
	}

	// Please run this test in Testing.java
	/*
	public void test() {
		String handle = "realDonaldTrump", pattern = "mexico";
		MatchBot bot = new MatchBot(handle, 2000);

		// Search all tweets for the pattern.
		List<String> ansNaive = new ArrayList<>();
		int compsNaive = bot.searchTweetsNaive(pattern, ansNaive);
		List<String> ansKMP = new ArrayList<>();
		int compsKMP = bot.searchTweetsKMP(pattern, ansKMP);
		List<String> ansBoyerMoore = new ArrayList<>();
		int compsBoyerMoore = bot.searchTweetsBoyerMoore(pattern, ansKMP);

		System.out.println("naive comps = " + compsNaive + ", KMP comps = " + compsKMP + ", Boyer Moore comps = " + compsBoyerMoore);
		//System.out.println("naive comps = " + compsNaive + ", KMP comps = " + compsKMP);

		for (int i = 0; i < ansKMP.size(); i++) {
			String tweet = ansKMP.get(i);
			assert tweet.equals(ansNaive.get(i));
			System.out.println(i++ + ". " + tweet);
			System.out.println(pattern + " appears at index " + tweet.toLowerCase().indexOf(pattern.toLowerCase()));
		}

		// Do something similar for the Boyer-Moore matching algorithm.

		for (int i = 0; i < ansBoyerMoore.size(); i++) {
			String tweet = ansBoyerMoore.get(i);
			assert tweet.equals(ansNaive.get(i));
			System.out.println(i++ + ". " + tweet);
			System.out.println(pattern + " appears at index " + tweet.toLowerCase().indexOf(pattern.toLowerCase()));
		}
	}
	*/
}
