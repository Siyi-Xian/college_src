package RandomShaps;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class MyRect extends Shape{
	private int height;
	private int width;
	
	/**
	 * Constructor with fill color, border color, and position
	 * @param fillColor
	 * @param borderColor
	 * @param x
	 * @param y
	 */
	public MyRect(Color fillColor, Color borderColor, int x, int y) {
		super(fillColor, borderColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with fill color and position
	 * @param fillColor
	 * @param x
	 * @param y
	 */
	public MyRect(Color fillColor, int x, int y) {
		super(fillColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with position
	 * @param x
	 * @param y
	 */
	public MyRect(int x, int y) {
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
	 * Draw this rectangle
	 */
	@Override
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect(getX(), getY(), width, height);
		g.setColor(getFillColor());
		g.fillRect(getX(), getY(), width, height);
	}

	/**
	 * Check if the shape is out of border, if true bounce it by the border
	 */
	@Override
	void checkBorder(int FRAME_WIDTH, int FRAME_HEIGHT) {
		// TODO Auto-generated method stub
		if (getX() <= 0)                               // touch the left border
			setMovement(new Point(1, getMovement().y));
		if (getX() + width >= FRAME_WIDTH)   // touch the right border
			setMovement(new Point(-1, getMovement().y));
		if (getY() <= 0)                               // touch the up border
			setMovement(new Point(getMovement().x, 1));
		if (getY() + height >= FRAME_HEIGHT)  // touch the down border
			setMovement(new Point(getMovement().x, -1));
	}
	
	/**
	 * return this rectangle as rectangle
	 */
	@Override
	Rectangle getRect() {
		// TODO Auto-generated method stub
		return new Rectangle(super.getX(), super.getY(), width, height);
	}
}
