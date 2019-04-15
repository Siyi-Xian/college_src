package RandomShaps;

import static org.junit.Assert.*;

import org.junit.Test;

public class SquareCircleTest {

	@Test
	public void test() {
		Square s = new Square(10, 20);
		Circle c = new Circle(10, 30);
		
		s.setEdge(20);
		c.setRadius(5);
		
		ShapeDriver sd = new ShapeDriver();
		
		assertEquals(true, sd.checkIntersects(s, c));
	}

}
