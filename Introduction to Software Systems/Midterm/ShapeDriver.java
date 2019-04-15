import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Driver program for random shape generator app
 */
public class ShapeDriver extends JPanel implements ActionListener {
	public final int FRAME_WIDTH = 600;
	public final int FRAME_HEIGHT = 600;
	private Random random;
	
	// List of all shapes
	ArrayList<Shape> shapes;
	
	// A timer to control start time and moving
	private Timer timer;
	
	/**
	 * Constructor
	 */
	public ShapeDriver() {
		super();
		// initialize fields need to declare and use
		random = new Random();
		shapes = new ArrayList<Shape>();
		
		// set up Jpanel
		setBackground(Color.CYAN);                                  // set background color of this panel
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT)); // get prefer size of this panel
		setFocusable(true);                                         // set focusable as true
		
		// add the KeyListiner
		MyKeyListenner myKeyListener = new MyKeyListenner();
		addKeyListener(myKeyListener);
		
		// set the timer
		timer = new Timer(1000 / 25, this);                         // make all the timer delay as 1/25 second
		timer.setInitialDelay(10 * 1000);                           // set the initial delay as 10 seconds, 
				                                                    // in order to make the shapes move 10 seconds after draw the first one. 
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// let all shapes move toward their own direction
		for (int i = 0; i < shapes.size(); i++)
			switch (shapes.get(i).getDirections()) {
				case 0 :                             // Move Up
					shapes.get(i).moveLoc(0, -1);    // Y coordinate minus 1
					break;
				case 1 :                             // Move Right_Up
					shapes.get(i).moveLoc(1, -1);    // X coordinate add 1, Y coordinate minus 1
					break;
				case 2 :                             // Move Right
					shapes.get(i).moveLoc(1, 0);     // X coordinate add 1
					break;
				case 3 :                             // Move Right_Down
					shapes.get(i).moveLoc(1, 1);     // X coordinate add 1, Y coordinate add 1
					break;
				case 4 :                             // Move Down
					shapes.get(i).moveLoc(0, 1);     // Y coordinate add 1
					break;
				case 5 :                             // Move Left_Down
					shapes.get(i).moveLoc(-1, 1);    // X coordinate minus 1, Y coordinate add 1
					break;
				case 6 :                             // Move Left
					shapes.get(i).moveLoc(-1, 0);    // X coordinate minus 1
					break;
				case 7 :                             // Move Left_Up
					shapes.get(i).moveLoc(-1, -1);   // X coordinate minus 1, Y coordinate minus 1
					break;
				default : break;
			}
		// repaint all shapes to show the movement
		repaint();
	}
	
	// add an inner class as mouse listener
	public class MyMouseClicker implements MouseListener {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			// get a randomly fill color
			Color fillColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			// get a random size
			int width = random.nextInt(20);
			int height = random.nextInt(20);
			// get a randomly x and y position
			int x = e.getX() - width;
			int y = e.getY() - height;
			// get randomly type of shapes
			int s = random.nextInt(3);
			// get randomly direction
			int d = random.nextInt(8);
			
			// choose which shape to draw
			switch (s) {
				case 0 :                                                       // Ellipse :
					Ellipse ellipse = new Ellipse(fillColor, x, y);            // built a new ellipse
					ellipse.setEdge(height * 2, width * 2);                    // set the size of this new ellipse
					ellipse.setDirections(d);                                  // set the direction of this ellipse
					shapes.add(ellipse);                                       // add this ellipse to the array list
					break;
				case 1 :                                                       // Circle :
					Circle circle = new Circle(fillColor, x, y);               // built a new circle
					circle.setEdge(width * 2);                                 // set the size of this new circle
					circle.setDirections(d);                                   // set the direction of this circle
					shapes.add(circle);                                        // add this circle to the array list
					break;
				case 2 :                                                       // Rectangle :
					Rectangle rectangle = new Rectangle(fillColor, x, y);      // built a new rectangle
					rectangle.setEdge(height * 2, width * 2);                  // set the size of this new rectangle
					rectangle.setDirections(d);                                // set the direction of this rectangle
					shapes.add(rectangle);                                     // add this rectangle to the array list
					break;
				case 3 :                                                       // Square :
					Square square = new Square(fillColor, x, y);               // built a new square
					square.setEdge(width * 2);                                 // set the size of this new square
					square.setDirections(d);                                   // set the direction of this square
					shapes.add(square);                                        // add this square to the array list
					break;
				default : break;
			}
			
			// draw all the shapes in order to show all update
			repaint();
			// start the timer, and make sure it will start first time when the first shape has been drawn
			if (!timer.isRunning())  // make sure that the timer is not start multiply
				timer.start();
		}
		
		// override all methods in the interface
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}
	
	// add an inner class as key listener
	public class MyKeyListenner implements KeyListener {
		// A mouse listener
		MyMouseClicker myMouseListener = new MyMouseClicker();
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE :                    // When pressed 'SPACEBAR', add the mouse listener to enable mouse clicks.
					if (getMouseListeners().length == 0)    // make sure that there are only one mouse listener in this components
						addMouseListener(myMouseListener);
					break;
				case KeyEvent.VK_ESCAPE :                   // When pressed 'ESC', remove the mouse listener to disable mouse clicks.
					removeMouseListener(myMouseListener);
					break;
				default : break;
			}
		}
		
		// override all methods in the interface
		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
	}
}