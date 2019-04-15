import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
/*
 * A component that draws a circle inside a rectangle inside an ellipse.
 * all centered at same point, and filled with different colors
 * Name : Siyi Xian
 * User Name : siyixian
 * Partner : Zicheng Wang
 */ 
public class ImageComponent extends JComponent{
 public void paintComponent(Graphics g){
  //Recover Graphics2D
  Graphics2D g2 = (Graphics2D) g;
  
  // Construct a ellipse and draw it
     Ellipse2D.Double ellipse = new Ellipse2D.Double(75, 100, 150, 200);
  // Set the color as red
  g2.setColor(Color.RED);
  g2.fill(ellipse);
  g2.draw(ellipse);
  
  // Construct a rectangle and draw it
  Rectangle box = new Rectangle(115, 150, 70, 100);
  // Set the color as blue
  g2.setColor(Color.BLUE);
  g2.fill(box);
  g2.draw(box);
  
  // Construct a circle and draw it
     Ellipse2D.Double circle = new Ellipse2D.Double(140, 190, 20, 20);
  // Set the color as green
  g2.setColor(Color.GREEN);
  g2.fill(circle);
  g2.draw(circle);
 }
}