package testing;

import java.util.LinkedList;

public class Reverse {
	private LinkedList l; //Store this linked list
	
	/**
	 * Constructor : declare variable
	 * @param list : entry list
	 */
	public Reverse(LinkedList list) {
		l = list;
	}
	
	/**
	 * Return this list as String
	 */
	public String toString() {
		return l.toString();
	}
	
	/**
	 * Reverse this list
	 */
	public void reverseList() {
		for (int i = 0, j = l.size() - 1; j - i >= 1; i++, j--) {
			Object temp = l.get(i);
			l.set(i, l.get(j));
			l.set(j, temp);
		}
	}
	
	/**
	 * Get the original and reversed list as String
	 * @return tow lists in String
	 */
	public String getReverseList() {
		String text = toString();
		reverseList();
		return text += "\n" + toString() + "\n";
	}
}
