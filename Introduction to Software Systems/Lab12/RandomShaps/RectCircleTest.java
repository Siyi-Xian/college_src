package RandomShaps;

import static org.junit.Assert.*;

import org.junit.Test;

public class RectCircleTest {

	@Test
	public void test() {
		MyRect r = new MyRect(10, 10);
		Circle c = new Circle(10, 30);
		
		r.setEdge(10, 20);
		c.setRadius(5);
		
		ShapeDriver sd = new ShapeDriver();
		
		assertEquals(false, sd.checkIntersects(r, c));
	}

}
