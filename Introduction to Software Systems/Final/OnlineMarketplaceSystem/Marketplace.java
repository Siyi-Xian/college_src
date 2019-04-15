package OnlineMarketplaceSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Marketplace {	
	private ArrayList<Buyer> buyers;
	private ArrayList<Seller> sellers;
	private ArrayList<Category> cates;
	private ArrayList<Transaction> transactions;
	private ArrayList<Item> items;
	
	private final File buyer;
	private final File selle;
	private final File cate;
	private final File trans;
	private final File item;
	
	private final FileIO f;
	
	public Marketplace() throws FileNotFoundException, IOException {
		buyer = new File("Buyers.csv");
		selle = new File("Sellers.csv");
		cate = new File("Cates.csv");
		trans = new File("Transactions.csv");
		item = new File("Items.csv");
		
		f = new FileIO();
		
		this.fileInput();
	}
	
	/**
	 * inner class to do File I/O
	 */
	private class FileIO {
		
		/**
		 * 
		 * @param file
		 * @param type
		 * @return
		 * @throws FileNotFoundException
		 * @throws IOException
		 */
		@SuppressWarnings("unchecked")
		public <T> ArrayList<T> getInput(File file, int type) throws FileNotFoundException, IOException {
			Scanner in = new Scanner(file);
			ArrayList<T> list = new ArrayList<T>();
			
			while (in.hasNextLine()) {
				String[] tempList = in.nextLine().split(",");
				
				switch (type) {
					case 1 :
						list.add((T) this.addBuyer(tempList, list.size()));
						break;
					case 2 :
						list.add((T) this.addSeller(tempList, list.size()));
						break;
					case 3 :
						list.add((T) this.addCategory(tempList));
						break;
					case 4 :
						list.add((T) this.addTransaction(tempList));
						break;
					case 5 :
						list.add((T) this.addItem(tempList));
						break;
					default :
						break;
				}	
			}
			return list;
		}
		
		//
		private Buyer addBuyer(String[] l, int id) throws IOException {
			return new Buyer(l[0], l[1], l[2], l[3], id);
		}
		private Seller addSeller(String[] l, int id) throws IOException {
			return new Seller(l[0], l[1], l[2], l[3], id);
		}
		private Category addCategory(String[] l) {
			return new Category(l[0], Integer.parseInt(l[1]));
		}
		private Transaction addTransaction(String[] l) {
			return new Transaction(getBuyer(Integer.parseInt(l[0])), 
								   getSeller(Integer.parseInt(l[1])), 
								   getItem(Integer.parseInt(l[2])), 
								   Integer.parseInt(l[3]), 
								   l[4], 
								   Integer.parseInt(l[5]));
		}
		private Item addItem(String[] l) {
			return new Item(l[0], Double.parseDouble(l[1]), Integer.parseInt(l[2]), Integer.parseInt(l[3]));
		}
		
		/**
		 * 
		 * @param t
		 * @param f
		 * @throws IOException
		 */
		public void addNewTransaction(Transaction t, File f) throws IOException {
			FileWriter w = new FileWriter(f, true);
			
			w.write((t.getBuyer().getID() + "") + "," +
					(t.getSeller().getID() + "") + "," +
					(t.getItem().getSellerID() + "") + "," +
					(t.getQuantity() + "") + "," +
					(t.getDate() + "") + "," +
					(t.getTrakingNumber() + "") + "\n");
			
			w.close();
		}
		
		/**
		 * 
		 * @param u
		 * @param f
		 * @throws IOException
		 */
		public void addNewUser(User u, File f) throws IOException {
			FileWriter w = new FileWriter(f, true);
			
			w.write((u.getEmail() + "") + "," +
					(u.getName() + "") + "," +
					(u.getPasswords() + "") + "," +
					(u.getAddress() + "") + "\n");
			
			w.close();
		}
	}
	
	/**
	 * get all data from CSV files
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void fileInput() throws FileNotFoundException, IOException {
		buyers = f.<Buyer>getInput(buyer, 1);
		sellers = f.<Seller>getInput(selle, 2);
		cates = f.<Category>getInput(cate, 3);
		items = f.<Item>getInput(item, 5);
		transactions = f.<Transaction>getInput(trans, 4);
		
		for (Buyer temp : buyers) 
			temp.readFile(this);
		for (Seller temp : sellers) 
			temp.readFile(this);
	}
	
	/**
	 * new users' register
	 * @throws IOException
	 */
	public void regist(int s, Scanner i) throws IOException {
		System.out.print("Name: ");
		String name = i.nextLine();
		System.out.print("Email: ");
		String email = i.nextLine();
		System.out.print("Password: ");
		String password = i.nextLine();
		System.out.println("Address: ");
		String address = i.nextLine();
		
		switch (s) {
			case 1 : {
				Buyer n = new Buyer(email, name, password, address, this.getBuyers().size());
				buyers.add(n);
				f.addNewUser(n, buyer);
				break;
			}
			case 2 : {
				Seller n = new Seller(email, name, password, address, this.getSellers().size());
				sellers.add(n);
				f.addNewUser(n, selle);
				break;
			}
			default :
				break;
		}
	}
	
	/**
	 * create a new transaction
	 * @param buyerID
	 * @param sellerID
	 * @param itemID
	 * @param quatity
	 * @throws IOException
	 */
	public void addTransaction(int buyerID, int sellerID, int itemID, int quatity) throws IOException {
		String date = Calendar.getInstance().getTime().toString();
		Transaction t = new Transaction(getBuyer(buyerID), getSeller(sellerID), getItem(itemID), quatity, date, 0);
		
		f.addNewTransaction(t, trans);
		this.transactions.add(t);
		this.buyers.get(buyerID).addTransaction(t);
		this.sellers.get(sellerID).addTransaction(t);
	}
	
	/**
	 * Search item
	 * @param name
	 * @return
	 */
	public void search(Scanner in) {
		String name;
		System.out.println("What would you like to search?");
		name = in.nextLine();
		
		for (Item temp : items)
			if (temp.getName().equals(name))
				System.out.println(temp.toString());
		System.out.println("Sorry, we could not find anything of this item.");
	}
	
	/**
	 * 
	 * @param list
	 * @param type
	 * @return
	 */
	private <T> User reLogin(ArrayList<T> list, String type) {
		System.out.println("Your password or " + type + " is incorrect!");
		return this.<T>userLogin(list);
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("resource")
	private <T> User userLogin(ArrayList<T> list) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Please enter your email address or name:");
		String name = in.nextLine();
		System.out.println("Please enter your password:");
		String password = in.nextLine();
		
		if (name.indexOf("@") != -1 && name.indexOf(".") != -1) {
			for (T temp : list)
				if (((User) temp).getEmail().equals(name))
					if (((User) temp).getPasswords().equals(password))
						return (User) temp;
					else
						return this.<T>reLogin(list, "email address");
			return this.<T>reLogin(list, "email address");
		}
		else {
			for (T temp : list)
				if (((User) temp).getName().equals(name))
					if (((User) temp).getPasswords().equals(password))
						return (User) temp;
					else
						return this.<T>reLogin(list, "name");
			return this.<T>reLogin(list, "name");
		}
	}
	
	/**
	 * login
	 * @throws IOException
	 */
	public User login(int s) throws IOException {
		switch (s) {
			case 1 :
				return this.<Buyer>userLogin(buyers);
			case 2 :
				return this.<Seller>userLogin(sellers);
			default :
				break;
		}
		return null;
	}
	
	// all get methods
	public Transaction getTransaction(int id) {
		return this.transactions.get(id);
	}
	public Buyer getBuyer(int id) {
		return this.buyers.get(id);
	}
	public Seller getSeller(int id) {
		return this.sellers.get(id);
	}
	public Category getCategory(int id) {
		return this.cates.get(id);
	}
	public Item getItem(int id) {
		return this.items.get(id);
	}
	public ArrayList<Transaction> getTransactions() {
		return this.transactions;
	}
	public ArrayList<Buyer> getBuyers() {
		return this.buyers;
	}
	public ArrayList<Seller> getSellers() {
		return this.sellers;
	}
	public ArrayList<Category> getCategorys() {
		return this.cates;
	}
	public ArrayList<Item> getItems() {
		return this.items;
	}
	public void getData() {
	}
	
	public static void main (String[] args) throws FileNotFoundException, IOException {
		Marketplace m = new Marketplace();
	}
}
