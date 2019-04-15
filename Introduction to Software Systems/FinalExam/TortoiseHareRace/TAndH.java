package TortoiseHareRace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

abstract public class TAndH {
	private int p;
	private Random r;
	
	/**
	 * Constructor
	 */
	public TAndH() {
		setP(0);
		setR();
	}
	
	/**
	 * get position in Point of the panel
	 * @param w : width of the panel
	 * @param h : height of the panel
	 * @return position
	 */
	public Point getPos(int w, int h) {
		// TODO Auto-generated method stub
		return new Point(w - (this.getP() % w) + 20 - 4, Math.abs(160 - (this.getP() % w)) + 100 - 4);
	}
	
	/**
	 * Draw a little square to represent animals
	 * @param g : Graphics
	 * @param w : width of frame
	 * @param h : height of frame
	 */
	public void draw(Graphics g, int w, int h) {
		g.drawRect(this.getPos(w, h).x + 2, this.getPos(w, h).y + 2, 4, 4);
		g.setColor(Color.BLACK);
		g.fillRect(this.getPos(w, h).x + 2, this.getPos(w, h).y + 2, 4, 4);
	}
	
	/**
	 * @return the p
	 */
	public int getP() {
		return p;
	}

	/**
	 * @param p the p to set
	 */
	public void setP(int p) {
		this.p = p;
	}
	
	/**
	 * @return the r
	 */
	public Random getR() {
		return r;
	}

	/**
	 * @param r the r to set
	 */
	public void setR() {
		this.r = new Random();
	}
	
	/**
	 * let animals move
	 */
	abstract void move(); 
}
