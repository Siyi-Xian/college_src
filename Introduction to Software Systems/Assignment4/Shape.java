// we have not covered awt graphcis yet, but you do not need to know much for this assignment 
// we will be covering next week and using them for the HW
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class Shape{
	private Color fillColor; 
	private Color borderColor;  
	private Point Location;
	
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
	 * Move the position
	 * @param dx : new x position
	 * @param dy : new y position
	 */
	// moves location by dx and dy 
	private void moveLoc(int dx, int dy) { 
		Location = new Point(dx, dy);
	}
	
	/**
	 * Get area of this shape
	 * @return Double : area
	 */
	abstract double getArea();
	/**
	 * Get perimeter of this shape
	 * @return DOuble : perimeter
	 */
	abstract double getPerimeter();
	/**
	 * Draw this shape
	 * @param g
	 */
	abstract void draw(Graphics g);
}