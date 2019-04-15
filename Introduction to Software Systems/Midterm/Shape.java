import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class Shape{
	private Color fillColor; 
	private Color borderColor;  
	private Point location;
	private int height;
	private int width;
	private int directions;
	
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
		location = new Point(0, 0);
		moveLoc(x, y);
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
		location = new Point(0, 0);
		moveLoc(x, y);
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
		location = new Point(0, 0);
		moveLoc(x, y);
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
		return location;
	}
	
	// Note: subclasses of Shape do not inherent private members so we need methods the subclasses 
	// can use to get the x and y values from the private Point instance field
	/**
	 * Get x position
	 * @return Integer : x position
	 */
	public int getX() { 
		return location.x;
	}
	/**
	 * Get y position
	 * @return Integer : y position
	 */
	public int getY() { 
		return location.y;
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
	 * Move the position
	 * @param dx : new x position
	 * @param dy : new y position
	 */
	// moves location by dx and dy 
	public void moveLoc(int dx, int dy) { 
		location = new Point(getX() + dx, getY() + dy);
	}
	
	/**
	 * Set the size of this shape
	 * @param w : width of this shape
	 * @param h : height of this shape
	 */
	public void setEdge(int w, int h) {
		width = w;
		height = h;
	}
	
	/**
	 * Get height of this shape
	 * @return height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Get width of this shape
	 * @return width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Set the moving direction of this shape
	 * @param d : direction number
	 */
	public void setDirections(int d) {
		directions = d;
	}
	 /**
	  * Get direction of this shape
	  * @return direction
	  */
	public int getDirections() {
		return directions;
	}
	
	/**
	 * Draw this shape
	 * @param g : Graphics
	 */
	abstract void draw(Graphics g);
}