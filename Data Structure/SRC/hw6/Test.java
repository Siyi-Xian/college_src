import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void DoublyLinkedList() {
		DoublyLinkedList<Integer> xs = new DoublyLinkedList<>();
		int[] a = new int[] { 4, 3, 0, 6, 5, 7, 2, 8, 1 };
		for (int x : a)
			xs.add(x);
		// Sort xs in the natural order:
		xs.sort((x, y) -> x.compareTo(y));
		for (int i = 0; i < xs.size(); i++)
			assert i == xs.get(i);
		// Sort xs in the reverse of the natural order:
		xs.sort((x, y) -> y.compareTo(x));
		for (int i = 0; i < xs.size(); i++)
			assert xs.size() - 1 - i == xs.get(i);
		System.out.println("All tests passed...");
	}
	
	

}
