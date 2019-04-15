package RandomShaps;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Shape{
	private Color fillColor; 
	private Color borderColor; 
	private Point Location;
	private Point movement;
	
	/**
	 * Constructor with fill color, border color, and position
	 * @param fillColor
	 * @param borderColor
	 * @param x
	 * @param y
	 */
	// the three constructors initialize the instance fields 
	public Shape(Color fillColor, Color borderColor, int x, int y) {
		if (!isFilled())
			setFillColor(fillColor);
		setBorderColor(borderColor);
		moveLoc(x, y);
		setMovement(new Point(1, 1));
	}
	
	/**
	 * Constructor with fill color and position
	 * @param fillColor
	 * @param x
	 * @param y
	 */
	// set borderColor to Black since not provided 
	public Shape(Color fillColor, int x, int y) { 
		if (!isFilled())
			setFillColor(fillColor);
		setBorderColor(Color.BLACK);
		moveLoc(x, y);
		setMovement(new Point(1, 1));
	}
	
	/**
	 * Constructor with position
	 * @param x
	 * @param y
	 */
	// set fillColor to white and border color to black
	public Shape(int x, int y) { 
		setFillColor(Color.BLACK);
		setBorderColor(Color.BLACK);
		moveLoc(x, y);
		setMovement(new Point(1, 1));
	}
	
	/**
	 * Set fill color
	 * @param c : fill color
	 */
	public void setFillColor(Color c) { 
		fillColor = c;
	} 
	
	/**
	 * Get fill color
	 * @return Color : fill color
	 */
	public Color getFillColor() { 
		return fillColor;
	}
	
	/**
	 * Set border color
	 * @param c : border color
	 */
	public void setBorderColor(Color c) { 
		borderColor = c;
	} 
	
	/**
	 * Get border color
	 * @return Color : border color
	 */
	public Color getBorderColor() { 
		return borderColor;
	} 
	
	/**
	 * Get location
	 * @return Location : location
	 */
	public Point getLocation() { 
		return Location;
	}
	
	// Note: subclasses of Shape do not inherent private members so we need methods the subclasses 
	// can use to get the x and y values from the private Point instance field
	/**
	 * Get x position
	 * @return Integer : x position
	 */
	public int getX() { 
		return Location.x;
	}
	/**
	 * Get y position
	 * @return Integer : y position
	 */
	public int getY() { 
		return Location.y;
	}
	
	/**
	 * Check if it is filled?
	 * @return Boolean
	 */
	// if fillColor is white returns true, else returns false 
	public boolean isFilled() { 
		return fillColor == Color.white;
	}
	
	/**
	 * Get the direction of this shape
	 * @return direction
	 */
	public Point getMovement() {
		return movement;
	}
	
	/**
	 * Move the position
	 * @param dx : new x position
	 * @param dy : new y position
	 */
	// moves location by dx and dy 
	public void moveLoc(int dx, int dy) { 
		Location = new Point(dx, dy);
	}
	
	/**
	 * move this shape follow its own direction
	 */
	public void move() {
		moveLoc(getX() + getMovement().x, getY() + getMovement().y);
	}
	
	/**
	 * Set the direction 
	 * @param m : direction
	 */
	public void setMovement(Point m) {
		movement = m;
	}
	
	/**
	 * change the direction of this shape to an opposite way
	 */
	public void bounceOff() {
		setMovement(new Point(-movement.x, -movement.y));
	}
	
	/**
	 * Draw this shape
	 * @param g
	 */
	abstract void draw(Graphics g);
	/**
	 * Check if the shape is out of border, if true bounce it by the border
	 * @param FRAME_WIDTH : horizatal border
	 * @param FRAME_HEIGHT : vertical border
	 */
	abstract void checkBorder(int FRAME_WIDTH, int FRAME_HEIGHT);
	/**
	 * Get this shape as rectangle
	 * @return shape as rectangle
	 */
	abstract Rectangle getRect();
}