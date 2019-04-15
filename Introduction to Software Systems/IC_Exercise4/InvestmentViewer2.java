import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
   This program displays the growth of an investment. 
*/
public class InvestmentViewer2
{  
   private static final int FRAME_WIDTH = 400;
   private static final int FRAME_HEIGHT = 100;

   private static final double INTEREST_RATE = 10;
   private static final double Delet_RATE = 11;
   private static final double INITIAL_BALANCE = 1000;

   public static void main(String[] args)
   {  
      JFrame frame = new JFrame();
      
      // The buttons to trigger the calculation
      JButton buttonAdd = new JButton("Add Interest");
      JButton buttonDelete = new JButton("Delete Interest");

      // The application adds interest to this bank account
      final BankAccount account = new BankAccount(INITIAL_BALANCE);
      
      // The label for displaying the results
      final JLabel label = new JLabel("balance: " + account.getBalance());
      
      // The label for displaying the names
      final JLabel labelName = new JLabel("Siyi Xian, Zicheng Wang");
      
      // The panel that holds the user interface components
      JPanel panel = new JPanel();
      panel.add(buttonAdd);
      panel.add(buttonDelete);
      panel.add(label);
      frame.add(panel);
      panel.add(labelName);
  
      class AddInterestListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            double interest = account.getBalance() * INTEREST_RATE / 100;
            account.deposit(interest);
            label.setText("balance: " + account.getBalance());
         }            
      }

      ActionListener listenerAdd = new AddInterestListener();
      buttonAdd.addActionListener(listenerAdd);
      
      class DeleteInterestListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            double interest = account.getBalance() / Delet_RATE;
            account.withdraw(interest);
            label.setText("balance: " + account.getBalance());
         }            
      }

      ActionListener listenerDelete = new DeleteInterestListener();
      buttonDelete.addActionListener(listenerDelete);
      
      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
