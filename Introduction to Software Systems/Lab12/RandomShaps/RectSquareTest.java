package RandomShaps;

import static org.junit.Assert.*;

import org.junit.Test;

public class RectSquareTest {

	@Test
	public void test() {
		MyRect r = new MyRect(10, 10);
		Square s = new Square(10, 20);
		
		r.setEdge(10, 20);
		s.setEdge(20);
		
		ShapeDriver sd = new ShapeDriver();
		
		assertEquals(true, sd.checkIntersects(r, s));
	}

}
