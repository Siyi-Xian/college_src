package TortoiseHareRace;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class THWindow extends JFrame {
	private final Dimension FRAME_SIZE = new Dimension(360, 300);
	
	JPanel thDriver;
	
	/**
	 * Constructor and run method
	 */
	public THWindow() {
		super();
		// set up the frame
		thDriver = new THDriver(FRAME_SIZE);            // add a new panel
		setTitle("Race of Tortoise and Hare");          // set the frame title
		setSize(FRAME_SIZE);                            // set the frame size
		add(thDriver);                                  // add the panel just created to this frame
		setVisible(true);                               // make the frame visible
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // let the frame able to close
	}
	
	public static void main(String[] args) {
		// run main application 
		JFrame shapeWindow = new THWindow();
	}
}
