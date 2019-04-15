import java.awt.*;

import javax.swing.*;

import org.w3c.dom.Text;

public class Square{
	private final int SQUARE_WIDTH = 90;
	private final int SQUARE_HEIGHT = 90;
	
	private Color fillColor;
	private int value;
	private int pos;
	
	public Square(int num, int p) {
		setValue(num);
		setPosition(p);
		setColor(num);
	}
	
	public void setValue (int v) {
		value = v;
	}
	
	public int getValue () {
		return value;
	}
	
	public void setPosition (int p) {
		pos = p;
	}
	
	public int getPosition () {
		return pos;
	}
	
	public void setColor (int num) {
		switch (num) {
		case 0 :
			fillColor = new Color(0xffffffff);
			break;
        case 2 :
        	fillColor = new Color(0xffeee4da);
			break;
        case 4 :
        	fillColor = new Color(0xffede0c8);
			break;
        case 8 :
        	fillColor = new Color(0xfff2b179);
			break;
        case 16 :
        	fillColor = new Color(0xfff59563);
			break;
        case 32 :
        	fillColor = new Color(0xfff67c5f);
			break;
        case 64 :
        	fillColor = new Color(0xfff65e3b);
			break;
        case 128 :
        	fillColor = new Color(0xffedcf72);
			break;
        case 256 :
        	fillColor = new Color(0xffedcc61);
			break;
        case 512 :
        	fillColor = new Color(0xffedc850);
			break;
        case 1024 :
        	fillColor = new Color(0xffedc53f);
			break;
        case 2048 :
        	fillColor = new Color(0xffedc22e);
			break;
        default :
        	fillColor = new Color(0xff3c3a32);
			break;
        }
	}
	
	public Color getColor () {
		return fillColor;
	}
	
	public JPanel getSquare () {
		JPanel square = new JPanel();
		JLabel v = new JLabel();
		int x = getPosition() % 4 * 100 + 5;
		int y = getPosition() / 4 * 100 + 5;
		
		if (getValue() != 0)
			v = new JLabel(getValue() + "");
		v.setVisible(true);
		
		square.setSize(SQUARE_WIDTH, SQUARE_HEIGHT);
		square.setBackground(getColor());
		square.add(v);
		square.setLocation(x, y);
		
		return square;
	}
	
	private void drawRect(Graphics g, int x, int y) {
		g.drawRect(x, y, SQUARE_WIDTH, SQUARE_HEIGHT);
		g.setColor(getColor());
//		g.fillRect(x, y, SQUARE_WIDTH, SQUARE_HEIGHT);
	}
	
	private void drawText(Graphics g, int x, int y) {
		g.drawString(getValue() + "", x + 45, y + 45);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier", Font.BOLD,40));
	}
	
	public void draw(Graphics g) {
		int x = getPosition() % 4 * 100 + 30;
		int y = getPosition() / 4 * 100 + 180;
		
	}
}
