import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameDriver extends JPanel implements KeyListener{
	private final int PANEL_WIDTH = 450;
	private final int PANEL_HEIGHT = 620;
	
	private PlayTable playTable;
	
	private JLabel achieve;
	private JLabel score;
	
	public GameDriver() {
		super();
		
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setFocusable(true);
		
		playTable = new PlayTable();
		
		achieve = new JLabel(playTable.getAchieve() + "");
		score = new JLabel(playTable.getScore() + "");
		
//		this.add(achieve);
//		this.add(score);
//		this.add(playTable);
		
		playTable.setLocation(25, 175);
//		achieve.setLocation(450 / 4, 25);
//		score.setLocation(450 / 4 *3, 25);
		
		playTable.setSize(400, 400);
//		achieve.setSize(225, 50);
//		score.setSize(225, 50);
		
//		achieve.setVisible(true);
//		score.setVisible(true);
		
		this.setLayout(null);
		
		this.addKeyListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// call super class paintComponent method
		// background will not be colored otherwise
		super.paintComponent(g);
		
		g.drawString(playTable.getAchieve() + "", 450 / 4, 25);
		g.drawString(playTable.getScore() + "", 450 / 4 * 3, 25);
		
//		achieve = new JLabel(playTable.getAchieve() + "");
//		score = new JLabel(playTable.getScore() + "");
		
		playTable.draw(g);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP :
				playTable.moveUp();
				break;
			case KeyEvent.VK_DOWN :
				playTable.moveDown();
				break;
			case KeyEvent.VK_LEFT :
				playTable.moveLeft();
				break;
			case KeyEvent.VK_RIGHT :
				playTable.moveRight();
				break;
			default : break;
		}
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public static void main (String[] args) {
		
		GameDriver g = new GameDriver();
	}
}
