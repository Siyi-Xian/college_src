package RandomShaps;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class ShapeDriver extends JPanel implements ActionListener{ 
	private final int FRAME_WIDTH = 600;
	private final int FRAME_HEIGHT = 600;
	
	private Timer timer;
	private Random random;
	
	ArrayList<Shape> shapes;
	
	/**
	 * Constructor
	 */
	public ShapeDriver() {
		random = new Random();
		shapes = new ArrayList<Shape>();
		
		// set up Jpanel
		setBackground(Color.CYAN);                                  // set background color of this panel
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT)); // get prefer size of this panel
		setFocusable(true);                                         // set the visible as true
		
		// add the key listener
		AddKeyListener myKeyListener = new AddKeyListener();
		addKeyListener(myKeyListener);;
		
		// the second argument to the Timer Constructor takes an ActionListener
		// the this key word informs the JVM to look inside this class for
		// the actionPerformed method that must be overridden when
		// ActionListener is implemented
		// Every tick of the clock will now run the actionPerformed method 
		timer = new Timer(25, this);
		// start the timer
		timer.start();
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
	
	public boolean checkIntersects(Shape one, Shape two) {
		return one.getRect().intersects(two.getRect());
	}
	
	// Method that must be implemented since the class implements ActionListener
	public void actionPerformed(ActionEvent e) {
		// move each circle
		// check if circle is in bounds, and bounce off the borders if need be
		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).move();
			shapes.get(i).checkBorder(FRAME_WIDTH, FRAME_HEIGHT);
			
			// check if circle hits another circle
			// bounce circles off each other and swap colors
			for (int j = i + 1; j < shapes.size(); j++)
				if (checkIntersects(shapes.get(i), shapes.get(j))) {
					shapes.get(i).bounceOff(); // change the direction of first circle
					shapes.get(j).bounceOff(); // change the direction of second circle
					
					// exchange two circles' color
					Color temp = shapes.get(i).getFillColor();
					shapes.get(i).setFillColor(shapes.get(j).getFillColor());
					shapes.get(j).setFillColor(temp);
				}
			
			// call repaint 
			repaint();
		}
		
		
	}
	
	// add the key listener as an inner class
	class AddKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			// get a randomly fill color
			Color fillColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			// get a randomly x and y position
			int x = random.nextInt(FRAME_WIDTH);
			int y = random.nextInt(FRAME_HEIGHT);
			
			switch (e.getKeyCode()) {
				case KeyEvent.VK_C :                                            // draw a randomly circle when press "c"
					Circle circle = new Circle(fillColor, x, y);                // build a new circle
					circle.setRadius(random.nextInt(20));                       // get a randomly radius less than 20
					shapes.add(circle);                                         // add this circle to the list
					break;
				case KeyEvent.VK_R :                                            // draw a randomly rectangle when press "r"
					MyRect rectangle = new MyRect(fillColor, x, y);             // build a new rectangle
					rectangle.setEdge(random.nextInt(40), random.nextInt(40));  // get a randomly width and height
					shapes.add(rectangle);                                      // add this rectangle to the list
					break;
				case KeyEvent.VK_S :                                            // draw a randomly square when press "s"
					Square square = new Square(fillColor, x, y);                // build a new square
					square.setEdge(random.nextInt(40));                         // get a randomly edge length
					shapes.add(square);                                         // add this square to the list
					break;
				default : break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}
		
	}
}
