package OnlineMarketplaceSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Object;


public class BuyerClient {
	private Marketplace m;
	private User currentUser;
	private Scanner in;
	
	public BuyerClient() throws FileNotFoundException, IOException {
		this.m = new Marketplace();
		in = new Scanner(System.in);
	}
	
	public void logOut() {
		this.setCurrentUser(null);
	}
	
	/**
	 * @return the currentUser
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	private String getTypingVaule() {
		String t = in.nextLine();
		return t;
	}
	
	/**
	 * operation of buyers after login
	 * @throws IOException 
	 */
	private void buyerOperate(Scanner i) throws IOException {
		while (this.getCurrentUser() != null) {
			System.out.println("What would you like to do?\n1. Search Items\n2. My Orders\n3. Logout");
			int op = Integer.parseInt(this.getTypingVaule());
			switch (op) {
				case 1 :
					m.search(i);
					System.out.println("Which one do you like to get, please enter the item ID or press 999999 to exit.");
					int itemId = Integer.parseInt(this.getTypingVaule());
					if (itemId == 999999)
						break;
					Item target = m.getItems().get(itemId);
					System.out.println("How many do you want? (The max is " + (target.getAmount() + "") + ".");
					int q = Integer.parseInt(this.getTypingVaule());
					m.addTransaction(this.currentUser.getID(), target.getSellerID(), itemId, q);
					m.getItems().get(itemId).setAmount(target.getAmount() - q);
					break;
				case 2 :
					Buyer u = (Buyer) this.getCurrentUser();
					u.getHistory();
					break;
				case 3 :
					this.logOut();
					return;
				default :
					break;
			}
		}
	}
	
	/**
	 * General operation without login
	 * @throws IOException
	 */
	private void generalOperate() throws IOException {
		while (true) {
			System.out.println("Welcome to Our Online Marketplace!\nWhat would you like to do?\n1. Search Items\n2. Login\n3. Regist\n4. Exit");
			int value = Integer.parseInt(this.getTypingVaule());
			while(value!= 1 && value!= 2 && value!= 3 && value!= 4) {
				System.out.println("must tpye 1-4");
				value = Integer.parseInt(this.getTypingVaule());
			}
			
			switch (value) {
				case 1 :
					m.search(in);
					System.out.println("If you want to buy one, please login first!");
					break;
				case 2 :
					this.setCurrentUser(m.login(1));
					this.buyerOperate(in);
					break;
				case 3 :
					m.regist(1, in);
					System.out.println("Please log in");
					this.setCurrentUser(m.login(1));
					this.buyerOperate(in);
					break;
				case 4 :
					System.out.println("Thanks for coming!");
					return;
				default :
					break;
			}
		}
	}
	
	// main program
	public static void main(String[] args) throws FileNotFoundException, IOException {
		BuyerClient newProgram = new BuyerClient();
		newProgram.generalOperate();
	}
}
