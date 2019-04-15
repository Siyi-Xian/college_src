import java.awt.*;
import java.lang.Math;

public class Circle extends Shape{
	private int radius;
	
	/**
	 * Constructor with fill color, border color, and position
	 * @param fillColor
	 * @param borderColor
	 * @param x
	 * @param y
	 */
	public Circle(Color fillColor, Color borderColor, int x, int y) {
		super(fillColor, borderColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with fill color and position
	 * @param fillColor
	 * @param x
	 * @param y
	 */
	public Circle(Color fillColor, int x, int y) {
		super(fillColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with position
	 * @param x
	 * @param y
	 */
	public Circle(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Set radius of this circle
	 * @param r : the value of radius
	 */
	public void setRadius(int r) {
		radius = r;
	}
	
	/**
	 * Get area of this circle
	 */
	@Override
	double getArea() {
		// TODO Auto-generated method stub
		return Math.PI * radius * radius;
	}
	
	/**
	 * Get perimeter of this circle
	 */
	@Override
	double getPerimeter() {
		// TODO Auto-generated method stub
		return 2 * Math.PI * radius;
	}
	
	/**
	 * Draw this circle
	 */
	@Override
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawOval(getX(), getY(), radius * 2, radius * 2);
		g.setColor(getFillColor());
		g.fillOval(getX(), getY(), radius * 2, radius * 2);
	}
}
