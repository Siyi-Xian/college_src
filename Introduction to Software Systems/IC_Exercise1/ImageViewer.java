import javax.swing.JFrame;

public class ImageViewer
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();

      frame.setSize(300, 400);
      frame.setTitle("Rectangle,ellipse, and circle");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      ImageComponent component = new ImageComponent();
      frame.add(component);

      frame.setVisible(true);
   }
}
