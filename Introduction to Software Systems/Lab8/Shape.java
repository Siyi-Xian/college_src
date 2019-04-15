// we have not covered awt graphcis yet, but you do not need to know much for this assignment 
// we will be covering next week and using them for the HW
import java.awt.Color;
import java.awt.Point;

public abstract class Shape {
	private Color fillColor; 
	private Color borderColor; 
	private Boolean isFilled; 
	private Point Location;
	
	// the three constructors initialize the instance fields 
	public Shape(Color fillColor, Color borderColor, int x, int y) {
		if (!isFilled())
			setFillColor(fillColor);
		setBorderColor(borderColor);
		moveLoc(x, y);
	}
	
	// set borderColor to Black since not provided 
	public Shape(Color fillColor, int x, int y) { 
		if (!isFilled())
			setFillColor(fillColor);
		moveLoc(x, y);
	}
	
	// set fillColor to white and border color to black
	public Shape(int x, int y) { 
		moveLoc(x, y);
	}
	
	public void setFillColor(Color c) { 
		fillColor = c;
	} 
	
	public Color getFillColor() { 
		return fillColor;
	}
	
	public void setBorderColor(Color c) { 
		borderColor = c;
	} 
	
	public Color getBorderColor() { 
		return borderColor;
	} 
	
	public Point getLocation() { 
		return Location;
	}
	
	// Note: subclasses of Shape do not inherent private members so we need methods the subclasses 
	// can use to get the x and y values from the private Point instance field
	public int getX() { 
		return Location.x;
	}
	public int getY() { 
		return Location.y;
	}
	
	// if fillColor is white returns true, else returns false 
	public boolean isFilled() { 
		isFilled = fillColor == Color.white;
		return isFilled;
	}
	
	// moves location by dx and dy 
	private void moveLoc(int dx, int dy) { }
	
	abstract double getArea();
	abstract double getPerimeter();
}