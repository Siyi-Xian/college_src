/**
 * hw3: Problem 2 starter code.
 */

public class SetOps {

	/**
	 * Returns a list (without duplicates) containing all the items in ls1 plus
	 * all the items in ls2. Note: ls1 and ls2 are unchanged by this method.
	 */
	public static <T> List<T> union(List<T> ls1, List<T> ls2) {
		// TODO
		List<T> newLs = new DoublyLinkedList<>();
		for (T i : ls1)
			if (!newLs.contains(i))
				newLs.add(i);
		for (T i : ls2)
			if (!newLs.contains(i))
				newLs.add(i);
		return newLs;
	}

	/**
	 * Returns a list (without duplicates) of all the items which appear both in
	 * ls1 and in ls2. Note: ls1 and ls2 are unchanged by this method.
	 */
	public static <T> List<T> intersection(List<T> ls1, List<T> ls2) {
		// TODO
		List<T> newLs = new DoublyLinkedList<>();
		for (T i : ls1)
			if (ls2.contains(i))
				newLs.add(i);
		return newLs;
	}

	/**
	 * Simple testing to get you started. Add more tests of your own!
	 */
	public static void main(String... args) {
		List<String> ls1 = new DoublyLinkedList<>();
		ls1.add("ant");
		ls1.add("bat");
		ls1.add("cat");
		ls1.add("ant"); // this is a duplicate element
		ls1.add("fox");
		int n1 = ls1.size();
		System.out.println("ls1 = " + ls1);

		List<String> ls2 = new DoublyLinkedList<>();
		ls2.add("cat");
		ls2.add("dog");
		ls2.add("dog"); // this is a duplicate element
		ls2.add("emu");
		ls2.add("fox");
		ls2.add("gnu");
		int n2 = ls2.size();
		System.out.println("ls2 = " + ls2);

		List<String> ls3, ls4;
		ls3 = union(ls1, ls2);
		assert n1 == ls1.size();
		assert n2 == ls2.size();
		assert 7 == ls3.size();
		System.out.println("ls3 = " + ls3);

		ls4 = intersection(ls1, ls2);
		assert n1 == ls1.size();
		assert n2 == ls2.size();
		assert 2 == ls4.size();
		System.out.println("ls4 = " + ls4);
	}
}
