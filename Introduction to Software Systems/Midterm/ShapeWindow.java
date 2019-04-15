import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Main application for random shape generator app
 */
public class ShapeWindow extends JFrame {
	private final Dimension FRAME_SIZE = new Dimension(600, 600);
	
	JPanel shapeDriver;
	
	/**
	 * Constructor and run method
	 */
	public ShapeWindow() {
		super();
		// set up the frame
		shapeDriver = new ShapeDriver();                // add a new panel
		setTitle("Random Shaps");                       // set the frame title
		setSize(FRAME_SIZE);                            // set the frame size
		add(shapeDriver);                               // add the panel just created to this frame
		setVisible(true);                               // make the frame visible
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // let the frame able to close
	}
	
	public static void main(String[] args) {
		// run main application 
		JFrame shapeWindow = new ShapeWindow();
	}
}
