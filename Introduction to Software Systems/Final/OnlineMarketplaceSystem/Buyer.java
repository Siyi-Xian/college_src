package OnlineMarketplaceSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Buyer extends User{
	private ArrayList<Transaction> history;
	
	private File f = new File("BuyerHistory/" + super.getEmail() + ".csv");
	
	public Buyer(String e, String n, String p, String add, int id) throws IOException {
		super(e, n, p, add, id);
		// TODO Auto-generated constructor stub
		f.createNewFile();
		
		history = new ArrayList<Transaction>();
		super.setStatus(true);
	}
	
	public void readFile(Marketplace m) throws FileNotFoundException {
		Scanner filein = new Scanner(f);
		while (filein.hasNextLine()) {
			String[] l = filein.nextLine().split(",");
		
			history.add(new Transaction(m.getBuyer(Integer.parseInt(l[0])), 
										m.getSeller(Integer.parseInt(l[1])), 
										m.getItem(Integer.parseInt(l[2])), 
										Integer.parseInt(l[3]), 
										l[4], 
										Integer.parseInt(l[5])));
		}
	}
	
	/**
	 * add a new history record
	 * @param t
	 * @throws IOException
	 */
	public void addTransaction(Transaction t) throws IOException {
		history.add(t);
		
		FileWriter w = new FileWriter(f, true);
		
		w.write((t.getBuyer().getID() + "") + "," +
				(t.getSeller().getID() + "") + "," +
				(t.getItem().getSellerID() + "") + "," +
				(t.getQuantity() + "") + "," +
				(t.getDate() + "") + "," +
				(t.getTrakingNumber() + "") + "\n");
		
		w.close();
	}
	
	// get all history buying records
	public void getHistory() {
		String t = "";
		
		if (history != null)
			for (Transaction temp : history)
				t += temp.toString();
		else
			t += "You have not gotten anything.";
		
		System.out.println(t);
	}
}
