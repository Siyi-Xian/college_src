package TortoiseHareRace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class THDriver extends JPanel implements ActionListener{	
	private final int width;
	private final int height;
	
	Timer t;                    // time clock
	JButton start;              // start button
	JButton stop;               // stop button
	
	Hare hare;                  // hare
	Tortoise tortoise;          // tortoise
	
	JLabel hareLabel;           // "HX" which represent hare
	JLabel tortoiseLabel;       // "TX" which represent tortoise
	JLabel bit;                 // "OUCH!!!"
	JLabel begin;               // "BANG !!!!! AND THEY'RE OFF !!!!!"
	JLabel winner;              // Label to show the winner
	
	int time;                   // save the time in order to restart the program
	
	/**
	 * Contractor
	 * @param s : size of frame
	 */
	public THDriver(Dimension s) {
		width = 320;                   // set size
		height = 160;
		
		time = 999;                    // time recorder
		
		t = new Timer(1000, this);     // initial clock
		
		hare = new Hare();             // initial hare
		tortoise = new Tortoise();     // initial tortoise
		
		this.setBackground(Color.CYAN);// set background color
		this.setPreferredSize(s);      // set size of this panel
		this.setFocusable(true);       // set focus
		
		// button initial setting
		start = new JButton("Start");
		stop = new JButton("Stop");
		start.addActionListener(new Start());
		stop.addActionListener(new Stop());
		start.setLayout(null);
		stop.setLayout(null);
		stop.setVisible(false);
		
		// label initial setting
		hareLabel = new JLabel();
		tortoiseLabel = new JLabel();
		bit = new JLabel("OUCH!!!");
		begin = new JLabel("BANG !!!!!\nAND THEY'RE OFF !!!!!");
		winner = new JLabel("", JLabel.CENTER);
		
		this.hareLabel.setFont(new Font("Times New Roman", Font.BOLD, 10));
		this.hareLabel.setVisible(false);
		this.hareLabel.setLayout(null);
		this.tortoiseLabel.setVisible(false);
		this.tortoiseLabel.setLayout(null);
		this.tortoiseLabel.setFont(new Font("Times New Roman", Font.BOLD, 10));
		this.bit.setFont(new Font("Times New Roman", Font.BOLD, 12));
		this.bit.setVisible(false);
		this.bit.setLayout(null);
		this.begin.setLayout(null);
		this.begin.setFont(new Font("Times New Roman", Font.BOLD, 20));
		this.begin.setVisible(false);
		this.winner.setVerticalAlignment(SwingConstants.CENTER);
		this.winner.setFont(new Font("Times New Roman", Font.BOLD, 20));
		this.winner.setVisible(false);
		
		// add all components
		this.add(start);
		this.add(stop);
		this.add(hareLabel);
		this.add(tortoiseLabel);
		this.add(bit);
		this.add(begin);
	}
	
	/**
	 * draw the mountain line
	 * @param g : Graphics
	 */
	private void drawLine(Graphics g) {
		g.drawLine(340, 260, 180, 100);
		g.drawLine(20, 260, 180, 100);
	}
	
	/**
	 * display what we want
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);  // paint all panel information
		
		// set location of some panels
		this.start.setLocation(new Point(260, 10));
		this.stop.setLocation(new Point(260, 10));
		this.begin.setLocation(new Point(20, 120));
		
		if (time == 999);    // status before start
		else if (time < 0)   // status after the competition is tie 
			time++;
		else if (hare.getP() >= 639 && tortoise.getP() >= 639) { // two animals tie
			this.add(winner);
			this.winner.setText("IT’S A TIE");
			this.winner.setVisible(true);
			
			hare.setP(0);
			tortoise.setP(0);
			time = -3;
			
			this.tortoiseLabel.setVisible(false);
			this.hareLabel.setVisible(false);
			this.bit.setVisible(false);
			this.begin.setVisible(false);
		}
		else if (hare.getP() >= 639) {  // hare win
			this.removeAll();
			t.stop();
			
			this.add(winner);
			this.winner.setText("HARE WINS! YUCH!");
			this.winner.setVisible(true);
		}
		else if (tortoise.getP() >= 639) { // tortoise win
			this.removeAll();
			t.stop();
			
			this.add(winner);
			this.winner.setText("TORTOISE WINS!!! YAY!!!");
			this.winner.setVisible(true);
		}
		else if (time >= 0) { // after click start button
			this.winner.setVisible(false);
			if (time <= 2 && time >= 0) { // show start information
				begin.setVisible(true);
				time++;
			}
			else { // competition in process
				this.drawLine(g);
				
				this.hareLabel.setVisible(true);
				this.hareLabel.setLocation(hare.getPos(width, height).x - 2, hare.getPos(width, height).y - 9);
				this.hareLabel.setText("H" + ((hare.getP() / 320 + 1) + ""));
				this.tortoiseLabel.setVisible(true);
				this.tortoiseLabel.setLocation(tortoise.getPos(width, height).x - 2, tortoise.getPos(width, height).y + 5);
				this.tortoiseLabel.setText("T" + ((tortoise.getP() / 320 + 1) + ""));
				this.bit.setLocation(hare.getPos(width, height).x - 18, hare.getPos(width, height).y - 19);
				
				bit.setVisible(false);
				if (this.tortoise.getP() % 320 == this.hare.getP() % 320)
					bit.setVisible(true);
				
				this.hare.draw(g, width, height);
				this.tortoise.draw(g, width, height);
				
				this.begin.setVisible(false);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		hare.move();      // let hare move
		tortoise.move();  // let tortoise move
		
		this.repaint();   // display on time
	}
	
	/**
	 * inner class to set start button
	 * @author xiansiyi
	 *
	 */
	private class Start implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			t.start();
			start.setVisible(false);
			stop.setVisible(true);
			time = 0;
		}
		
	}
	
	/**
	 * inner class to set stop button
	 * @author xiansiyi
	 *
	 */
	private class Stop implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			t.stop();
			start.setVisible(true);
			stop.setVisible(false);
		}
		
	}
}
