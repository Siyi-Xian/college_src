import java.awt.Color;
import java.lang.Math;

public class Circle extends Shape{
	private int radius;
	
	public Circle(Color fillColor, Color borderColor, int x, int y) {
		super(fillColor, borderColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public Circle(Color fillColor, int x, int y) {
		super(fillColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public Circle(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void getRadius() {
		radius = (int) (Math.random() * 10);
	}
	
	@Override
	double getArea() {
		// TODO Auto-generated method stub
		return Math.PI * radius * radius;
	}

	@Override
	double getPerimeter() {
		// TODO Auto-generated method stub
		return 2 * Math.PI * radius;
	}
}
