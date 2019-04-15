import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape{
	private int height;
	private int width;
	
	/**
	 * Constructor with fill color, border color, and position
	 * @param fillColor
	 * @param borderColor
	 * @param x
	 * @param y
	 */
	public Rectangle(Color fillColor, Color borderColor, int x, int y) {
		super(fillColor, borderColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with fill color and position
	 * @param fillColor
	 * @param x
	 * @param y
	 */
	public Rectangle(Color fillColor, int x, int y) {
		super(fillColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with position
	 * @param x
	 * @param y
	 */
	public Rectangle(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Set edge length of this rectangle
	 * @param x : width
	 * @param y : height
	 */
	public void setEdge(int x, int y) {
		width = x;
		height = y;
	}
	
	/**
	 * Get area of this rectangle
	 */
	@Override
	double getArea() {
		// TODO Auto-generated method stub
		return height * width;
	}
	
	/**
	 * get perimeter of this rectangle
	 */
	@Override
	double getPerimeter() {
		// TODO Auto-generated method stub
		return 2 * (height + width);
	}
	
	/**
	 * Draw this rectangle
	 */
	@Override
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect(getX(), getY(), width, height);
		g.setColor(getFillColor());
		g.fillRect(getX(), getY(), width, height);
	}
}
