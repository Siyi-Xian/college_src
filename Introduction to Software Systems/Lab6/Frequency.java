public class Frequency {
	private String word;
	private int frequency;
	
	/**
	 * Constructor, setter method
	 * @param name : the word's name
	 * @param num : the frequency of this word
	 */
	public Frequency (String name, int num) {
		this.word = name;
		this.frequency = num;
	}
	
	/**
	 * Getter method of word
	 * @return word
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Getter method of frequency
	 * @return frequency
	 */
	public int getFrequency() {
		return frequency;
	}
	
	/**
	 * Transfer word and frequency to String type
	 * Connect with a semicolon and a space
	 * @return the string type of this Frequency class
	 */
	public String toString() {
		return word + ": " + (frequency + "");
	}
}
