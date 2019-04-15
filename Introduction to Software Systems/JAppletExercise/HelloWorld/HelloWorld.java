package HelloWorld;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class HelloWorld extends Applet{
	public void init() {}
	public void stop() {}
	
	public void paint (Graphics g) {
		g.drawRect(0, 0, 100, 100);
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 100, 100);
	}
}
