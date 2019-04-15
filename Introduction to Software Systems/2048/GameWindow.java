import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame{
	private final int FRAME_WIDTH = 450;
	private final int FRAME_HEIGHT = 620;
	
	JPanel gameDriver;
	
	public GameWindow() {
		super();
		
		gameDriver = new GameDriver();
		this.setTitle("2048");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.add(gameDriver);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main (String[] args) {
		
		GameWindow gameWindow = new GameWindow();
	}
}
