import java.awt.Point;
import java.awt.Rectangle;

public class Square extends Rectangle {
	/**
	 * Constructor
	 * @param x : the x-coordinate of square center
	 * @param y : the y-coordinate of square center
	 * @param l : the side length of square
	 */
	public Square (int x, int y, int l) {
		this.setLocation(x - l / 2, y - l / 2);
		this.setSize(l, l);
	}
	
	/**
	 * Get the area of square
	 * @return the area as double
	 */
	private double getArea () {
		return this.height * this.width;
	}
	
	/**
	 * Get the coordinate of square center
	 * @return the center as Point
	 */
	private Point getCenter () {
		return new Point(this.x + this.width / 2, this.y + this.height / 2);
	}
	
	/**
	 * Get side length of square
	 * @return the side length as double
	 */
	private double getSideLength () {
		return this.width;
	}
	
	/**
	 * Returns a String representing this Square and its values.
     * @return a String representing this Square object's center coordinate, side length, and area. 
	 */
	public String toString () {
		return  getClass().getName() + "[x=" + this.getCenter().x + ",y=" + this.getCenter().y + ",SideLength=" + this.getSideLength() + ",Area=" + this.getArea() + "]";
	}
	
	public static void main (String[] args) {
		Square s = new Square (2, 2, 2);
		
		System.out.println(s.toString());
	}
}
