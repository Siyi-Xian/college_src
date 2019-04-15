import java.util.HashMap;

/**
 * TODO: Complete the implementation of this class.
 */

public class FrequencyTable extends HashMap<Character, Integer> {
	/**
	 * Constructs an empty table.
	 */
	public FrequencyTable() {
		super();
	}

	/**
	 * TODO: Make use of get() and put().
	 * 
	 * Constructs a table of character counts from the given text string.
	 */
	public FrequencyTable(String text) {
		for (int i = 0; i < text.length(); i++)
			this.put(text.charAt(i), this.get(text.charAt(i)) + 1);
	}

	/**
	 * TODO
	 * 
	 * Returns the count associated with the given character. In the case that
	 * there is no association of ch in the map, return 0.
	 */
	@Override
	public Integer get(Object ch) {
		if (this.containsKey(ch))
			return super.get(ch);
		else
			return 0;
	}
}
