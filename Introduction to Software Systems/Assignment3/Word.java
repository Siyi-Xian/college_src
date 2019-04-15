////////////////////////////////////////////////////////////////////////////////////
//
//  H212 Fall 16
//  Homework 3 
//  
//  Due: 9/24/16 11:59 PM
//
//  Author  Siyi Xian    siyixian
//  Last Edited:  22 February 2017 
//     
//               
//////////////////////////////////////////////////////////////////////////////////
/*
 * Once all the bugs are fixed the program should 
 * generate the following output for inputs "mississippi" and "Trending"
 * output:
 * First repeating char in mississippi is: i 
 * First repeating char in Trending is: n 
 */
public class Word {
	/*
	 * Constructs an analyzer for a given word.
	 * 
	 * @param aWord the word to be analyzed
	 */

	public Word(String _word) {
		word = _word;
	}

	/*
	 * Gets the first repeating character. For example, given "hello" as input
	 * the function returns "l". The function returns 0 if no characters are
	 * repeating
	 */
	public char firstRepeatCharacter() {
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			for (int j = i + 1; j < word.length(); j++) // We need to add a
														// for-loop to search
														// every character after
														// ch.
				if (find(ch, j))
					return ch;
		}
		return 0;
	}

	/*
	 * returns true if input character 'c' is equal to the char at position
	 * 'pos' else returns false
	 */
	private boolean find(char c, int pos) // We only need to check if this
											// position's character is equal to
											// c,
	{ // so it is not necessary to use a for-loop.
		if (word.charAt(pos) == c)
			return true;
		return false;
	}

	private String word;

	public static void main(String args[]) {
		Word[] words = new Word[2];

		words[0] = new Word("mississippi"); // We need to use constructor to
											// change the value because it is
											// non-static
		words[1] = new Word("Trending");

		// find the first repeating char for the two words in the array
		char ch = words[0].firstRepeatCharacter(); // We need to deal with the
													// first value in words, so
													// we need to use word[0]
		System.out.println("First repeating char in " + words[0].word + " is: " + ch);

		ch = words[1].firstRepeatCharacter(); // we need to deal with the second
												// value in words, so we need to
												// use word[1].
												// We still need to change the
												// value of ch, or we still
												// print out the answer of last
												// step
		System.out.println("First repeating char in " + words[1].word + " is: " + ch);
	}
}