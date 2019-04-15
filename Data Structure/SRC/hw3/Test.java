import static org.junit.Assert.*;

import java.util.Iterator;

public class Test {

	@org.junit.Test
	public void testDoubleLinkedList() {
		DoublyLinkedList<Integer> xs = new DoublyLinkedList<>();
		int[] a = new int[] { 4, 3, 6, 5, 7, 8 };
		for (int x : a)
			xs.add(x);
		assert 6 == xs.size();
		for (int i = 0; i < a.length; i++)
			assert xs.get(i) == a[i];
		assert !xs.contains(null);
		for (int x : a)
			assert xs.contains(x);
		assert "(4 3 6 5 7 8)".equals(xs.toString());
		assert xs.remove(0) == 4;
		assert xs.remove(1) == 6;
		assert 4 == xs.size();
		assert "(3 5 7 8)".equals(xs.toString());
		while (!xs.isEmpty())
			xs.remove(xs.size() - 1);
		assert 0 == xs.size();
		for (int x : a)
			xs.add(x);
		for (int x : xs)
			assert xs.contains(x);
		Iterator<Integer> it = xs.iterator();
		while (it.hasNext())
			if (it.next() % 2 == 0)
				it.remove();
		assert 3 == xs.size();
		assert "(3 5 7)".equals(xs.toString());
		System.out.println("All tests passed...");

	}

	@org.junit.Test
	public void testSetOps() {
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
		ls3 = SetOps.union(ls1, ls2);
		assert n1 == ls1.size();
		assert n2 == ls2.size();
		assert 7 == ls3.size();
		System.out.println("ls3 = " + ls3);

		ls4 = SetOps.intersection(ls1, ls2);
		assert n1 == ls1.size();
		assert n2 == ls2.size();
		assert 2 == ls4.size();
		System.out.println("ls4 = " + ls4);
	}

	@org.junit.Test
	public void testDeque() {
		Deque<Integer> de = new Deque<Integer>();
		int[] a = new int[] { 4, 3, 6, 5, 7, 8 };
		for (int i : a)
			de.push(i);
		assert "(8 7 5 6 3 4)".equals(de.toString());
		de.pop();
		assert "(7 5 6 3 4)".equals(de.toString());
		de.inject(9);
		assert "(7 5 6 3 4 9)".equals(de.toString());
		de.eject();
		assert "(7 5 6 3 4)".equals(de.toString());
	}

	@org.junit.Test
	public void testSelfAdjustingList() {
		SelfAdjustingList<Integer> xs = new SelfAdjustingList<>();
		for (int x = 1; x <= 10; x++)
			xs.add(x);
		for (int i = 0; i < xs.size(); i++)
			assert 10 - i == xs.get(i);
		for (int i = 0; i < xs.size(); i++) {
			int x = xs.get(i);
			assert x == xs.find(i);
		}
		for (int i = 0; i < xs.size(); i++) {
			int x = xs.find(i);
			assert x == xs.get(0);
		}
		System.out.println("All tests passed...");
	}
}
