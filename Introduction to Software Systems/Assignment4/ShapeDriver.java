////////////////////////////////////////////////////////////////////////////////////
//
//  H212 Fall 16
//
//  Homework 4 Template
//  @Author  Siyi Xian
//
///////////////////////////////////////////////////////////////////////////////////

// These are the imports I used 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/*
 * Driver program for random shape generator app
 */
public class ShapeDriver extends JPanel implements KeyListener {
	public final int FRAME_WIDTH = 600;
	public final int FRAME_HEIGHT = 600;
	private Random random;

	// List of all shapes
	ArrayList<Shape> shapes;

	/**
	 * Constructor
	 */
	public ShapeDriver() {
		super();
		// initialize fields need to declare and use
		random = new Random();
		shapes = new ArrayList<Shape>();

		// set up Jpanel
		setBackground(Color.CYAN); // set background color of this panel
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT)); // get
																	// prefer
																	// size of
																	// this
																	// panel
		setFocusable(true); // set focusable as true

		// add the KeyListiner
		addKeyListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		// call super class paintComponent method
		// background will not be colored otherwise
		super.paintComponent(g);

		// draw all shapes in the list
		for (int i = 0; i < shapes.size(); i++)
			shapes.get(i).draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// get a randomly fill color
		Color fillColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		// get a randomly x and y position
		int x = random.nextInt(FRAME_WIDTH);
		int y = random.nextInt(FRAME_HEIGHT);

		switch (e.getKeyCode()) {
		case KeyEvent.VK_C: // draw a randomly circle when press "c"
			Circle circle = new Circle(fillColor, x, y); // build a new circle
			circle.setRadius(random.nextInt(20)); // get a randomly radius less
													// than 20
			shapes.add(circle); // add this circle to the list
			break;
		case KeyEvent.VK_R: // draw a randomly rectangle when press "r"
			Rectangle rectangle = new Rectangle(fillColor, x, y); // build a new
																	// rectangle
			rectangle.setEdge(random.nextInt(40), random.nextInt(40)); // get a
																		// randomly
																		// width
																		// and
																		// height
			shapes.add(rectangle); // add this rectangle to the list
			break;
		case KeyEvent.VK_S: // draw a randomly square when press "s"
			Square square = new Square(fillColor, x, y); // build a new square
			square.setEdge(random.nextInt(40)); // get a randomly edge length
			shapes.add(square); // add this square to the list
			break;
		default:
			break;
		}

		// repaint the JPanel in order to show the new shape
		repaint();
	}

	// do not need to do anything with this method from KeyListener
	// but must have since this class implements KeyListiner
	@Override
	public void keyReleased(KeyEvent e) {
	}

	// do not need to do anything with this method from KeyListener
	// but must have since this class implements KeyListiner
	@Override
	public void keyTyped(KeyEvent e) {
	}
}