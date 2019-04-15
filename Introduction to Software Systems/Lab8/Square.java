import java.awt.Color;

public class Square extends Rectangle{
	private int hight;
	private int width;
	
	public Square(Color fillColor, Color borderColor, int x, int y) {
		super(fillColor, borderColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public Square(Color borderColor, int x, int y) {
		super(borderColor, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public Square(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void setEdge() {
		hight = (int) (Math.random() * 10);
		width = hight;
	}
}
