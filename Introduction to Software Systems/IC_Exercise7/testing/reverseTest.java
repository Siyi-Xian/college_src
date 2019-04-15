package testing;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class reverseTest {

	@Test
	public void test() {
		LinkedList<String> list = new LinkedList<String>(); // The entry list
		// Add elements into the entry list
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		// Built new reverse class
		Reverse r = new Reverse(list);
		
		// Test the result
		assertEquals("[A, B, C, D, E]\n[E, D, C, B, A]\n", r.getReverseList());
	}
}
