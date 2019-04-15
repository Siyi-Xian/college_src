import java.awt.Color;
import java.lang.Math;

public class Rectangle extends Shape{
	private int hight;
	private int width;
	
	public Rectangle(Color fillColor, Color borderColor, int x, int y) {
		super(fillColor, borderColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public Rectangle(Color fillColor, int x, int y) {
		super(fillColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public Rectangle(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void setEdge() {
		hight = (int) (Math.random() * 10);
		width = (int) (Math.random() * 10);
	}
	
	@Override
	double getArea() {
		// TODO Auto-generated method stub
		return hight * width;
	}

	@Override
	double getPerimeter() {
		// TODO Auto-generated method stub
		return 2 * (hight + width);
	}

}
