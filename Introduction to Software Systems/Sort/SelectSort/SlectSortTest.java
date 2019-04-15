package SelectSort;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class SlectSortTest {

	@Test
	public void test() {
		SlectSort s = new SlectSort();
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(5);
		l.add(4);
		l.add(7);
		
		assertEquals("[1, 4, 5, 7]", s.sort(l, "ascending").toString());
	}

}
