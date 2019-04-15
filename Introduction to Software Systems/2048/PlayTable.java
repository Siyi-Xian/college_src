import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class PlayTable extends JPanel{
	private Square[] squares;
	
	private Random random;
	
	public PlayTable () {
		random = new Random();
		squares = new Square[16];
		
		setBegining();
//		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
	}
	
	private void setBegining () {
		for (int i = 0; i < 16; i++)
			squares[i] = new Square(0, i);
	}
	
	public int getAchieve () {
		int max = 0;
		
		for (int i = 0; i < 16; i++)
			max = Math.max(max, squares[i].getValue());
		
		return max;
	}
	
	public int getScore () {
		int sum = 0;
		
		for (int i = 0; i < 16; i++)
			sum += squares[i].getValue();
		
		return sum;
	}
	
	private void addOne() {
		int nextPos = random.nextInt(16);
		if (squares[nextPos].getValue() == 0)
			squares[nextPos] = new Square(2 * (nextPos % 2 + 1), nextPos);
		else addOne();
	}
	
	public void draw (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		Rectangle background = new Rectangle(25, 175, 400, 400);
		g2.setColor(Color.GRAY);
		g2.fill(background);
		g2.draw(background);
		
		addOne();
		
		for (int i = 0; i < 16; i++) {
			int x = squares[i].getPosition() % 4 * 100 + 30;
			int y = squares[i].getPosition() / 4 * 100 + 180;
			
			Rectangle box = new Rectangle(x, y, 90, 90);
			g2.setColor(squares[i].getColor());
			g2.fill(box);
			g2.draw(box);
			
			if (squares[i].getValue() != 0) {
				Font f = new Font("Courier", Font.BOLD,40);
				g2.setColor(Color.BLACK);
				g2.setFont(new Font("Courier", Font.BOLD,40));
				g2.drawString(squares[i].getValue() + "", x, y + 45);
			}
			
//			JPanel s = squares[i].getSquare();
			
//			s.setPreferredSize(new Dimension(100, 100));
			
//			this.add(s);
		}
	}
	
	private void mergeAll(int num, int def) {
		ArrayList<Integer> row = new ArrayList<Integer>();
		int size = 1;
		
		for (int i = 0; i < 4; i++)
			if (squares[num + def * i].getValue() != 0)
				row.add(squares[num + def * i].getValue());
		
		while (size < row.size()) {
			if (row.get(size) == row.get(size - 1)) {
				row.set(size - 1, row.get(size) * 2);
				row.remove(size);
			}
			size++;
		}
		
		for (int i = row.size(); i < 4; i++)
			row.add(0);
		
		for (int i = 0; i < 4; i++) {
			squares[num + def * i] = new Square(row.get(i), num + def * i);
		}
	}
	
	public void moveUp () {
		for (int i = 0; i < 4; i++)
			mergeAll(i, 4);
	}
	
	public void moveDown () {
		for (int i = 0; i < 4; i++)
			mergeAll(15 - i, -4);
	}
	
	public void moveLeft () {
		for (int i = 0; i < 4; i++)
			mergeAll(i * 4, 1);
	}
	
	public void moveRight () {
		for (int i = 0; i < 4; i++)
			mergeAll(15 - i * 4, -1);
	}
	public static void main (String[] args) {
		PlayTable p = new PlayTable();
		p.addOne();
		p.addOne();
		p.addOne();
		p.addOne();
		p.addOne();
		p.addOne();
		p.addOne();
		p.addOne();
		p.addOne();
		p.addOne();
		p.addOne();
		p.addOne();
	}
}
