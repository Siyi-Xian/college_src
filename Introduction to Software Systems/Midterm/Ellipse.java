import java.awt.Color;
import java.awt.Graphics;

public class Ellipse extends Shape{
	/**
	 * Constructor with fill color, border color, and position
	 * @param fillColor
	 * @param borderColor
	 * @param x
	 * @param y
	 */
	public Ellipse(Color fillColor, Color borderColor, int x, int y) {
		super(fillColor, borderColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with fill color and position
	 * @param fillColor
	 * @param x
	 * @param y
	 */
	public Ellipse(Color fillColor, int x, int y) {
		super(fillColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with position
	 * @param x
	 * @param y
	 */
	public Ellipse(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Draw this ellipse
	 */
	@Override
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawOval(getX(), getY(), getHeight(), getWidth());
		g.setColor(getFillColor());
		g.fillOval(getX(), getY(), getHeight(), getWidth());
	}
}
