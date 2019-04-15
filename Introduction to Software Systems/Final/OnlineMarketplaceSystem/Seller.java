package OnlineMarketplaceSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Seller extends User{
	private ArrayList<Transaction> history;
	private ArrayList<Item> current;
	
	private File historyF = new File("SellerHistory/" + super.getEmail() + ".csv");
	private File currentF = new File("SellerCurrent/" + super.getEmail() + ".csv");
	private File items = new File("Items.csv");
	
	public Seller(String e, String n, String p, String add, int id) throws IOException {
		super(e, n, p, add, id);
		// TODO Auto-generated constructor stub
		historyF.createNewFile();
		currentF.createNewFile();
		history = new ArrayList<Transaction>();
		current = new ArrayList<Item>();
		super.setStatus(false);
	}
	
	public void readFile(Marketplace m) throws FileNotFoundException {
		Scanner filein = new Scanner(historyF);
		while (filein.hasNextLine()) {
			String[] l = filein.nextLine().split(",");
			history.add(new Transaction(m.getBuyer(Integer.parseInt(l[0])), 
									   m.getSeller(Integer.parseInt(l[1])), 
									   m.getItem(Integer.parseInt(l[2])), 
									   Integer.parseInt(l[3]), 
									   l[4], 
									   Integer.parseInt(l[5])));
		}
		
		Scanner filein2 = new Scanner(historyF);
		while (filein2.hasNextLine()) {
			String[] l2 = filein2.nextLine().split(",");
			current.add(new Item(l2[0], Double.parseDouble(l2[1]), Integer.parseInt(l2[2]), Integer.parseInt(l2[3])));
		}
	}
	
	public void addTransaction(Transaction t) throws IOException {
		history.add(t);
		
		FileWriter w = new FileWriter(historyF, true);
		
		w.write((t.getBuyer().getID() + "") + "," +
				(t.getSeller().getID() + "") + "," +
				(t.getItem().getSellerID() + "") + "," +
				(t.getQuantity() + "") + "," +
				(t.getDate() + "") + "," +
				(t.getTrakingNumber() + "") + "\n");
		
		w.close();
	}
	
	public void getHistory() {
		String t = "";
		
		if (history != null)
			for (Transaction temp : history)
				t += temp.toString();
		else
			t += "You have not sold anything.";
		
		System.out.println(t);
	}
	
	public void addItem(Item it) throws IOException {
		current.add(it);
		
		FileWriter w = new FileWriter(currentF,true);
		
		w.write(it.getName() + "," +
			   (it.getPrice() + "") + "," +
			   (it.getAmount() + "") + "," +
			   (it.getSellerID() + "") + "\n");
		
		FileWriter w2 = new FileWriter(items,true);
		
		w2.write(it.getName() + "," +
				(it.getPrice() + "") + "," +
				(it.getAmount() + "") + "," +
				(it.getSellerID() + "") + "\n");
		
		w2.close();
		w.close();
	}
	
	public void removeItem(Item it) throws IOException {
		current.remove(it);
		
		FileWriter w = new FileWriter(currentF);
		
		for (Item temp : current)
			w.write(temp.getName() + "," +
				   (temp.getPrice() + "") + "," +
				   (temp.getAmount() + "") + "," +
				   (temp.getSellerID() + "") + "\n");
		
		w.close();
	}
	
	public void getItem() {
		String t = "";
		
		if (history != null)
			for (Item temp : current)
				t += temp.toString();
		else
			t += "You do not have anything to sale.";
		
		System.out.println(t);
	}
}
